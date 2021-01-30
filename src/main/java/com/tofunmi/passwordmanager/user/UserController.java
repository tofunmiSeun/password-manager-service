package com.tofunmi.passwordmanager.user;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created By tofunmi on 15/12/2020
 */
@RestController
@RequestMapping(value = "user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("register")
    public UserTokenResponse create(@RequestBody @Valid NewUserRequestBody requestBody) {
        return service.create(requestBody.getName(), requestBody.getEmail(), requestBody.getPassword());
    }

    @PostMapping("login")
    public UserTokenResponse login(@RequestBody @Valid LoginUserRequestBody requestBody) {
        return service.login(requestBody.getEmail(), requestBody.getPassword());
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
