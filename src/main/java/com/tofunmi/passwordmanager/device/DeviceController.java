package com.tofunmi.passwordmanager.device;

import org.springframework.web.bind.annotation.*;

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
    public String create(@RequestBody NewDeviceRequestBody requestBody) {
        return service.create(requestBody);
    }

    @PutMapping("{id}")
    public void updateCredentials(@PathVariable String id, @RequestBody UpdateDeviceRequestBody requestBody) {
         service.updateCredentials(id, requestBody);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
