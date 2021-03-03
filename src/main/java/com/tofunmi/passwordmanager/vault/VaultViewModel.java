package com.tofunmi.passwordmanager.vault;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * Created By tofunmi on 03/03/2021
 */
@Data
@Builder
public class VaultViewModel {
    private String id;
    private String name;
    private String createdBy;
    private Instant createdAt;
}
