package com.tofunmi.passwordmanager.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created By tofunmi on 15/12/2020
 */
@RestController
@RequestMapping(value = "users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public String create(@RequestParam String emailAddress) {
        return service.create(emailAddress);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
         service.delete(id);
    }
}
