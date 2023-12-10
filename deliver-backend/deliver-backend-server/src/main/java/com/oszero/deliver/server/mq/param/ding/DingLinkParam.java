package com.oszero.deliver.server.mq.param.ding;

import lombok.*;

/**
 * 钉钉链接消息内容
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DingLinkParam extends DingMessageParam {


    private LinkMessage msg;

    @Data
    public static class LinkMessage {
        private String msgtype;
        private Link link;
    }

    @Data
    public static class Link {
        private String messageUrl;
        private String picUrl;
        private String title;
        private String text;


    }

}
