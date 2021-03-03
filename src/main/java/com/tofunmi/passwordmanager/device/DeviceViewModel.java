package com.tofunmi.passwordmanager.device;

import lombok.Builder;
import lombok.Data;

/**
 * Created By tofunmi on 03/03/2021
 */
@Data
@Builder
public class DeviceViewModel {
    private String id;
    private String alias;
    private String publicKey;
    private String encryptedPrivateKey;
    private String mukSalt;
}
