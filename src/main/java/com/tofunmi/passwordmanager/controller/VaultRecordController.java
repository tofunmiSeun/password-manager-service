package com.tofunmi.passwordmanager.controller;

import com.tofunmi.passwordmanager.model.SectionedVaultRecord;
import com.tofunmi.passwordmanager.model.VaultRecord;
import com.tofunmi.passwordmanager.service.VaultRecordService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.List;

/**
 * Created By Tofunmi on 11/24/2018
 */
@RestController
@RequestMapping(value = "vault-records")
public class VaultRecordController {

    private final VaultRecordService service;

    public VaultRecordController(VaultRecordService service) {
        this.service = service;
    }

    @GetMapping("sectioned")
    public List<SectionedVaultRecord> getAllSectioned() {
        return service.findAllSectioned();
    }

    @GetMapping("sectioned/search")
    public List<SectionedVaultRecord> searchSectioned(@RequestParam String searchKey) {
        return service.searchSectioned(searchKey);
    }

    @GetMapping("paged")
    public Page<VaultRecord> getAll(@RequestParam(required = false, defaultValue = "0") Integer page) {
        return service.findAll(page);
    }

    @PostMapping
    public VaultRecord create(@RequestBody VaultRecord record) throws IllegalArgumentException {
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

    @PostMapping("delete/{id}")
    public void delete(@PathVariable String id) throws IllegalArgumentException {
        service.delete(id);
    }

    @GetMapping("search")
    public Page<VaultRecord> search(@RequestParam String searchKey, @RequestParam(required = false, defaultValue = "0") Integer page) {
        return service.search(searchKey, page);
    }

    @GetMapping("reveal-password/{id}")
    public List<String> revealPassword(@PathVariable String id) throws IllegalArgumentException {
        String password = service.revealPassword(id);
        return Collections.singletonList(password);
    }
}