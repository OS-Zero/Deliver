package com.oszero.deliver.server.message.param.ding;

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


    public static class FileMessage {
        private String msgtype;
        private FileContent file;

    }

    public static class FileContent {
        private String media_id;
    }
}
