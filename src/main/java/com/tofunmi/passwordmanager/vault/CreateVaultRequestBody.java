package com.tofunmi.passwordmanager.vault;

import lombok.Data;

/**
 * Created By tofunmi on 17/12/2020
 */
@Data
public class CreateVaultRequestBody {
    private String name;
    private String userId;
}
