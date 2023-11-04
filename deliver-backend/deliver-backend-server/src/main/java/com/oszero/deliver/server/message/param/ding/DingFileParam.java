package com.oszero.deliver.server.message.param.ding;

import cn.hutool.core.annotation.Alias;
import lombok.*;

/**
 * 钉钉文件消息
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DingFileParam extends DingMessageParam {

    private FileMessage msg;

    @Data
    public static class FileMessage {
        private String msgtype;
        private FileContent file;

    }

    @Data
    public static class FileContent {
        @Alias("media_id")
        private String mediaId;
    }
}
