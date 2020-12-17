package com.tofunmi.passwordmanager.vault;

import com.tofunmi.passwordmanager.BaseEntity;
import com.tofunmi.passwordmanager.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created By tofunmi on 28/11/2020
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Vault extends BaseEntity {
    private String name;
    @ManyToOne
    private User createdBy;
}
