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

package com.oszero.deliver.server.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.model.entity.MessageRecord;
import com.oszero.deliver.server.web.service.MessageRecordService;
import com.oszero.deliver.server.web.mapper.MessageRecordMapper;
import org.springframework.stereotype.Service;

/**
 * 针对表【message_record(消息记录)】的数据库操作Service实现
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class MessageRecordServiceImpl extends ServiceImpl<MessageRecordMapper, MessageRecord>
        implements MessageRecordService {

    @Override
    public void saveMessageRecord(SendTaskDto sendTaskDto, StatusEnum messageStatus, String user) {

        MessageRecord messageRecord = MessageRecord.builder()
                .traceId(sendTaskDto.getTraceId())
                .templateId(sendTaskDto.getTemplateId())
                .appId(sendTaskDto.getAppId())
                .channelType(sendTaskDto.getChannelType())
                .messageType(sendTaskDto.getMessageType())
                .userType(sendTaskDto.getUsersType())
                .pushUser(user)
                .pushRange(sendTaskDto.getPushRange())
                .retried(sendTaskDto.getRetried())
                .messageStatus(messageStatus.getStatus())
                .build();
        this.save(messageRecord);
    }
}




