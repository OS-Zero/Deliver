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

package com.oszero.deliver.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.admin.mapper.AdminMessageRecordMapper;
import com.oszero.deliver.admin.model.entity.MessageRecord;
import com.oszero.deliver.admin.service.MessageRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 针对表【message_record(消息记录)】的数据库操作Service实现
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class AdminMessageRecordServiceImpl extends ServiceImpl<AdminMessageRecordMapper, MessageRecord>
        implements MessageRecordService {

    private final AdminMessageRecordMapper adminMessageRecordMapper;

    @Override
    public List<MessageRecord> getTemplateInfo(LocalDateTime startTime, LocalDateTime endTime, Integer size) {
        return adminMessageRecordMapper.getTemplateInfo(startTime, endTime, size);
    }

    @Override
    public List<MessageRecord> getAppInfo(LocalDateTime startTime, LocalDateTime endTime, Integer size) {
        return adminMessageRecordMapper.getAppInfo(startTime, endTime, size);
    }

    @Override
    public List<MessageRecord> getPushUserInfo(LocalDateTime startTime, LocalDateTime endTime, Integer size) {
        return adminMessageRecordMapper.getPushUserInfo(startTime, endTime, size);
    }

}




