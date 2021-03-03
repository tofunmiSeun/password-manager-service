package com.tofunmi.passwordmanager.vault.record;

import com.tofunmi.passwordmanager.vault.Vault;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By Tofunmi on 11/24/2018
 */
public interface VaultRecordRepository extends JpaRepository<VaultRecord, String> {
    List<VaultRecord> findAllByVault(Vault vault);
}

