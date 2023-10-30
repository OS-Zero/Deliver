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

    public static class Content {
        private ZhCn zh_cn;
    }

    public static class ZhCn {
        private String title;
        private List<List<Object>> content;
    }
}
