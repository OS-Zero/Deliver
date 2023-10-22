package com.oszero.deliver.server.message.param.feishu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeiShuMessageParam {
    private String msg_type;
    private List<String> user_ids;
}
