package com.tofunmi.passwordmanager.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By tofunmi on 08/07/2020
 */

@Getter
@Setter
@NoArgsConstructor
public class SectionedVaultRecord {
    private Character title;
    private List<VaultRecord> data = new ArrayList<>();

    public SectionedVaultRecord(Character title) {
        this.title = title;
    }
}
