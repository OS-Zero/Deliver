/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oszero.deliver.business.admin.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.oszero.deliver.business.admin.model.entity.cache.CacheUserInfo;
import com.oszero.deliver.business.admin.util.GroupUtils;
import com.oszero.deliver.business.admin.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Configuration
@MapperScan("com.oszero.deliver.business.admin.mapper")
public class MyBatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Component
    public static class MyMetaObjectHandler implements MetaObjectHandler {

        @Override
        public void insertFill(MetaObject metaObject) {
            CacheUserInfo userInfo = UserUtils.getCurrentLoginUserInfo();
            if (Objects.isNull(userInfo)) {
                return;
            }
            this.strictInsertFill(metaObject, "createUser", userInfo::getUserRealName, String.class);
            this.strictInsertFill(metaObject, "updateUser", userInfo::getUserRealName, String.class);
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            CacheUserInfo userInfo = UserUtils.getCurrentLoginUserInfo();
            if (Objects.isNull(userInfo)) {
                return;
            }
            this.strictUpdateFill(metaObject, "updateUser", userInfo::getUserRealName, String.class);
        }
    }
}
