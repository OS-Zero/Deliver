package com.oszero.deliver.server.pretreatment.link;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 责任链上下文
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkContext<T extends LinkModel> {

    /**
     * 标识责任链的code
     */
    private String code;

    /**
     * 存储责任链上下文数据的模型
     */
    private T processModel;


}
