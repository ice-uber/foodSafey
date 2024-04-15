package com.weike.system.dao;

import com.weike.system.entity.RoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统角色表
 * 
 * @author yuanding
 * @email 1472939313@qq.com
 * @date 2024-04-10 14:03:35
 */
@Mapper
public interface RoleDao extends BaseMapper<RoleEntity> {
	
}
