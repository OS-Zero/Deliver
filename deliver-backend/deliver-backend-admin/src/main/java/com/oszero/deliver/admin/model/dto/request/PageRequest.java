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
     * 当前页
     */
    private Integer currentPage;

    /**
     * 页大小
     */
    private Integer pageSize;
}
