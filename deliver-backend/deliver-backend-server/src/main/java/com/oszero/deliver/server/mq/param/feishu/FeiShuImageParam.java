package com.oszero.deliver.server.mq.param.feishu;

import lombok.*;

/**
 * 飞书图片消息内容
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FeiShuImageParam extends FeiShuMessageParam {

    private Content content;

    @Data
    public static class Content {
        private String image_key;
    }
}
