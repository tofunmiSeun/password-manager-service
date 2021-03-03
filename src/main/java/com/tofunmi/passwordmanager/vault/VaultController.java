package com.tofunmi.passwordmanager.vault;

import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public String create(@RequestBody CreateVaultRequestBody requestBody, Principal principal) {
        return service.create(requestBody, principal);
    }

    @GetMapping("for-device/{deviceId}")
    public List<VaultViewModel> forDevice(@PathVariable String deviceId, Principal principal) {
        return service.forDevice(deviceId, principal);
    }
}
