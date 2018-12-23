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
@RequestMapping(value = "/api/v1/vault-records")
public class VaultRecordController {

    @Autowired
    private VaultRecordService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<VaultRecord> findAll() {
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public VaultRecord create(@RequestBody VaultRecord record) {
        return service.create(record);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public VaultRecord get(@PathVariable String id) {
        return service.findOne(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public VaultRecord update(@PathVariable String id, @RequestBody VaultRecord record) throws Exception {
        return service.update(id, record);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) throws Exception {
        service.delete(id);
    }
}
