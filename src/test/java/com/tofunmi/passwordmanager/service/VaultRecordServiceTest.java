package com.tofunmi.passwordmanager.service;

import com.tofunmi.passwordmanager.model.VaultRecord;
import com.tofunmi.passwordmanager.repository.VaultRecordRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created By tofunmi on 20/01/2019
 */
public class VaultRecordServiceTest {

    @InjectMocks
    private VaultRecordService vaultRecordService;

    @Mock
    private VaultRecordRepository vaultRecordRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        when(vaultRecordRepository.findAll(any(PageRequest.class))).thenAnswer(mockFindAllAnswer());

        Page<VaultRecord> vaultRecordPage = vaultRecordService.findAll(0);
        assertNotNull(vaultRecordPage);

        List<VaultRecord> vaultRecords = vaultRecordPage.getContent();
        int pageNumber = vaultRecordPage.getNumber();

        assertNotNull(vaultRecords);
        assertEquals(0, pageNumber);
        assertEquals(3, vaultRecords.size());

        VaultRecord record1 = vaultRecords.get(0);
        assertNotNull(record1);
        assertEquals("1", record1.getId());
        assertEquals("Mock Record 1", record1.getName());
        assertEquals("https://mock-record-1-url.com", record1.getUrl());
        assertEquals("mockUsername1", record1.getUsername());
        assertNull(record1.getEncodedPassword());

        VaultRecord record2 = vaultRecords.get(1);
        assertNotNull(record2);
        assertEquals("2", record2.getId());
        assertEquals("Mock Record 2", record2.getName());
        assertEquals("https://mock-record-2-url.com", record2.getUrl());
        assertEquals("mockUsername2", record2.getUsername());
        assertNull(record2.getEncodedPassword());

