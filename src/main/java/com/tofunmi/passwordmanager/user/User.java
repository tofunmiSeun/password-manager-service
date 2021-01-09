package com.tofunmi.passwordmanager.user;

import com.tofunmi.passwordmanager.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created By tofunmi on 28/11/2020
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(nullable = false)
    private String emailAddress;
    @Column(nullable = false)
    private String passwordSalt;
    @Column(nullable = false)
    private String hashedPassword;
}
