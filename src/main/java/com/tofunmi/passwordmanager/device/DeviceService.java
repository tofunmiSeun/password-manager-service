package com.tofunmi.passwordmanager.device;

import com.tofunmi.passwordmanager.user.User;
import com.tofunmi.passwordmanager.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.Objects;
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

    public String create(NewDeviceRequestBody requestBody, Principal principal) {
        User userForDevice = userService.findByPrincipal(principal);

        Device device = new Device();
        device.setUser(userForDevice);
        device.setPublicKey(requestBody.getPublicKey());
        device.setEncryptedPrivateKey(requestBody.getEncryptedPrivateKey());
        device.setMukSalt(requestBody.getMukSalt());

        return repository.save(device).getId();
    }

    public void delete(String id, Principal principal) {
        Device device = getDevice(id);
        validateThatPrincipalOwnsDevice(principal, device);
        repository.deleteById(id);
    }

    public void updateCredentials(String id, UpdateDeviceRequestBody requestBody, Principal principal) {
        Device device = getDevice(id);
        validateThatPrincipalOwnsDevice(principal, device);
        device.setPublicKey(requestBody.getPublicKey());
        device.setEncryptedPrivateKey(requestBody.getEncryptedPrivateKey());
        device.setMukSalt(requestBody.getMukSalt());
        repository.save(device);

    }

    public Optional<Device> findById(String id) {
        return repository.findById(id);
    }

    private Device getDevice(String id) {
        Optional<Device> optionalDevice = repository.findById(id);
        Assert.isTrue(optionalDevice.isPresent(), "Device not present for ID " + id);
        return optionalDevice.get();
    }

    private void validateThatPrincipalOwnsDevice(Principal principal, Device device) {
        Assert.isTrue(Objects.equals(principal.getName(), device.getUser().getEmailAddress()), "User does not won this device");
    }
}
