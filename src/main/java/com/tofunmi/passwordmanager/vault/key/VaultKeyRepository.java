package com.tofunmi.passwordmanager.vault.key;

import com.tofunmi.passwordmanager.device.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By tofunmi on 28/11/2020
 */
public interface VaultKeyRepository extends JpaRepository<VaultKey, String> {
    List<VaultKey> findAllByDevice(Device device);
}
