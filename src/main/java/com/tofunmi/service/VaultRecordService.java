package com.tofunmi.service;

import com.tofunmi.model.SectionedVaultRecord;
import com.tofunmi.model.VaultRecord;
import com.tofunmi.repository.VaultRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created By Tofunmi on 11/24/2018
 */
@Service
public class VaultRecordService {

    @Autowired
    private VaultRecordRepository repository;

    public Page<VaultRecord> findAll(int pageNumber) {
        PageRequest pageRequest = buildPageRequest(pageNumber);
        Page<VaultRecord> records = repository.findAll(pageRequest);
        records.forEach(r -> r.setEncodedPassword(null));
        return records;
    }

    public VaultRecord create(VaultRecord record) throws IllegalArgumentException {
        validateEntry(record);
        record.setCreatedAt(LocalDateTime.now());
        VaultRecord newlyCreatedVaultRecord = repository.save(record);
        newlyCreatedVaultRecord.setEncodedPassword(null);
        return newlyCreatedVaultRecord;
    }

    public VaultRecord findOne(String id) throws IllegalArgumentException {
        VaultRecord vaultRecord = findById(id);
        vaultRecord.setEncodedPassword(null);
        return vaultRecord;
    }

    public VaultRecord update(String id, VaultRecord updatedRecord) throws IllegalArgumentException {
        if (updatedRecord == null) {
            throw new IllegalArgumentException("Updated record is not set");
        }
        VaultRecord existingRecord = findById(id);
        copyPropertiesForUpdate(existingRecord, updatedRecord);

        existingRecord = repository.save(existingRecord);
        existingRecord.setEncodedPassword(null);
        return existingRecord;
    }

    public void delete(String id) throws IllegalArgumentException {
        VaultRecord existingRecord = findById(id);
        repository.delete(existingRecord);
    }

    public Page<VaultRecord> search(String searchKey, int pageNumber) {
        PageRequest pageRequest = buildPageRequest(pageNumber);
        Page<VaultRecord> records = repository.findAllByNameLikeIgnoreCaseOrUrlLikeIgnoreCase(searchKey, searchKey, pageRequest);
        records.forEach(r -> r.setEncodedPassword(null));
        return records;
    }

    public String revealPassword(String id) throws IllegalArgumentException {
        VaultRecord vaultRecord = findById(id);
        return vaultRecord.getEncodedPassword();
    }

    private PageRequest buildPageRequest(int pageNumber) {
        final int pageSize = 8;
        Sort sort = new Sort(Sort.Direction.DESC, "createdAt");
        return new PageRequest(pageNumber, pageSize, sort);
    }

    private VaultRecord findById(String id) throws IllegalArgumentException {
        VaultRecord vaultRecord = repository.findOne(id);
        if (vaultRecord == null) {
            throw new IllegalArgumentException(String.format("No Vault record for id - %s", id));
        }
        return vaultRecord;
    }

    private void validateEntry(VaultRecord record) throws IllegalArgumentException {
        if (isNullOrEmpty(record.getName())) {
            throw new IllegalArgumentException("Name is required");
        }

        if (isNullOrEmpty(record.getUrl())) {
            record.setUrl("N/A");
        }

        if (isNullOrEmpty(record.getUsername())) {
            throw new IllegalArgumentException("username is required");
        }

        if (isNullOrEmpty(record.getEncodedPassword())) {
            throw new IllegalArgumentException("Password is required");
        }
    }

    private void copyPropertiesForUpdate(VaultRecord existingRecord, VaultRecord updatedRecord) {
        existingRecord.setName(updatedRecord.getName());
        existingRecord.setUrl(isNullOrEmpty(updatedRecord.getUrl()) ? "N/A" : updatedRecord.getUrl());
        existingRecord.setUsername(updatedRecord.getUsername());
        if (!isNullOrEmpty(updatedRecord.getEncodedPassword())) {
            existingRecord.setEncodedPassword(updatedRecord.getEncodedPassword());
        }
    }

    private boolean isNullOrEmpty(String aString) {
        return aString == null || aString.isEmpty();
    }

    public List<VaultRecord> findAll() {
        List<VaultRecord> records = repository.findAll(new Sort(Sort.Direction.ASC, "name"));
        records.forEach(r -> r.setEncodedPassword(null));
        return records;
    }

    public List<SectionedVaultRecord> findAllSectioned() {
        List<SectionedVaultRecord> sectionedVaultRecords = new ArrayList<>();

        List<VaultRecord> vaultRecords = findAll();
        for (VaultRecord vaultRecord : vaultRecords) {
            Character startingCharacter = vaultRecord.getName().toUpperCase().charAt(0);

            SectionedVaultRecord sectionedVaultRecord = sectionedVaultRecords.stream()
                    .filter(e -> Objects.equals(startingCharacter, e.getTitle()))
                    .findAny()
                    .orElse(null);

            if (sectionedVaultRecord == null) {
                sectionedVaultRecord = new SectionedVaultRecord(startingCharacter);
                sectionedVaultRecords.add(sectionedVaultRecord);
            }

            sectionedVaultRecord.getData().add(vaultRecord);
        }

        return sectionedVaultRecords;
    }
}
