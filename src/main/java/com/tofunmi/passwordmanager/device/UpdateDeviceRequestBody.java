package com.tofunmi.passwordmanager.device;

import lombok.Data;

/**
 * Created By tofunmi on 16/12/2020
 */
@Data
public class UpdateDeviceRequestBody {
    private String publicKey;
    private String encryptedPrivateKey;
    private String mukSalt;
}
