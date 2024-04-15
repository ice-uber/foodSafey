package com.weike.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName: MybatisPlusConfig
 * @Author: YuanDing
 * @Date: 2024/4/11 17:25
 * @Description:
 */

@Slf4j
@Configuration
@MapperScan({"com/weike/foodsafe/dao" , "com/weike/system/dao"})
public class MybatisPlusConfig implements MetaObjectHandler {

    /**
     * 添加分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));//如果配置多个插件,切记分页最后添加
        //interceptor.addInnerInterceptor(new PaginationInnerInterceptor()); 如果有多数据源可以不配具体类型 否则都建议配上具体的DbType
        return interceptor;
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");

        // 或者
        this.strictInsertFill(metaObject, "addtime", () -> new Date(), Date.class);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        // 或者
        this.strictUpdateFill(metaObject, "updatetime", () -> new Date(), Date.class); // 起始版本 3.3.3(推荐)

    }
}
