package com.tofunmi.passwordmanager.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * Created By tofunmi on 15/12/2020
 */
@Service
@Slf4j
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public String create(String emailAddress, String password) {
        Assert.hasText(emailAddress, "Email address is not valid");
        Assert.hasText(password, "Password not provided");

        if (repository.findByEmailAddress(emailAddress).isPresent()) {
            throw new IllegalStateException("Email address already present for a user");
        }
        User user = new User();
        user.setEmailAddress(emailAddress);

        String salt = createSalt();
        String hashedPassword = hashText(password, salt);

        user.setPasswordSalt(salt);
        user.setHashedPassword(hashedPassword);

        return repository.save(user).getId();
    }

    public Optional<User> findById(String id) {
        return repository.findById(id);
    }

    public User findByPrincipal(Principal principal) {
        return repository.findByEmailAddress(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Could not find user for principal " + principal.getName()));
    }

    public Optional<User> findByEmailAddress(String emailAddress) {
        return repository.findByEmailAddress(emailAddress);
    }

    public boolean passwordMatches(User user, String password) {
        return Objects.equals(hashText(password, user.getPasswordSalt()), user.getHashedPassword());
    }

    public void delete(String id) {
        Assert.isTrue(repository.existsById(id), "User not present for ID " + id);
        deleteForUser(id);
        repository.deleteById(id);
    }

    private void deleteForUser(String userId) {

    }

    private static String createSalt() {
        Random r = new SecureRandom();
        byte[] salt = new byte[16];
        r.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private static String hashText(String textToHash, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(textToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("Password hashing failed", e);
            throw new IllegalStateException("Server error");
        }
    }
}
