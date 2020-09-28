package com.tofunmi.passwordmanager.repository;

import com.tofunmi.passwordmanager.model.VaultRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created By Tofunmi on 11/24/2018
 */
public interface VaultRecordRepository extends MongoRepository<VaultRecord, String> {
    Page<VaultRecord> findAllByNameLikeIgnoreCaseOrUrlLikeIgnoreCase(String name, String url, Pageable pageable);
    List<VaultRecord> findAllByNameLikeIgnoreCaseOrUrlLikeIgnoreCase(String name, String url);
}

