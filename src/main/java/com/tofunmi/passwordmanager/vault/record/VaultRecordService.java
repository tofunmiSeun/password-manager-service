package com.tofunmi.passwordmanager.vault.record;

import com.tofunmi.passwordmanager.user.User;
import com.tofunmi.passwordmanager.user.UserService;
import com.tofunmi.passwordmanager.vault.Vault;
import com.tofunmi.passwordmanager.vault.VaultService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created By tofunmi on 28/11/2020
 */

@Service
public class VaultRecordService {
    private final VaultRecordRepository repository;
    private final VaultService vaultService;
    private final UserService userService;

    public VaultRecordService(VaultRecordRepository repository, VaultService vaultService, UserService userService) {
        this.repository = repository;
        this.vaultService = vaultService;
        this.userService = userService;
    }

    public String create(CreateRecordRequestBody requestBody) {
        Vault vault = vaultService.findById(requestBody.getVaultId())
                .orElseThrow(() -> new IllegalArgumentException("Could not find vault for id " + requestBody.getVaultId()));
        User user = userService.findById(requestBody.getVaultId())
                .orElseThrow(() -> new IllegalArgumentException("Could not find user for id " + requestBody.getVaultId()));

        VaultRecord vaultRecord = new VaultRecord();
        vaultRecord.setVault(vault);
        vaultRecord.setCreatedBy(user);
        vaultRecord.setName(requestBody.getName());
        vaultRecord.setUrl(requestBody.getUrl());
        vaultRecord.setUsername(requestBody.getUsername());
        vaultRecord.setPassword(requestBody.getPassword());

        return repository.save(vaultRecord).getId();
    }

    public List<VaultRecord> getAll() {
        return repository.findAll();
    }


    public void update(String id, EditRecordRequestBody update) {
        VaultRecord vaultRecord = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Could not find vault record for id " + id));

        vaultRecord.setName(update.getName());
        vaultRecord.setUrl(update.getUrl());
        vaultRecord.setUsername(update.getUsername());
        vaultRecord.setPassword(update.getPassword());

        repository.save(vaultRecord);
    }

    public void delete(String id) {
        Assert.isTrue(repository.existsById(id), "Vault record not found for id " + id);
        repository.deleteById(id);
    }
}
