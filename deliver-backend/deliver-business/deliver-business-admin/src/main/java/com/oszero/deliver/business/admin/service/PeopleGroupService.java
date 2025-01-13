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

package com.oszero.deliver.business.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oszero.deliver.business.admin.model.dto.request.common.DeleteIdsRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.peoplegroup.PeopleGroupSaveRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.peoplegroup.PeopleGroupSearchByNameRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.peoplegroup.PeopleGroupSearchRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.peoplegroup.PeopleGroupUpdateRequestDto;
import com.oszero.deliver.business.admin.model.dto.response.common.SearchResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.peoplegroup.PeopleGroupSearchByNameResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.peoplegroup.PeopleGroupSearchResponseDto;
import com.oszero.deliver.business.admin.model.entity.database.PeopleGroup;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author oszero
 * @version 1.0.0
 */
public interface PeopleGroupService extends IService<PeopleGroup> {
    SearchResponseDto<PeopleGroupSearchResponseDto> search(PeopleGroupSearchRequestDto dto);
    void save(PeopleGroupSaveRequestDto dto);
    void update(PeopleGroupUpdateRequestDto dto);
    void delete(DeleteIdsRequestDto dto);
    void getExcelTemplateFile();
    String analysisExcelTemplateFile(MultipartFile file) throws IOException;
    List<PeopleGroupSearchByNameResponseDto> searchByName(@Valid PeopleGroupSearchByNameRequestDto dto);
}
