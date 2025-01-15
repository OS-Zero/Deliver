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

package com.oszero.deliver.business.admin.model.dto.request.sendtask;

import com.oszero.deliver.business.admin.annotation.DescriptionLength;
import com.oszero.deliver.business.admin.annotation.NameLength;
import com.oszero.deliver.business.admin.annotation.StatusRange;
import com.oszero.deliver.business.common.annotation.JsonString;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author oszero
 * @version 1.0.0
 */
@Data
public class SendTaskUpdateRequestDto {
    @NotNull(message = "任务id不能为空")
    private Long taskId;

    @NameLength
    private String taskName;

    @DescriptionLength
    private String taskDescription;

    @JsonString
    private String taskMessageParam;

    private String taskTimeExpression;

    @StatusRange(min = 1, max = 3, message = "任务类型只能是1-3")
    private Integer taskType;

    @NotNull(message = "模板id不能为空")
    private Long templateId;

    @NotNull(message = "人群id不能为空")
    private Long peopleGroupId;
}