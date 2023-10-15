package com.oszero.deliver.server.pretreatment.pipeline;

public interface BusinessProcess<T extends ProcessModel> {

    /**
     * 真正处理逻辑
     *
     * @param context 处理上下文
     */
    void process(ProcessContext<T> context);
}
