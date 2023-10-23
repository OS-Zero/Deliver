package com.oszero.deliver.server.message.param.feishu;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeiShuTextParam extends FeiShuMessageParam {

    private Text text;

    public static class Text {
        private String text;
    }
}
