package com.weike.system.vo.user;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: UserInfoVo
 * @Author: YuanDing
 * @Date: 2024/4/10 19:57
 * @Description:
 */

@Data
public class UserInfoVo {
    private String userName;
    private String avatar;
    private String role;
    private String companyName;

    private String distributionId;

    private String distributionCompanyName;
}
