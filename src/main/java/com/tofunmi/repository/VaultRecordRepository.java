package com.tofunmi.repository;

import com.tofunmi.model.VaultRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created By Tofunmi on 11/24/2018
 */
public interface VaultRecordRepository extends MongoRepository<VaultRecord, String> {
}