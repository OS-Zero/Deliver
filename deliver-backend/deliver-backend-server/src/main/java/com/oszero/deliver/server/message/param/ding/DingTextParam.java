package com.oszero.deliver.server.message.param.ding;

import lombok.*;

/**
 * 钉钉文本消息内容
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DingTextParam extends DingMessageParam {

    private TextMessage msg;

    @Data
    static
    class TextMessage {
        private String msgtype;
        private Text text;
    }

    @Data
    static
    class Text {
        private String content;
    }
}
