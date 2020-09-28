package com.tofunmi.passwordmanager.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Created By Tofunmi on 11/24/2018
 */
@Data
@Document(collection = "vault-record")
public class VaultRecord {
    @Id
    private String id;
    private String name;
    private String url;
    private String username;
    private String encodedPassword;
    @JsonIgnore
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdateTime;
    private Integer timesViewed;
}