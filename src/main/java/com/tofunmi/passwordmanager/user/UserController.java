package com.tofunmi.passwordmanager.user;

import org.springframework.web.bind.annotation.*;

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
    public String create(@RequestParam NewUserRequestBody requestBody) {
        return service.create(requestBody.getEmailAddress(), requestBody.getPassword());
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
         service.delete(id);
    }
}
