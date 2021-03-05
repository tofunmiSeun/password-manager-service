package com.tofunmi.passwordmanager.vault.record;

import com.tofunmi.passwordmanager.user.User;
import com.tofunmi.passwordmanager.user.UserService;
import com.tofunmi.passwordmanager.vault.Vault;
import com.tofunmi.passwordmanager.vault.VaultService;
import com.tofunmi.passwordmanager.vault.key.VaultKeyService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created By tofunmi on 28/11/2020
 */

@Service
public class VaultRecordService {
    private final VaultRecordRepository repository;
    private final VaultService vaultService;
    private final UserService userService;
    private final VaultKeyService vaultKeyService;

    public VaultRecordService(VaultRecordRepository repository, VaultService vaultService, UserService userService, VaultKeyService vaultKeyService) {
        this.repository = repository;
        this.vaultService = vaultService;
        this.userService = userService;
        this.vaultKeyService = vaultKeyService;
    }

    public String create(String vaultId, SubmitRecordRequestBody requestBody, Principal principal) {
        Vault vault = getVault(vaultId);
        User user = userService.findByPrincipal(principal);
        vaultKeyService.validateUserHasAccessToVault(vault, user);

        VaultRecord vaultRecord = new VaultRecord();
        vaultRecord.setVault(vault);
        vaultRecord.setCreatedBy(user);
        vaultRecord.setName(requestBody.getName());
        vaultRecord.setUrl(requestBody.getUrl());
        vaultRecord.setUsername(requestBody.getUsername());
        vaultRecord.setPassword(requestBody.getPassword());

        return repository.save(vaultRecord).getId();
    }

    public List<VaultRecordViewModel> findForVault(String vaultId, Principal principal) {
        Vault vault = getVault(vaultId);
        User user = userService.findByPrincipal(principal);
        vaultKeyService.validateUserHasAccessToVault(vault, user);

        return repository.findAllByVault(vault, Sort.by(Sort.Direction.DESC, "lastUpdateTime")).stream()
                .map(this::toViewModel)
                .collect(Collectors.toList());
    }

    public void update(String id, SubmitRecordRequestBody update, Principal principal) {
        VaultRecord vaultRecord = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Could not find vault record for id " + id));

        User user = userService.findByPrincipal(principal);
        vaultKeyService.validateUserHasAccessToVault(vaultRecord.getVault(), user);

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

    private Vault getVault(String vaultId) {
        return vaultService.findById(vaultId)
                .orElseThrow(() -> new IllegalArgumentException("Could not find vault for id " + vaultId));
    }

    private VaultRecordViewModel toViewModel(VaultRecord record) {
        return VaultRecordViewModel.builder()
                .id(record.getId())
                .vaultId(record.getVault().getId())
                .vaultName(record.getVault().getName())
                .createdBy(record.getCreatedBy().getName())
                .name(record.getName())
                .url(record.getUrl())
                .username(record.getUsername())
                .password(record.getPassword())
                .lastModifiedAt(record.getLastUpdateTime())
                .build();
    }
}
