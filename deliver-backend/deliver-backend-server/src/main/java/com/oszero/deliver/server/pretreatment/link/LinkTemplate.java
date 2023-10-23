package com.oszero.deliver.server.pretreatment.link;

import lombok.Data;

import java.util.List;

/**
 * 责任链模板（把链接起来）
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@SuppressWarnings("all")
public class LinkTemplate {

    private List<BusinessLink> processList;
}