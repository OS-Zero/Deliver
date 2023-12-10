package com.oszero.deliver.server.mq.param.feishu;

import lombok.*;

/**
 * 飞书文本消息内容
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FeiShuTextParam extends FeiShuMessageParam {

    private Content content;

    @Data
    public static class Content {
        private String text;
    }
}
