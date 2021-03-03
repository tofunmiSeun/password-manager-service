package com.tofunmi.passwordmanager.device;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Created By tofunmi on 16/12/2020
 */
@RestController
@RequestMapping(value = "device")
public class DeviceController {
    private final DeviceService service;

    public DeviceController(DeviceService service) {
        this.service = service;
    }

    @PostMapping
    public String create(@RequestBody NewDeviceRequestBody requestBody, Principal principal) {
        return service.create(requestBody, principal);
    }

    @GetMapping
    public List<DeviceViewModel> getAll(Principal principal) {
        return service.getAll(principal);
    }

    @PutMapping("{id}")
    public void updateCredentials(@PathVariable String id, @RequestBody UpdateDeviceRequestBody requestBody, Principal principal) {
        service.updateCredentials(id, requestBody, principal);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id, Principal principal) {
        service.delete(id, principal);
    }
}
