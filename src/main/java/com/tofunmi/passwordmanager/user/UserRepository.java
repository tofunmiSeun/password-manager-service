package com.tofunmi.passwordmanager.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created By tofunmi on 15/12/2020
 */
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmailAddress(String emailAddress);
}
