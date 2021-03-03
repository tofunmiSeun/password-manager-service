package com.tofunmi.passwordmanager.vault.key;

import com.tofunmi.passwordmanager.BaseEntity;
import com.tofunmi.passwordmanager.device.Device;
import com.tofunmi.passwordmanager.vault.Vault;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created By tofunmi on 28/11/2020
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class VaultKey extends BaseEntity {
    @ManyToOne
    private Vault vault;
    @ManyToOne
    private Device device;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String encryptedVaultKey;
}
