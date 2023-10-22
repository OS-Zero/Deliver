package com.oszero.deliver.server.pretreatment.link;

public interface BusinessLink<T extends LinkModel> {

    /**
     * 真正处理逻辑
     *
     * @param context 处理上下文
     */
    void process(LinkContext<T> context);
}
