package com.oszero.deliver.server.pretreatment.common;

import com.oszero.deliver.server.model.dto.SendTaskDto;
import lombok.Data;

import java.util.List;

/**
 * 责任链模板（把链接起来）
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class LinkTemplate {

    private List<MessageLink<SendTaskDto>> processList;
}