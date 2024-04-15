package com.weike.system.vo.user;

import lombok.Data;

/**
 * @ClassName: UserInfoDetailVo
 * @Author: YuanDing
 * @Date: 2024/4/13 23:41
 * @Description:
 */

@Data
public class UserInfoDetailVo {
    private String userId;
    private String userName;
//    private String userPassword;
    private String userRealname;
    private String userTel;
    private String userEmail;
    private String distributionid;
    private String companyname;
    private String addr;
    private String IdentificationCardImg; // 法人身份证照片
    private String BusinessCardImg; // 营业证照片
}
