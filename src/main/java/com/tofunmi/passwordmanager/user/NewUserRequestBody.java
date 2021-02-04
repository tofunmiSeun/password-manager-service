package com.tofunmi.passwordmanager.user;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * Created By tofunmi on 16/12/2020
 */
@Data
public class NewUserRequestBody {
    @NotEmpty(message = "Name is required")
    private String name;
    @Email(message = "Invalid email format")
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
