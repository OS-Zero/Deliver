package com.oszero.deliver.admin.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.oszero.deliver.admin.util.MdcUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static com.oszero.deliver.admin.constant.MdcConstant.USER_ID_NAME;

/**
 * Mybatis-Plus 配置类
 *
 * @author oszero
 * @version 1.0.0
 */
@Configuration
@MapperScan("com.oszero.deliver.admin.mapper")
public class AdminMyBatisPlusConfig {
    /**
     * 添加分页插件
     *
     * @return 拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));//如果配置多个插件,切记分页最后添加
        //interceptor.addInnerInterceptor(new PaginationInnerInterceptor()); 如果有多数据源可以不配具体类型 否则都建议配上具体的DbType
        return interceptor;
    }

    @Slf4j
    @Component
    public static class MyMetaObjectHandler implements MetaObjectHandler {

        @Override
        public void insertFill(MetaObject metaObject) {
            log.info("start insert fill ....");
            this.strictInsertFill(metaObject, "createUser", () -> MdcUtils.get(USER_ID_NAME), String.class); // 起始版本 3.3.3(推荐)
            this.strictInsertFill(metaObject, "updateUser", () -> MdcUtils.get(USER_ID_NAME), String.class); // 起始版本 3.3.3(推荐)
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            log.info("start update fill ....");
            // 或者
            this.strictUpdateFill(metaObject, "updateUser", () -> MdcUtils.get(USER_ID_NAME), String.class); // 起始版本 3.3.3(推荐)
        }
    }
}
