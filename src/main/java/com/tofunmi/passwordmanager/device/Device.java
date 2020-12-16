package com.tofunmi.passwordmanager.device;

import com.tofunmi.passwordmanager.BaseEntity;
import com.tofunmi.passwordmanager.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created By tofunmi on 28/11/2020
 */

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Device extends BaseEntity {
    @ManyToOne
    private User user;
    @Column(nullable = false)
    private String publicKey;
    @Column(nullable = false)
    private String encryptedPrivateKey;
    @Column(nullable = false)
    private String mukSalt;
}
