package com.tofunmi.passwordmanager.user;

import lombok.Data;

/**
 * Created By tofunmi on 16/12/2020
 */
@Data
public class NewUserRequestBody {
    private String emailAddress;
    private String password;
}
