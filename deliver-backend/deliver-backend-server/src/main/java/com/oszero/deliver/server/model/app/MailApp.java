package com.oszero.deliver.server.model.app;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailApp {
    private String host;
    private Integer port;
    private String username;
    private String password;
}
