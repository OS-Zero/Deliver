package com.oszero.deliver.admin.controller;

import com.oszero.deliver.admin.model.common.CommonResult;
import com.oszero.deliver.admin.model.dto.request.DeleteIdsRequestDto;
import com.oszero.deliver.admin.model.dto.request.GroupSaveAndUpdateRequestDto;
import com.oszero.deliver.admin.model.dto.response.GroupGetAllResponseDto;
import com.oszero.deliver.admin.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分组控制器
 *
 * @author oszero
 * @version 1.0.0
 */
@Validated
@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/getAll")
    CommonResult<List<GroupGetAllResponseDto>> getAll() {
        return CommonResult.success(groupService.getAll());
    }

    @PostMapping("/save")
    CommonResult<?> saveGroup(@RequestBody GroupSaveAndUpdateRequestDto dto) {
        groupService.saveGroup(dto);
        return CommonResult.success();
    }

    @PostMapping("/update")
    CommonResult<?> updateGroup(@RequestBody GroupSaveAndUpdateRequestDto dto) {
        groupService.updateGroup(dto);
        return CommonResult.success();
    }

    @PostMapping("/deleteOne")
    CommonResult<?> deleteGroup(@Valid @RequestBody DeleteIdsRequestDto dto) {
        groupService.deleteGroup(dto.getIds().get(0));
        return CommonResult.success();
    }
}
