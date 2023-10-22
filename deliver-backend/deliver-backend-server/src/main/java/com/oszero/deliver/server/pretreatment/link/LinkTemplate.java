package com.oszero.deliver.server.pretreatment.link;

import lombok.Data;

import java.util.List;

/**
 * 业务执行模板（把责任链的逻辑串起来）
 */
@SuppressWarnings("all")
@Data
public class LinkTemplate {

    private List<BusinessLink> processList;
}