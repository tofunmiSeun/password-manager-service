package com.tofunmi.controller;

import com.tofunmi.service.VaultRecordService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created By tofunmi on 20/01/2019
 */
public class VaultRecordControllerTest {

    @InjectMocks
    private VaultRecordController controller;

    @Mock
    private VaultRecordService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRevealPassword() {
        when(service.revealPassword(any())).thenReturn("MOCKPASSWORD");

        List<String> result = controller.revealPassword("test-record-id");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("MOCKPASSWORD", result.get(0));
    }
}