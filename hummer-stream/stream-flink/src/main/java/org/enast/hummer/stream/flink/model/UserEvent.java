package org.enast.hummer.stream.flink.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEvent {
    /**
     * user Id
     */
    private Long id;

    /**
     * User Name
     */
    private String userName;

    /**
     * User email
     */
    private String email;

    /**
     * User phone number
     */
    private String phoneNumber;

    /**
     * User 真实姓名
     */
    private String realName;

    /**
     * User 展示名称
     */
    private String displayName;

    /**
     * User 头像 Url
     */
    private String avatarUrl;

    /**
     * User 密码（加密后）
     */
    private String password;

    /**
     * User 地址
     */
    private String address;

    /**
     * User 注册来源（1：IOS、2：PC、3：Android）
     */
    private int deviceSource;
}
