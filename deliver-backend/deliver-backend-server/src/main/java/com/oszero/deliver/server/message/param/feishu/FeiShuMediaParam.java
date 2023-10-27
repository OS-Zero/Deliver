package com.oszero.deliver.server.message.param.feishu;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FeiShuMediaParam extends FeiShuMessageParam {

    private Content content;

    public static class Content {
        private String file_key;
        private String image_key;
    }
}
