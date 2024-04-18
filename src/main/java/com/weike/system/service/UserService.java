package com.weike.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weike.common.exception.LoginFailException;
import com.weike.common.exception.UserInfoFailException;
import com.weike.common.utils.PageUtils;
import com.weike.system.entity.UserEntity;
import com.weike.system.vo.user.UserInfoDetailVo;
import com.weike.system.vo.user.UserInfoVo;

import java.util.Map;

/**
 * 用户表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-10 14:03:35
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    String login(UserEntity userEntity) throws LoginFailException;

    UserInfoVo userInfoByToken(String token , String distributionId) throws UserInfoFailException;

    PageUtils queryUserPage(Map<String, Object> params , String token);

    UserInfoDetailVo userInfoDetailByToken(String token);


}

