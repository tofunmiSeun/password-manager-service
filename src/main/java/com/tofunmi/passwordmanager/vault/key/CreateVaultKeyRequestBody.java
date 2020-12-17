package com.tofunmi.passwordmanager.vault.key;

import lombok.Data;

/**
 * Created By tofunmi on 17/12/2020
 */
@Data
public class CreateVaultKeyRequestBody {
    private String vaultId;
    private String userId;
    private String deviceId;
    private String encryptedVaultKey;
}
