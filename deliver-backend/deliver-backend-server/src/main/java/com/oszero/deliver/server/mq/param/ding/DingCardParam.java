package com.oszero.deliver.server.mq.param.ding;

import cn.hutool.core.annotation.Alias;
import lombok.*;

/**
 * 钉钉卡片消息内容
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DingCardParam extends DingMessageParam {

    private DingCardMessage msg;

    @Data
    public static class DingCardMessage {
        @Alias("action_card")
        private ActionCardDTO actionCard;
        private String msgtype;
    }

    @Data
    public static class ActionCardDTO {
        @Alias("btn_json_list")
        private BtnJsonListDTO btnJsonList;
        @Alias("single_url")
        private String singleUrl;
        @Alias("btn_orientation")
        private String btnOrientation;
        @Alias("single_title")
        private String singleTitle;

        private String markdown;

        private String title;


        @Data
        public static class BtnJsonListDTO {
            @Alias("action_url")
            private String actionUrl;

            private String title;
        }
    }
}
