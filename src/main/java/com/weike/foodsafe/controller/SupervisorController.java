package com.weike.foodsafe.controller;

import com.weike.common.utils.PageUtils;
import com.weike.common.utils.R;
import com.weike.foodsafe.service.SupervisorService;
import com.weike.foodsafe.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: SupervisorController
 * @Author: YuanDing
 * @Date: 2024/4/19 20:20
 * @Description:
 */

@RestController
@RequestMapping("foodsafe/supervisor")
public class SupervisorController {

    @Autowired
    private SupervisorService supervisorService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String , Object> params) {
        PageUtils pageUtils = supervisorService.orderList(params);
        return R.ok().put("data" , pageUtils);
    }

}
