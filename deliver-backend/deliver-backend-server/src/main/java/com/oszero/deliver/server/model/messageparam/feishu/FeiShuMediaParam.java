package com.oszero.deliver.server.model.messageparam.feishu;

import lombok.*;

/**
 * 飞书视频消息内容
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FeiShuMediaParam extends FeiShuMessageParam {

    private Content content;

    @Data
    public static class Content {
        private String file_key;
        private String image_key;
    }
}
