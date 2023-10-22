package com.oszero.deliver.server.message.param.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailParam {
    private String title;
    private String content;
    // 抄送人列表
    private List<String> toCC;
    // 密送人列表
    private List<String> toBCC;
    private boolean htmlFlag = true;
}
