package com.tofunmi.passwordmanager.vault.record;

import com.tofunmi.passwordmanager.BaseEntity;
import com.tofunmi.passwordmanager.user.User;
import com.tofunmi.passwordmanager.vault.Vault;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

/**
 * Created By tofunmi on 28/11/2020
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VaultRecord extends BaseEntity {
    @ManyToOne
    private Vault vault;
    @ManyToOne
    private User createdBy;
    @Column(nullable = false)
    private String name;
    private String url;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
}
