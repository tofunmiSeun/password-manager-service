package com.tofunmi.repository;

import com.tofunmi.model.VaultRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created By Tofunmi on 11/24/2018
 */
public interface VaultRecordRepository extends MongoRepository<VaultRecord, String> {
    Page<VaultRecord> findAllByNameLikeIgnoreCaseOrUrlLikeIgnoreCase(String name, String url, Pageable pageable);
}

