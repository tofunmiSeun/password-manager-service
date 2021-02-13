package com.tofunmi.passwordmanager.user;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created By tofunmi on 28/01/2021
 */
@Data
@AllArgsConstructor
public class UserTokenResponse {
    private String userId;
    private String name;
    private String email;
    private String token;
}
