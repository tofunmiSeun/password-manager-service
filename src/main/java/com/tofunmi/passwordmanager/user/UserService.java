package com.tofunmi.passwordmanager.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.*;

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

    public UserTokenResponse create(String name, String emailAddress, String password) {
        Assert.hasText(name, "Name is not valid");
        Assert.hasText(emailAddress, "Email address is not valid");
        Assert.hasText(password, "Password not provided");
        Assert.isTrue(password.length() >= 8, "Password must have at least 8 characters");

        if (repository.findByEmailAddress(emailAddress).isPresent()) {
            throw new IllegalStateException("Email address already present for a user");
        }
        User user = new User();
        user.setName(name);
        user.setEmailAddress(emailAddress);

        String salt = createSalt();
        String hashedPassword = hashText(password, salt);

        user.setPasswordSalt(salt);
        user.setHashedPassword(hashedPassword);

        repository.save(user);
        return new UserTokenResponse(user.getId(), name, emailAddress, createUserToken(emailAddress, password));
    }

    public UserTokenResponse login(String emailAddress, String password) {
        Assert.hasText(emailAddress, "Email address is not valid");
        Assert.hasText(password, "Password not provided");

        Optional<User> user = findByEmailAddress(emailAddress);
        if (user.isPresent() && passwordMatches(user.get(), password)) {
            return new UserTokenResponse(user.get().getId(), user.get().getName(), emailAddress, createUserToken(emailAddress, password));
        } else {
            throw new IllegalArgumentException("Invalid email address / password");
        }
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

    private static String createUserToken(String emailAddress, String password) {
        return Base64.getEncoder().encodeToString(String.format("%s:%s", emailAddress, password).getBytes());
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
