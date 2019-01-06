package com.tofunmi.controller;

import com.tofunmi.model.VaultRecord;
import com.tofunmi.service.VaultRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created By Tofunmi on 11/24/2018
 */
@RestController
@RequestMapping(value = "api/v1/vault-records")
public class VaultRecordController {

    @Autowired
    private VaultRecordService service;

    @GetMapping
    public List<VaultRecord> getAll() {
        return service.findAll();
    }

    @PostMapping
    public VaultRecord create(@RequestBody VaultRecord record) {
        return service.create(record);
    }

    @GetMapping("{id}")
    public VaultRecord get(@PathVariable String id) throws IllegalArgumentException {
        return service.findOne(id);
    }

    @PostMapping("edit/{id}")
    public VaultRecord update(@PathVariable String id, @RequestBody VaultRecord record) throws IllegalArgumentException {
        return service.update(id, record);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) throws IllegalArgumentException {
        service.delete(id);
    }
}