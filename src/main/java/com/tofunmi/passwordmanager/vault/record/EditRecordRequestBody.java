package com.tofunmi.passwordmanager.vault.record;

import lombok.Data;

/**
 * Created By tofunmi on 19/12/2020
 */
@Data
public class EditRecordRequestBody {
    private String name;
    private String url;
    private String username;
    private String password;
}
