package com.tofunmi.passwordmanager.vault;

import com.tofunmi.passwordmanager.user.User;
import com.tofunmi.passwordmanager.user.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created By tofunmi on 28/11/2020
 */
@Service
public class VaultService {
    private final VaultRepository repository;
    private final UserService userService;

    public VaultService(VaultRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public String create(CreateVaultRequestBody requestBody) {
        User user = userService.findById(requestBody.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not present for ID " + requestBody.getUserId()));
        Vault vault = new Vault();
        vault.setName(requestBody.getName());
        vault.setCreatedBy(user);
        return repository.save(vault).getId();
    }

    public Optional<Vault> findById(String id) {
        return repository.findById(id);
    }
}
