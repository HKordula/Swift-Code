package org.example.swiftcode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountrySwiftCodeDto {
    private String countryISO2;
    private String countryName;
    private List<Branch> swiftCodes;
}
