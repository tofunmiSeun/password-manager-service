package com.tofunmi.passwordmanager.vault.key;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created By tofunmi on 28/02/2021
 */

@RestController
@RequestMapping(value = "vault-key")
public class VaultKeyController {

    private final VaultKeyService service;

    public VaultKeyController(VaultKeyService service) {
        this.service = service;
    }

    @GetMapping("for-vault/{vaultId}/for-device/{deviceId}")
    public VaultKeyViewModel forVaultAndDevice(@PathVariable String vaultId, @PathVariable String deviceId, Principal principal) {
        return service.findForVaultAndDevice(vaultId, deviceId, principal);
    }
}
