package com.tofunmi.passwordmanager.vault.record;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * Created By tofunmi on 19/12/2020
 */
@Data
@Builder
public class VaultRecordViewModel {
    private String id;
    private String vaultId;
    private String vaultName;
    private String name;
    private String url;
    private String username;
    private String password;
    private String createdBy;
    private Instant lastModifiedAt;
}
