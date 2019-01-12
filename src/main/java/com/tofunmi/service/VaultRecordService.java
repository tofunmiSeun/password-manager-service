package com.tofunmi.service;

import com.tofunmi.model.VaultRecord;
import com.tofunmi.repository.VaultRecordRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created By Tofunmi on 11/24/2018
 */
@Service
public class VaultRecordService {

    private final int pageSize = 8;

    @Autowired
    private VaultRecordRepository repository;

    public Page<VaultRecord> findAll(int pageNumber) {
        PageRequest pageRequest = buildPageRequest(pageNumber);
        Page<VaultRecord> records = repository.findAll(pageRequest);
        records.forEach(r -> r.setEncodedPassword(null));
        return records;
    }

    public VaultRecord findOne(String id) throws IllegalArgumentException {
        VaultRecord vaultRecord = findById(id);
        vaultRecord.setEncodedPassword(null);
        return vaultRecord;
    }

    public VaultRecord create(VaultRecord record) throws IllegalArgumentException {
        validateEntry(record);
        record.setCreatedAt(LocalDateTime.now());
        return repository.save(record);
    }

    public VaultRecord update(String id, VaultRecord updatedRecord) throws IllegalArgumentException {
        VaultRecord existingRecord = findById(id);
        copyPropertiesForUpdate(existingRecord, updatedRecord);
        return repository.save(existingRecord);
    }

    public void delete(String id) throws IllegalArgumentException {
        VaultRecord existingRecord = findById(id);
        repository.delete(existingRecord);
    }

    public String revealPassword(String id) throws IllegalArgumentException {
        VaultRecord vaultRecord = findById(id);
        return vaultRecord.getEncodedPassword();
    }

    private VaultRecord findById(String id) throws IllegalArgumentException {
        VaultRecord vaultRecord = repository.findOne(id);
        if (vaultRecord == null) {
            throw new IllegalArgumentException(String.format("No Vault record for id - %s", id));
        }
        return vaultRecord;
    }

    private void validateEntry(VaultRecord record) throws IllegalArgumentException {
        if (record.getName() == null || record.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }

        if (record.getUsername() == null || record.getUsername().isEmpty()) {
            throw new IllegalArgumentException("username is required");
        }

        if (record.getEncodedPassword() == null || record.getEncodedPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
    }

    private void copyPropertiesForUpdate(VaultRecord existingRecord, VaultRecord updatedRecord) {
        existingRecord.setName(updatedRecord.getName());
        existingRecord.setUrl(updatedRecord.getUrl());
        existingRecord.setUsername(updatedRecord.getUsername());
        if (updatedRecord.getEncodedPassword() != null && !updatedRecord.getEncodedPassword().isEmpty()) {
            existingRecord.setEncodedPassword(updatedRecord.getEncodedPassword());
        }
    }

    public Page<VaultRecord> search(String searchKey, int pageNumber) {
        PageRequest pageRequest = buildPageRequest(pageNumber);
        Page<VaultRecord> records = repository.findAllByNameLikeIgnoreCaseOrUrlLikeIgnoreCase(searchKey, searchKey, pageRequest);
        records.forEach(r -> r.setEncodedPassword(null));
        return records;
    }

    private PageRequest buildPageRequest(int pageNumber) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdAt");
        return new PageRequest(pageNumber, pageSize, sort);
    }
}
