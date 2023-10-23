package com.oszero.deliver.server.model.app;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailApp extends AppConfig {
    private String host;
    private String username;
    private String password;
}
