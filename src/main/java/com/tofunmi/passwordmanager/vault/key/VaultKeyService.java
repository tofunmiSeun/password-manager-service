package com.tofunmi.passwordmanager.vault.key;

import com.tofunmi.passwordmanager.device.Device;
import com.tofunmi.passwordmanager.device.DeviceService;
import com.tofunmi.passwordmanager.user.User;
import com.tofunmi.passwordmanager.user.UserService;
import com.tofunmi.passwordmanager.vault.Vault;
import com.tofunmi.passwordmanager.vault.VaultRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

/**
 * Created By tofunmi on 28/11/2020
 */
@Service
public class VaultKeyService {
    private final VaultKeyRepository repository;
    private final VaultRepository vaultRepository;
    private final DeviceService deviceService;
    private final UserService userService;

    public VaultKeyService(VaultKeyRepository repository, VaultRepository vaultRepository, DeviceService deviceService, UserService userService) {
        this.repository = repository;
        this.vaultRepository = vaultRepository;
        this.deviceService = deviceService;
        this.userService = userService;
    }

    public String create(CreateVaultKeyRequestBody requestBody, Principal principal) {
        User user = userService.findByPrincipal(principal);
        Device device = getDevice(requestBody.getDeviceId());
        validateUserOwnsDevice(user, device);

        Vault vault = getVault(requestBody.getVaultId());
        Assert.isTrue(!repository.findByVaultAndDevice(vault, device).isPresent(), "Device already has access to vault");

        VaultKey vaultKey = new VaultKey();
        vaultKey.setDevice(device);
        vaultKey.setVault(vault);
        vaultKey.setEncryptedVaultKey(requestBody.getEncryptedVaultKey());

        return repository.save(vaultKey).getId();
    }

    public List<VaultKey> getForDevice(String deviceId, Principal principal) {
        User user = userService.findByPrincipal(principal);
        Device device = getDevice(deviceId);
        validateUserOwnsDevice(user, device);
        return repository.findAllByDevice(device);
    }

    public VaultKeyViewModel findForVaultAndDevice(String vaultId, String deviceId, Principal principal) {
        User user = userService.findByPrincipal(principal);
        Device device = getDevice(deviceId);
        validateUserOwnsDevice(user, device);

        Vault vault = getVault(vaultId);

        return repository.findByVaultAndDevice(vault, device)
                .map(vaultKey -> VaultKeyViewModel.builder()
                        .encryptedVaultKey(vaultKey.getEncryptedVaultKey())
                        .build())
                .orElseThrow(() -> new IllegalArgumentException("Could not find vault key for device & vault "));
    }

    public void validateUserHasAccessToVault(Vault vault, User user) {
        Assert.isTrue(repository.existsByVaultAndDevice_user(vault, user), "User has no access to vault");
    }

    private void validateUserOwnsDevice(User user, Device device) {
        Assert.isTrue(Objects.equals(user.getId(), device.getUser().getId()), "User does not own device");
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

    private Device getDevice (String deviceId) {
        return deviceService.findById(deviceId).
                orElseThrow(() -> new IllegalArgumentException("Could not find device for id " + deviceId));
    }

    private Vault getVault (String vaultId) {
        return vaultRepository.findById(vaultId).
                orElseThrow(() -> new IllegalArgumentException("Could not find vault for id " + vaultId));
    }

}
