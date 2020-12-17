package com.tofunmi.passwordmanager.vault.key;

import com.tofunmi.passwordmanager.device.Device;
import com.tofunmi.passwordmanager.device.DeviceService;
import com.tofunmi.passwordmanager.vault.Vault;
import com.tofunmi.passwordmanager.vault.VaultService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * Created By tofunmi on 28/11/2020
 */
@Service
public class VaultKeyService {
    private final VaultKeyRepository repository;
    private final VaultService vaultService;
    private final DeviceService deviceService;

    public VaultKeyService(VaultKeyRepository repository, VaultService vaultService, DeviceService deviceService) {
        this.repository = repository;
        this.vaultService = vaultService;
        this.deviceService = deviceService;
    }

    public String create(CreateVaultKeyRequestBody requestBody) {
        Device device = deviceService.findById(requestBody.getDeviceId()).
                orElseThrow(() -> new IllegalArgumentException("Could not find device for id " + requestBody.getDeviceId()));
        Assert.isTrue(Objects.equals(requestBody.getUserId(), device.getUser().getId()), "User Id does not match device");

        Vault vault = vaultService.findById(requestBody.getVaultId()).
                orElseThrow(() -> new IllegalArgumentException("Could not find vault for id " + requestBody.getVaultId()));

        VaultKey vaultKey = new VaultKey();
        vaultKey.setDevice(device);
        vaultKey.setVault(vault);
        vaultKey.setEncryptedVaultKey(requestBody.getEncryptedVaultKey());

        return repository.save(vaultKey).getId();
    }

    public void edit(String id, String newEncryptedVaultKey) {
        VaultKey vaultKey = repository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Could not find vault key for id " + id));
        vaultKey.setEncryptedVaultKey(newEncryptedVaultKey);
        repository.save(vaultKey);
    }

    public void delete(String id) {
        Assert.isTrue(repository.existsById(id), "Vault key not found for id " + id);
        repository.deleteById(id);
    }
}
