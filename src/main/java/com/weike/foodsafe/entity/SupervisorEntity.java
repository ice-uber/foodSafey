package com.weike.foodsafe.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName: SupervisorEntity
 * @Author: YuanDing
 * @Date: 2024/4/19 20:11
 * @Description:
 */

@Data
@TableName("ybsx_supervisor")
public class SupervisorEntity {
    @TableId
    private String supervisorId;
    private String supervisorCompanyName;
    private String supervisorName;
    private String tel;

}
