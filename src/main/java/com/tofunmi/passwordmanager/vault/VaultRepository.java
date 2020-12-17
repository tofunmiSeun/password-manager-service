package com.tofunmi.passwordmanager.vault;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By tofunmi on 28/11/2020
 */
public interface VaultRepository extends JpaRepository<Vault, String> {
}
