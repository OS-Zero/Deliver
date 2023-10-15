package com.oszero.deliver.server.pretreatment.pipeline;

import lombok.Data;

import java.util.List;

/**
 * 业务执行模板（把责任链的逻辑串起来）
 */
@SuppressWarnings("all")
@Data
public class ProcessTemplate {

    private List<BusinessProcess> processList;
}