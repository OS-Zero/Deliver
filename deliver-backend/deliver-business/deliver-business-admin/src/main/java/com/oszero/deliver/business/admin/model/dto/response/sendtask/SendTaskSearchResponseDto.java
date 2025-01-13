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

package com.oszero.deliver.business.admin.model.dto.response.sendtask;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendTaskSearchResponseDto {
    private Long taskId;
    private String taskName;
    private String taskDescription;
    private Integer taskType;
    private String taskTimeExpression;
    private String taskMessageParam;
    private Integer taskStatus;
    private Long templateId;
    private String templateName;
    private Long peopleGroupId;
    private String peopleGroupName;
    private String createUser;
    private LocalDateTime createTime;
}
