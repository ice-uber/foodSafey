package com.weike.foodsafe.vo.distrobution;

import com.baomidou.mybatisplus.annotation.TableId;
import com.weike.foodsafe.entity.CooperationEntity;
import com.weike.foodsafe.vo.goods.GoodsCountVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DistributionResVo
 * @Author: YuanDing
 * @Date: 2024/4/18 16:44
 * @Description:
 */

@Data
public class DistributionResVo {

    private String distributionid;
    /**
     *
     */
    private String companyname;
    /**
     *
     */

    private String name;
    /**
     *
     */
    private String phone;
    /**
     *
     */
    private String email;
    /**
     *
     */
    private String area;
    /**
     *
     */
    private String addr;
    /**
     * 是否认证
     */
    private String hasAuthentication;
    private CooperationEntity cooperation;
    private List<GoodsCountVo> goodsCount;
    private int sumFinishOrder;
    private BigDecimal sumMoney;
    private List<String> imgList;

    private String shopImg;
}
