package com.weike.system.controller;

import java.util.Arrays;
import java.util.Map;

import com.weike.common.exception.LoginFailException;
import com.weike.common.exception.UserInfoFailException;
import com.weike.system.vo.user.UserInfoDetailVo;
import com.weike.system.vo.user.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.weike.system.entity.UserEntity;
import com.weike.system.service.UserService;
import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;

/**
 * 用户表
 *
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-10 14:03:35
 */
@Slf4j
@RestController
@RequestMapping("system/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param
     * @return
     */
    @PostMapping("/logout")
    public R logout() {

        return R.ok();
    }

    /**
     * 获取用户信息
     * @param token
     * @return
     * @throws UserInfoFailException
     */
    @GetMapping("/info")
    public R userInfo(@RequestHeader String token,
                      @RequestParam(required = false) String distributionId) throws UserInfoFailException {
        UserInfoVo userInfoVo = userService.userInfoByToken(token , distributionId);
        return R.ok().put("data" , userInfoVo);
    }

    /**
     * 查询用户详细信息
     * @param token
     * @return
     * @throws UserInfoFailException
     */
    @GetMapping("/info/detail")
    public R userInfoDetail(@RequestHeader String token) {
        UserInfoDetailVo userInfoDetailVo = userService.userInfoDetailByToken(token);
        return R.ok().put("data" , userInfoDetailVo);
    }

    /**
     * 登录
     * @param userEntity
     * @return
     * @throws LoginFailException
     */
    @PostMapping("/login")
    public R login(@RequestBody UserEntity userEntity) throws LoginFailException {
        String token = userService.login(userEntity);
        return R.ok().put("data" , token);
    }

    /**
     * 当前用户下子账户列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,
                  @RequestHeader String token){
        PageUtils page = userService.queryUserPage(params , token);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    //@RequiresPermissions("system:user:info")
    public R info(@PathVariable("userId") String userId){
		UserEntity user = userService.getById(userId);
        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("system:user:save")
    public R save(@RequestBody UserEntity user){
		userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("system:user:update")
    public R update(@RequestBody UserEntity user){
		userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("system:user:delete")
    public R delete(@RequestBody String[] userIds){
		userService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
