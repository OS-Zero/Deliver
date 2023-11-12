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
