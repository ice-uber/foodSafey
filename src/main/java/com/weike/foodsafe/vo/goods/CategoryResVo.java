package com.weike.foodsafe.vo.goods;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: CategoryVo
 * @Author: YuanDing
 * @Date: 2024/4/16 20:00
 * @Description:
 */

@Data
public class CategoryResVo {

    private String value;
    private String label;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CategoryResVo> children;
}
