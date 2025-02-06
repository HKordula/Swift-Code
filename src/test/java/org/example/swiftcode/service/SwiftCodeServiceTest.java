package org.example.swiftcode.service;

import org.example.swiftcode.dto.Branch;
import org.example.swiftcode.dto.SwiftCodeDto;
import org.example.swiftcode.exception.SwiftCodeNotFoundException;
import org.example.swiftcode.model.SwiftCode;
import org.example.swiftcode.repo.SwiftCodeRepo;
import org.example.swiftcode.service.impl.SwiftCodeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SwiftCodeServiceTest {

    @Mock
    private SwiftCodeRepo swiftCodeRepo;

    @InjectMocks
    private SwiftCodeServiceImpl swiftCodeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSwiftCode_Success() {
        SwiftCode swiftCode = new SwiftCode();
        swiftCode.setSwiftCode("AAISALTRXXX");
        swiftCode.setBankName("United Bank of Albania");
        swiftCode.setCountryISO2("AL");
        swiftCode.setCountryName("Albania");
        swiftCode.setAddress("Tirana, Albania");

        when(swiftCodeRepo.findBySwiftCode("AAISALTRXXX")).thenReturn(swiftCode);

        SwiftCodeDto result = swiftCodeService.getSwiftCode("AAISALTRXXX");

        assertNotNull(result);
        assertEquals("AAISALTRXXX", result.getSwiftCode());
        assertEquals("United Bank of Albania", result.getBankName());
    }

    @Test
    void testGetSwiftCode_NotFound() {
        when(swiftCodeRepo.findBySwiftCode("INVALIDCODE")).thenReturn(null);

        assertThrows(SwiftCodeNotFoundException.class, () -> swiftCodeService.getSwiftCode("INVALIDCODE"));
    }

    @Test
    void testAddSwiftCode_Success() {
        SwiftCode swiftCode = new SwiftCode();
        swiftCode.setSwiftCode("TESTBANK1234");
        swiftCode.setBankName("Test Bank");
        swiftCode.setCountryISO2("US");
        swiftCode.setCountryName("United States");
        swiftCode.setAddress("Test Address");

        when(swiftCodeRepo.findBySwiftCode("TESTBANK1234")).thenReturn(null);
        when(swiftCodeRepo.save(any(SwiftCode.class))).thenReturn(swiftCode);

        Branch result = swiftCodeService.addSwiftCode(new SwiftCodeDto(
                "Test Address", "Test Bank", "US", "United States", false, "TESTBANK1234", List.of()));

        assertNotNull(result);
        assertEquals("TESTBANK1234", result.getSwiftCode());
        assertEquals("Test Bank", result.getBankName());
    }

    @Test
    void testDeleteSwiftCode_NotFound() {
        when(swiftCodeRepo.findBySwiftCode("NONEXISTENT")).thenReturn(null);

        assertThrows(SwiftCodeNotFoundException.class, () -> swiftCodeService.deleteSwiftCode("NONEXISTENT"));
    }
}
