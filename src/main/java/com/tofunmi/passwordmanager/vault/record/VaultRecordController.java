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

    @PostMapping("create/{vaultId}")
    public String create(@PathVariable String vaultId, @RequestBody SubmitRecordRequestBody requestBody, Principal principal) {
        return service.create(vaultId, requestBody, principal);
    }

    @GetMapping("for-vault/{vaultId}")
    public List<VaultRecordViewModel> forVault(@PathVariable String vaultId, Principal principal) {
        return service.findForVault(vaultId, principal);
    }

    @PostMapping("edit/{id}")
    public void edit(@PathVariable String id, @RequestBody SubmitRecordRequestBody requestBody, Principal principal) {
        service.update(id, requestBody, principal);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable String id, Principal principal) {
        service.delete(id, principal);
    }
}
