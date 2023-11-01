package com.oszero.deliver.server.message.param.ding;

import lombok.*;

/**
 * 钉钉图片消息内容
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DingImageParam extends DingMessageParam {
    private ImageMessage msg;

    @Data
    public static class ImageMessage {
        private String msgtype;
        private Image image;
    }

    @Data
    public static class Image {
        private String media_id;
    }

}
