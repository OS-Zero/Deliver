package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;

@Data
public abstract class PageRequest {

    /**
     * 当前页
     */
    private Integer currentPage;

    /**
     * 页大小
     */
    private Integer pageSize;
}
