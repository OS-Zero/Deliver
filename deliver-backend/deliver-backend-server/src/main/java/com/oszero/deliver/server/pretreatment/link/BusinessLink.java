package com.oszero.deliver.server.pretreatment.link;

/**
 * 前置处理责任链抽象接口
 *
 * @author oszero
 * @version 1.0.0
 */
public interface BusinessLink<T extends LinkModel> {

    /**
     * 真正处理逻辑
     *
     * @param context 处理上下文
     */
    void process(LinkContext<T> context);
}
