package com.oszero.deliver.server.pretreatment.pipeline;

public interface BusinessProcess<T extends ProcessModel> {
    /**
     * 真正处理逻辑
     *
     * @param context
     */
    void process(ProcessContext<T> context);
}
