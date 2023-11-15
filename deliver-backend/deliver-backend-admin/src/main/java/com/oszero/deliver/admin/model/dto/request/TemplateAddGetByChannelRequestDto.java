package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TemplateAddGetByChannelRequestDto {
    @NotNull
    private Integer channelType;
}
