package com.oszero.deliver.server.message.param.ding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 钉钉图片消息内容
 * @author oszero
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DingImageParam extends DingMessageParam{
    private  ImageMessage   msg;

    public static class ImageMessage{
        private String msgtype;
        private Image image;
    }

    public static class Image{
        private String media_id;
    }

}
