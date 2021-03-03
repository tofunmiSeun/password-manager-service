package com.tofunmi.passwordmanager.vault.record;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Created By tofunmi on 28/02/2021
 */

@RestController
@RequestMapping(value = "vault-record")
public class VaultRecordController {

    private final VaultRecordService service;

    public VaultRecordController(VaultRecordService service) {
        this.service = service;
    }

    @PostMapping
    public String create(@RequestBody CreateRecordRequestBody requestBody, Principal principal) {
        return service.create(requestBody, principal);
    }

    @GetMapping("for-vault/{vaultId}")
    public List<VaultRecordViewModel> forVault(@PathVariable String vaultId, Principal principal) {
        return service.findForVault(vaultId, principal);
    }
}
