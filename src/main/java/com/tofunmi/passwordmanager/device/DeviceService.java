package com.tofunmi.passwordmanager.device;

import com.tofunmi.passwordmanager.user.User;
import com.tofunmi.passwordmanager.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Created By tofunmi on 16/12/2020
 */
@Service
public class DeviceService {

    private final DeviceRepository repository;
    private final UserService userService;

    public DeviceService(DeviceRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public String create(NewDeviceRequestBody requestBody) {
        Optional<User> userForDevice = userService.findById(requestBody.getUserId());
        Assert.isTrue(userForDevice.isPresent(), "User not present for id " + requestBody.getUserId());

        Device device = new Device();
        device.setUser(userForDevice.get());
        device.setPublicKey(requestBody.getPublicKey());
        device.setEncryptedPrivateKey(requestBody.getEncryptedPrivateKey());
        device.setMukSalt(requestBody.getMukSalt());

        return repository.save(device).getId();
    }

    public void delete(String id) {
        Assert.isTrue(repository.existsById(id), "Device not present for ID " + id);
        repository.deleteById(id);
    }

    public void updateCredentials(String id, UpdateDeviceRequestBody requestBody) {
        Optional<Device> optionalDevice = repository.findById(id);
        Assert.isTrue(optionalDevice.isPresent(), "Device not present for ID " + id);
        optionalDevice.ifPresent(device -> {
            device.setPublicKey(requestBody.getPublicKey());
            device.setEncryptedPrivateKey(requestBody.getEncryptedPrivateKey());
            device.setMukSalt(requestBody.getMukSalt());
            repository.save(device);
        });
    }

    public Optional<Device> findById(String id) {
        return repository.findById(id);
    }
}
