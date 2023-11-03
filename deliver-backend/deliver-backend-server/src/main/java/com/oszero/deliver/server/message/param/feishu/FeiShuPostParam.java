package com.oszero.deliver.server.message.param.feishu;

import lombok.*;

import java.util.List;

/**
 * 飞书富文本消息内容
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FeiShuPostParam extends FeiShuMessageParam {

    private Content content;

    @Data
    public static class Content {
        private Post post;
    }

    @Data
    public static class Post {
        private ZhCn zh_cn;
        private ZhCn en_us;
    }

    @Data
    public static class ZhCn {
        private String title;
        private List<Object> content;
    }
}
