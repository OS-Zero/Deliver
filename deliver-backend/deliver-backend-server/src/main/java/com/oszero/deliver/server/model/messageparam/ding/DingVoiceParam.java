package com.oszero.deliver.server.model.messageparam.ding;

import lombok.*;

/**
 * 钉钉语言消息内容
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DingVoiceParam extends DingMessageParam {

    private VoiceMessage msg;


    @Data
    public static class VoiceMessage {
        private String msgtype;
        private Voice voice;
    }

    @Data
    public static class Voice {
        private String media_id;
        private String duration;
    }
}
