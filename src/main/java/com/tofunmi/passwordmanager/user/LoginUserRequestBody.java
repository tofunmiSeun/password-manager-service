package com.tofunmi.passwordmanager.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Created By tofunmi on 16/12/2020
 */
@Data
public class LoginUserRequestBody {
    @Email(message = "Invalid email format")
    private String email;
    @NotEmpty(message = "Password not present")
    private String password;
}
