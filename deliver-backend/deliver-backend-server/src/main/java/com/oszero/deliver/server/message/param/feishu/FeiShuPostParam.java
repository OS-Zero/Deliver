package com.oszero.deliver.server.message.param.feishu;

import lombok.*;

import java.util.List;

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
