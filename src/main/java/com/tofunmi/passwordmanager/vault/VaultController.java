package com.tofunmi.passwordmanager.vault;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * Created By tofunmi on 28/02/2021
 */

@RestController
@RequestMapping(value = "vault")
public class VaultController {

    private final VaultService service;

    public VaultController(VaultService service) {
        this.service = service;
    }

    @GetMapping
    public List<Vault> getAll(Principal principal) {
        return service.getAll(principal);
    }
}
