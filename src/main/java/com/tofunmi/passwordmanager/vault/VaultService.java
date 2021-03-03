package com.tofunmi.passwordmanager.vault;

import com.tofunmi.passwordmanager.user.User;
import com.tofunmi.passwordmanager.user.UserService;
import com.tofunmi.passwordmanager.vault.key.CreateVaultKeyRequestBody;
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

    public List<VaultViewModel> getAll(Principal principal) {
        User user = userService.findByPrincipal(principal);
        return repository.findByCreatedBy(user).stream()
                .map(v -> VaultViewModel.builder()
                        .id(v.getId())
                        .name(v.getName())
                        .createdAt(v.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public Optional<Vault> findById(String id) {
        return repository.findById(id);
    }
}
