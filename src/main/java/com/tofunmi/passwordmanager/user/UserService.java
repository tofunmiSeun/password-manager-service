package com.tofunmi.passwordmanager.user;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Created By tofunmi on 15/12/2020
 */
@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public String create(String emailAddress) {
        Assert.hasText(emailAddress, "Email address is not valid");
        if (repository.findByEmailAddress(emailAddress).isPresent()) {
            throw new IllegalStateException("Email address already present for a user");
        }
        User user = new User();
        user.setEmailAddress(emailAddress);
        return repository.save(user).getId();
    }

    public Optional<User> findById(String id) {
        return repository.findById(id);
    }

    public void delete(String id) {
        Assert.isTrue(repository.existsById(id), "User not present for ID " + id);
        deleteForUser(id);
        repository.deleteById(id);
    }

    private void deleteForUser(String userId) {

    }
}
