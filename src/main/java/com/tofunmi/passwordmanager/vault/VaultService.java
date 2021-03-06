package com.tofunmi.passwordmanager.vault;

import com.tofunmi.passwordmanager.user.User;
import com.tofunmi.passwordmanager.user.UserService;
import com.tofunmi.passwordmanager.vault.key.CreateVaultKeyRequestBody;
import com.tofunmi.passwordmanager.vault.key.VaultKey;
import com.tofunmi.passwordmanager.vault.key.VaultKeyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created By tofunmi on 28/11/2020
 */
@Service
public class VaultService {
    private final VaultRepository repository;
    private final UserService userService;
    private final VaultKeyService vaultKeyService;

    public VaultService(VaultRepository repository, UserService userService, VaultKeyService vaultKeyService) {
        this.repository = repository;
        this.userService = userService;
        this.vaultKeyService = vaultKeyService;
    }

    @Transactional
    public String create(CreateVaultRequestBody requestBody, Principal principal) {
        User user = userService.findByPrincipal(principal);

        Vault vault = new Vault();
        vault.setName(requestBody.getName());
        vault.setCreatedBy(user);

        vault = repository.save(vault);

        CreateVaultKeyRequestBody createVaultKeyRequestBody = new CreateVaultKeyRequestBody();
        createVaultKeyRequestBody.setVaultId(vault.getId());
        createVaultKeyRequestBody.setDeviceId(requestBody.getDeviceId());
        createVaultKeyRequestBody.setEncryptedVaultKey(requestBody.getEncryptedVaultKey());

        vaultKeyService.create(createVaultKeyRequestBody, principal);

        return vault.getId();
    }

    public List<VaultViewModel> getCreatedByUser(Principal principal) {
        User user = userService.findByPrincipal(principal);
        return repository.findByCreatedBy(user).stream()
                .map(this::toViewModel)
                .collect(Collectors.toList());
    }

    public Optional<Vault> findById(String id) {
        return repository.findById(id);
    }

    public List<VaultViewModel> forDevice(String deviceId, Principal principal) {
        return vaultKeyService.getForDevice(deviceId, principal).stream()
                .map(VaultKey::getVault)
                .distinct()
                .map(this::toViewModel)
                .collect(Collectors.toList());
    }

    private VaultViewModel toViewModel(Vault v) {
        return VaultViewModel.builder()
                .id(v.getId())
                .name(v.getName())
                .createdBy(v.getCreatedBy().getName())
                .createdAt(v.getCreatedAt())
                .build();
    }

    public void edit(String id, EditVaultRequestBody requestBody, Principal principal) {
        Vault vault = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Could not find vault for id"));
        User user = userService.findByPrincipal(principal);

        vaultKeyService.validateUserHasAccessToVault(vault, user);

        vault.setName(requestBody.getName());
        repository.save(vault);
    }
}
