package com.oszero.deliver.server.message.param.feishu;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FeiShuShareChatParam extends FeiShuMessageParam {

    private Content content;

    public static class Content {
        private String chat_id;
    }
}
