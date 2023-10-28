package com.oszero.deliver.server.message.param.ding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 钉钉文件消息
 * @author oszero
 * @@version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DingFileparam extends DingMessageParam{
    private FileMessage msg;


    public static class FileMessage{
         private String msgtype;
         private FileContent file;

    }

    public static class FileContent{
        private String media_id;
    }
}
