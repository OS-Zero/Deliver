package com.oszero.deliver.server.message.param.feishu;

import lombok.*;

/**
 * 飞书音频消息内容
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FeiShuAudioParam extends FeiShuMessageParam {

    private Content content;

    public static class Content {
        private String file_key;
    }
}
