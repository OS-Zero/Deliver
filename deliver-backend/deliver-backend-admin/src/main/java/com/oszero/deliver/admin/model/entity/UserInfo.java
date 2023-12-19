package com.oszero.deliver.admin.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息表
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    /**
     * 用户 ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户真实姓名
     */
    private String realName;
}