        VaultRecord record3 = vaultRecords.get(2);
        assertNotNull(record3);
        assertEquals("3", record3.getId());
        assertEquals("Mock Record 3", record3.getName());
        assertEquals("https://mock-record-3-url.com", record3.getUrl());
        assertEquals("mockUsername3", record3.getUsername());
        assertNull(record3.getEncodedPassword());
    }

    @Test
    public void testCreate() {
        when(vaultRecordRepository.save(any(VaultRecord.class))).thenAnswer(mockCreateMethodAnswer());

        final VaultRecord vaultRecordToCreate = new VaultRecord();
        vaultRecordToCreate.setName("New Record");
        vaultRecordToCreate.setUrl("https://new-record.com");
        vaultRecordToCreate.setUsername("newUsername");
        vaultRecordToCreate.setEncodedPassword("newEncodedPassword");

        VaultRecord newlyCreatedVaultRecord = vaultRecordService.create(vaultRecordToCreate);

        assertNotNull(newlyCreatedVaultRecord);
        assertNotNull(newlyCreatedVaultRecord.getId());
        assertEquals("New Record", newlyCreatedVaultRecord.getName());
        assertEquals("https://new-record.com", newlyCreatedVaultRecord.getUrl());
        assertEquals("newUsername", newlyCreatedVaultRecord.getUsername());
        assertNull(newlyCreatedVaultRecord.getEncodedPassword());
        assertNotNull(newlyCreatedVaultRecord.getCreatedAt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNoName() {
        when(vaultRecordRepository.save(any(VaultRecord.class))).thenAnswer(mockCreateMethodAnswer());

        final VaultRecord vaultRecordToCreate = new VaultRecord();
        vaultRecordToCreate.setUrl("https://new-record.com");
        vaultRecordToCreate.setUsername("newUsername");
        vaultRecordToCreate.setEncodedPassword("newEncodedPassword");
        vaultRecordService.create(vaultRecordToCreate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNoUsername() {
        when(vaultRecordRepository.save(any(VaultRecord.class))).thenAnswer(mockCreateMethodAnswer());

        final VaultRecord vaultRecordToCreate = new VaultRecord();
        vaultRecordToCreate.setName("New record");
        vaultRecordToCreate.setUrl("https://new-record.com");
        vaultRecordToCreate.setEncodedPassword("newEncodedPassword");
        vaultRecordService.create(vaultRecordToCreate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNoPassword() {
        when(vaultRecordRepository.save(any(VaultRecord.class))).thenAnswer(mockCreateMethodAnswer());

        final VaultRecord vaultRecordToCreate = new VaultRecord();
        vaultRecordToCreate.setName("New record");
        vaultRecordToCreate.setUrl("https://new-record.com");
        vaultRecordToCreate.setUsername("newUsername");
        vaultRecordService.create(vaultRecordToCreate);
    }

    @Test
    public void testFindOne() {
        when(vaultRecordRepository.findOne(any(String.class))).thenAnswer(mockFindOneAnswer());

        final VaultRecord vaultRecord = vaultRecordService.findOne("1");
        assertNotNull(vaultRecord);
        assertEquals("1", vaultRecord.getId());
        assertNull(vaultRecord.getEncodedPassword());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindOneInvalidId() {
        when(vaultRecordRepository.findOne(any(String.class))).thenAnswer(mockFindOneAnswer());
        vaultRecordService.findOne("invalidId");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteInvalidId() {
        when(vaultRecordRepository.findOne(any(String.class))).thenAnswer(mockFindOneAnswer());
        vaultRecordService.delete("invalidId");
    }

    @Test
    public void testRevealPassword() {
        when(vaultRecordRepository.findOne(any(String.class))).thenAnswer(mockFindOneAnswer());

        final String password = vaultRecordService.revealPassword("1");
        assertEquals("mockPassword1", password);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRevealPasswordInvalidId() {
        when(vaultRecordRepository.findOne(any(String.class))).thenAnswer(mockFindOneAnswer());
        vaultRecordService.revealPassword("invalidId");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNullObject() {
        when(vaultRecordRepository.findOne(any(String.class))).thenAnswer(mockFindOneAnswer());
        vaultRecordService.update("1", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateInvalidId() {
        when(vaultRecordRepository.findOne(any(String.class))).thenAnswer(mockFindOneAnswer());

        final VaultRecord updatedRecord = new VaultRecord();
        updatedRecord.setId("1");
        updatedRecord.setName("New name");
        updatedRecord.setUsername("newMockUsername1");
        vaultRecordService.update("invalidId", updatedRecord);
    }

    @Test
    public void testUpdate() {
        when(vaultRecordRepository.findOne(any(String.class))).thenAnswer(mockFindOneAnswer());
        when(vaultRecordRepository.save(any(VaultRecord.class))).thenAnswer(mockSaveAnswer());

        final VaultRecord updatedRecord = new VaultRecord();
        updatedRecord.setId("1");
        updatedRecord.setName("New name");
        updatedRecord.setUrl("https://new-url-1.com");
        updatedRecord.setUsername("newMockUsername1");

        final VaultRecord newRecord = vaultRecordService.update("1", updatedRecord);
        assertNotNull(newRecord);
        assertEquals("1", newRecord.getId());
        assertEquals("New name", newRecord.getName());
        assertEquals("https://new-url-1.com", newRecord.getUrl());
        assertNull(newRecord.getEncodedPassword());
    }

    @Test
    public void testUpdateUrlNotSet() {
        when(vaultRecordRepository.findOne(any(String.class))).thenAnswer(mockFindOneAnswer());
        when(vaultRecordRepository.save(any(VaultRecord.class))).thenAnswer(mockSaveAnswer());

        final VaultRecord updatedRecord = new VaultRecord();
        updatedRecord.setId("1");
        updatedRecord.setName("New name");
        updatedRecord.setUsername("newMockUsername1");

        final VaultRecord newRecord = vaultRecordService.update("1", updatedRecord);
        assertNotNull(newRecord);
        assertEquals("1", newRecord.getId());
        assertEquals("New name", newRecord.getName());
        assertEquals("N/A", newRecord.getUrl());
        assertNull(newRecord.getEncodedPassword());
    }

    private Answer<VaultRecord> mockSaveAnswer() {
        return new Answer<VaultRecord>() {
            public VaultRecord answer(InvocationOnMock invocation) {
                return invocation.getArgumentAt(0, VaultRecord.class);
            }
        };
    }

    private Answer<VaultRecord> mockFindOneAnswer() {
        return new Answer<VaultRecord>() {
            public VaultRecord answer(InvocationOnMock invocation) {
                String id = invocation.getArgumentAt(0, String.class);
                return mockFindAllResult().stream()
                        .filter(r -> r.getId().equals(id))
                        .findAny()
                        .orElse(null);
            }
        };
    }

    private Answer<Page<VaultRecord>> mockFindAllAnswer() {
        return new Answer<Page<VaultRecord>>() {
            public Page<VaultRecord> answer(InvocationOnMock invocation) {
                PageRequest pageRequest = invocation.getArgumentAt(0, PageRequest.class);


                return new PageImpl<>(mockFindAllResult(), pageRequest, 30);
            }
        };
    }

    private Answer<VaultRecord> mockCreateMethodAnswer() {
        return new Answer<VaultRecord>() {
            public VaultRecord answer(InvocationOnMock invocation) {
                VaultRecord vaultRecordToCreate = invocation.getArgumentAt(0, VaultRecord.class);
                vaultRecordToCreate.setId("1");
                return vaultRecordToCreate;
            }
        };
    }

    private List<VaultRecord> mockFindAllResult() {
        VaultRecord v1 = new VaultRecord();
        v1.setId("1");
        v1.setName("Mock Record 1");
        v1.setUrl("https://mock-record-1-url.com");
        v1.setUsername("mockUsername1");
        v1.setEncodedPassword("mockPassword1");

        VaultRecord v2 = new VaultRecord();
        v2.setId("2");
        v2.setName("Mock Record 2");
        v2.setUrl("https://mock-record-2-url.com");
        v2.setUsername("mockUsername2");
        v2.setEncodedPassword("mockPassword2");

        VaultRecord v3 = new VaultRecord();
        v3.setId("3");
        v3.setName("Mock Record 3");
        v3.setUrl("https://mock-record-3-url.com");
        v3.setUsername("mockUsername3");
        v3.setEncodedPassword("mockPassword3");

        return Arrays.asList(v1, v2, v3);
    }
}