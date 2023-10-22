package com.oszero.deliver.server.model.app;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeiShuApp extends AppConfig {
    private String appId;
    private String appSecret;
}
