package com.tofunmi.passwordmanager.vault.key;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By tofunmi on 28/11/2020
 */
public interface VaultKeyRepository extends JpaRepository<VaultKey, String> {
}
