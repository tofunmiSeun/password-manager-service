package com.tofunmi.passwordmanager.vault.key;

import lombok.Builder;
import lombok.Data;

/**
 * Created By tofunmi on 19/12/2020
 */
@Data
@Builder
public class VaultKeyViewModel {
    private String encryptedVaultKey;
}
