package com.tofunmi.service;

import com.tofunmi.model.VaultRecord;
import com.tofunmi.repository.VaultRecordRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created By Tofunmi on 11/24/2018
 */
@Service
public class VaultRecordService {

    @Autowired
    private VaultRecordRepository repository;

    public List<VaultRecord> findAll() {
        return repository.findAll();
    }

    public VaultRecord findOne(String id) {
        return repository.findOne(id);
    }

    public VaultRecord create(VaultRecord record) {
        return repository.save(record);
    }

    public VaultRecord update(String id, VaultRecord updatedRecord) throws Exception {
        VaultRecord existingRecord = repository.findOne(id);
        if (existingRecord == null) {
            throw new Exception(String.format("No Vault record for id - %s", id));
        }

        BeanUtils.copyProperties(updatedRecord, existingRecord);
        return repository.save(existingRecord);
    }

    public void delete(String id) throws Exception {
        VaultRecord existingRecord = repository.findOne(id);
        if (existingRecord == null) {
            throw new Exception(String.format("No Vault record for id - %s", id));
        }

        repository.delete(existingRecord);
    }
}
