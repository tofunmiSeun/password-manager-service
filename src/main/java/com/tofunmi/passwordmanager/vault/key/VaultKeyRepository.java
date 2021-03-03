package com.tofunmi.passwordmanager.vault.key;

import com.tofunmi.passwordmanager.device.Device;
import com.tofunmi.passwordmanager.user.User;
import com.tofunmi.passwordmanager.vault.Vault;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By tofunmi on 28/11/2020
 */
public interface VaultKeyRepository extends JpaRepository<VaultKey, String> {
    List<VaultKey> findAllByDevice(Device device);
    boolean existsByVaultAndDevice_user(Vault vault, User user);
}
