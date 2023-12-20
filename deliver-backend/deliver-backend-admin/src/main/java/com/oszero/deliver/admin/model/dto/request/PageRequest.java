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

package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;

/**
 * 分页查询 dto
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public abstract class PageRequest {

    /**
     * 当前页 默认值 1
     */
    protected Integer currentPage = 1;

    /**
     * 页大小 默认值 10
     */
    protected Integer pageSize = 10;
}
