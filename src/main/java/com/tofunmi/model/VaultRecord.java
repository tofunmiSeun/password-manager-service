package com.tofunmi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Created By Tofunmi on 11/24/2018
 */
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

    public VaultRecord(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public VaultRecord() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format("Vault record for %s - (%s). Created on %s", name, url, createdAt);
    }
}