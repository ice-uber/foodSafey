package com.weike.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weike.common.exception.BizCodeEnum;
import com.weike.common.exception.LoginFailException;
import com.weike.common.exception.UserInfoFailException;
import com.weike.common.utils.JwtHelper;
import com.weike.common.utils.MD5Util;
import com.weike.foodsafe.dao.DistributionDao;
import com.weike.foodsafe.entity.AttachmentsEntity;
import com.weike.foodsafe.entity.CooperationEntity;
import com.weike.foodsafe.entity.DistributionEntity;
import com.weike.foodsafe.entity.PurchaserEntity;
import com.weike.foodsafe.service.AttachmentsService;
import com.weike.foodsafe.service.CooperationService;
import com.weike.foodsafe.service.DistributionService;
import com.weike.foodsafe.service.PurchaserService;
import com.weike.system.vo.user.UserInfoDetailVo;
import com.weike.system.vo.user.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.Query;

import com.weike.system.dao.UserDao;
import com.weike.system.entity.UserEntity;
import com.weike.system.service.UserService;


@Slf4j
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private DistributionDao distributionDao;

    @Autowired
    private AttachmentsService attachmentsService;

    @Autowired
    private PurchaserService purchaserService;

    @Autowired
    private CooperationService cooperationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 登录请求
     * @param userEntity 用户实体类
     */
    @Override
    public String login(UserEntity userEntity) throws LoginFailException {
        // 1. 查询当前用户是否存在
        UserEntity user = this.getOne(new LambdaQueryWrapper<UserEntity>()
                .eq(UserEntity::getUserName, userEntity.getUserName()));


        if (user == null) {
            // 不存在抛出登录异常
            throw new LoginFailException(BizCodeEnum.USERNAME_NO_EXIST_EXCEPTION.getMsg() , BizCodeEnum.USERNAME_NO_EXIST_EXCEPTION.getCode());
        } else {
            // 如果存在则验证密码

            // 先进行MD5加密
            String encryptStr = MD5Util.encrypt(userEntity.getUserPassword());
            log.info(encryptStr);
            long count = this.count(new LambdaQueryWrapper<UserEntity>()
                    .eq(UserEntity::getUserPassword, encryptStr)
                    .eq(UserEntity::getUserName , userEntity.getUserName()));

            // 如果密码验证成功则生成token并返回
            if (count > 0) {
                String token = jwtHelper.createToken(user.getUserId());
                log.info("生成的token: {}" , token);
                return token;
            }
        }

        throw new LoginFailException(BizCodeEnum.PASSWORD_ERROR_EXCEPTION.getMsg(), BizCodeEnum.PASSWORD_ERROR_EXCEPTION.getCode());

    }

    /**
     * 获取有用户详细信息
     * @param token
     * @return
     */
    @Override
    public UserInfoVo userInfoByToken(String token  , String distributionIdVo) throws UserInfoFailException {
        String userId = jwtHelper.getUserId(token);
        log.info("用户id: {}" , userId);
        UserEntity userEntity = this.getById(userId);
        if (userEntity != null) {
            String distributionId = distributionDao.getDistributionIdByUserId(userId);
            DistributionEntity distributionEntity = distributionDao.selectById(distributionId);
            UserInfoVo userInfoVo = new UserInfoVo();
            userInfoVo.setUserName(userEntity.getUserName());
            userInfoVo.setAvatar(userEntity.getAvatar());
            userInfoVo.setRole(userEntity.getUserType());
            // 如果配送商不为空则代表该账号是配送商
            if (distributionEntity != null ){
                userInfoVo.setCompanyName(distributionEntity.getCompanyname());
            } else {


                // 设置采购商公司名
                PurchaserEntity purchaserEntity = purchaserService.getOne(new LambdaQueryWrapper<PurchaserEntity>()
                        .eq(PurchaserEntity::getUserId, userId));
                userInfoVo.setCompanyName(purchaserEntity.getCompanyname());

                // 如果配送商id前端传递则要查指定的配送商数据
                if (distributionIdVo != null) {
                    DistributionEntity distribution = distributionDao.selectById(distributionIdVo);
                    userInfoVo.setDistributionId(distributionIdVo);
                    userInfoVo.setDistributionCompanyName(distribution.getCompanyname());
                } else {
                    // 查找合作的配送商，如果没有设置为null
                    List<CooperationEntity> cooperationEntity = cooperationService.list(new LambdaQueryWrapper<CooperationEntity>()
                            .eq(CooperationEntity::getPurchaserid, purchaserEntity.getPurchaserid()));
                    DistributionEntity distribution = distributionDao.selectById(cooperationEntity.get(0).getDistributionid());
                    userInfoVo.setDistributionId(cooperationEntity.get(0).getDistributionid());

                    userInfoVo.setDistributionCompanyName(distribution.getCompanyname());
                }


            }
            return userInfoVo;
        }

        throw new UserInfoFailException("获取用户信息失败");
    }

    /**
     * 当前用户子用户列表
     * @param params
     * @return
     */
    @Override
    public PageUtils queryUserPage(Map<String, Object> params , String token) {
        log.info("获取子账号");
        String userId = jwtHelper.getUserId(token);
        PageUtils pageUtils = null;
        if (!StringUtils.isBlank(userId)) {
            IPage<UserEntity> page = this.page(
                    new Query<UserEntity>().getPage(params),
                    new LambdaQueryWrapper<UserEntity>()
                            .eq(UserEntity::getBelongId, userId)
            );
            pageUtils = new PageUtils(page);
        }
        return pageUtils;
    }

    /**
     * 查询用户详细信息
     * @param token
     * @return
     */
    @Override
    public UserInfoDetailVo userInfoDetailByToken(String token) {
        String userId = jwtHelper.getUserId(token);

        UserInfoDetailVo userInfoDetailVo = new UserInfoDetailVo();

        UserEntity userEntity = this.getById(userId);
        BeanUtils.copyProperties(userEntity , userInfoDetailVo);

        DistributionEntity distributionEntity = distributionDao.selectOne(new LambdaQueryWrapper<DistributionEntity>()
                .eq(DistributionEntity::getUserId, userId));
        BeanUtils.copyProperties(distributionEntity, userInfoDetailVo);

        List<AttachmentsEntity> attachmentsEntityList = attachmentsService.list(new LambdaQueryWrapper<AttachmentsEntity>()
                .eq(AttachmentsEntity::getRelationid, distributionEntity.getDistributionid()));

        for(AttachmentsEntity attachmentsEntity : attachmentsEntityList) {
            if (Objects.equals(attachmentsEntity.getType(), "1")) {
                userInfoDetailVo.setBusinessCardImg(attachmentsEntity.getUrl());
            }
            if (Objects.equals(attachmentsEntity.getType(), "2")) {
                userInfoDetailVo.setIdentificationCardImg(attachmentsEntity.getUrl());
            }
        }

        return userInfoDetailVo;
    }

}