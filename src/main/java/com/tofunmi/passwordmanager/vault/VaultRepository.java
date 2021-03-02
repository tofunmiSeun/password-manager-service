package com.tofunmi.passwordmanager.vault;

import com.tofunmi.passwordmanager.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By tofunmi on 28/11/2020
 */
public interface VaultRepository extends JpaRepository<Vault, String> {
    List<Vault> findByCreatedBy(User createdBy);
}
