package org.example.swiftcode.service;

import org.example.swiftcode.dto.Branch;
import org.example.swiftcode.dto.CountrySwiftCodeDto;
import org.example.swiftcode.dto.SwiftCodeDto;

public interface SwiftCodeService {
    SwiftCodeDto getSwiftCode(String swiftCode);

    CountrySwiftCodeDto getSwiftCodesByCountry(String countryISO2);

    Branch addSwiftCode(SwiftCodeDto swiftCodeDto);

    void deleteSwiftCode(String swiftCode);
}
