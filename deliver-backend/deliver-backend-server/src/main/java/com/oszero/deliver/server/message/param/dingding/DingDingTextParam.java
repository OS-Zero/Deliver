package com.oszero.deliver.server.message.param.dingding;

import lombok.*;

/**
 * @author  oszero
 * @Date 2023/10/25
 * @Description    钉钉文本消息内容
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DingDingTextParam extends DingDingMessageParam{

    private TextMessage msg;

    @Data
    class TextMessage{
        private String msgtype;
        private Text  text;
    }

    @Data
    class Text{
        private String content;
    }
}
