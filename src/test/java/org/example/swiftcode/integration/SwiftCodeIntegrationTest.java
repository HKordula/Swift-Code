package org.example.swiftcode.integration;

import org.example.swiftcode.model.SwiftCode;
import org.example.swiftcode.repo.SwiftCodeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SwiftCodeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SwiftCodeRepo swiftCodeRepo;

    private final String existingSwiftCode = "AAISALTRXXX";

    @BeforeEach
    void checkDatabase() {
        Optional<SwiftCode> swiftCode = Optional.ofNullable(swiftCodeRepo.findBySwiftCode(existingSwiftCode));
        if (swiftCode.isEmpty()) {
            System.out.println("UWAGA: SWIFT Code 'AAISALTRXXX' nie istnieje w bazie.");
        }
    }

    @Test
    void testGetSwiftCode_Success() throws Exception {
        mockMvc.perform(get("/v1/swift-codes/" + existingSwiftCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.swiftCode").value(existingSwiftCode))
                .andExpect(jsonPath("$.bankName").exists());
    }

    @Test
    void testGetSwiftCode_NotFound() throws Exception {
        mockMvc.perform(get("/v1/swift-codes/NONEXISTENT"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddSwiftCode_Success() throws Exception {
        String uniqueSwiftCode = "TEST" + System.currentTimeMillis();

        String requestBody = """
    {
        "address": "New Address",
        "bankName": "Test Bank",
        "countryISO2": "US",
        "countryName": "United States",
        "isHeadquarter": false,
        "swiftCode": "%s"
    }
    """.formatted(uniqueSwiftCode);

        mockMvc.perform(post("/v1/swift-codes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }


    @Test
    void testAddSwiftCode_Duplicate() throws Exception {
        String requestBody = """
        {
            "address": "Existing Address",
            "bankName": "Existing Bank",
            "countryISO2": "US",
            "countryName": "United States",
            "isHeadquarter": false,
            "swiftCode": "%s"
        }
        """.formatted(existingSwiftCode);

        mockMvc.perform(post("/v1/swift-codes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}
