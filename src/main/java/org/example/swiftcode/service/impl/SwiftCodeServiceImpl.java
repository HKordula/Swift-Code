package org.example.swiftcode.service.impl;

import lombok.AllArgsConstructor;
import org.example.swiftcode.dto.Branch;
import org.example.swiftcode.dto.CountrySwiftCodeDto;
import org.example.swiftcode.dto.SwiftCodeDto;
import org.example.swiftcode.exception.SwiftCodeNotFoundException;
import org.example.swiftcode.model.SwiftCode;
import org.example.swiftcode.repo.SwiftCodeRepo;
import org.example.swiftcode.service.SwiftCodeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SwiftCodeServiceImpl implements SwiftCodeService {

    private final SwiftCodeRepo swiftCodeRepo;

    @Override
    public SwiftCodeDto getSwiftCode(String swiftCode) {
        SwiftCode swiftCodeEntity = swiftCodeRepo.findBySwiftCode(swiftCode);
        if (swiftCodeEntity == null) {
            throw new SwiftCodeNotFoundException("SWIFT Code not found: " + swiftCode);
        }

        boolean isHeadquarter = swiftCode.endsWith("XXX");
        SwiftCodeDto response = new SwiftCodeDto();
        response.setAddress(swiftCodeEntity.getAddress());
        response.setBankName(swiftCodeEntity.getBankName());
        response.setCountryISO2(swiftCodeEntity.getCountryISO2().toUpperCase());
        response.setCountryName(swiftCodeEntity.getCountryName().toUpperCase());
        response.setHeadquarter(isHeadquarter);
        response.setSwiftCode(swiftCodeEntity.getSwiftCode());

        if (isHeadquarter) {
            String swiftCodePrefix = swiftCode.substring(0, 8);
            List<SwiftCode> branches = swiftCodeRepo.findBySwiftCodeStartingWith(swiftCodePrefix);
            List<Branch> branchResponses = branches.stream()
                    .filter(branch -> !branch.getSwiftCode().endsWith("XXX"))
                    .map(branch -> new Branch(
                            branch.getAddress(),
                            branch.getBankName(),
                            branch.getCountryISO2().toUpperCase(),
                            false,
                            branch.getSwiftCode()
                    ))
                    .collect(Collectors.toList());
            response.setBranches(branchResponses);
        }

        return response;
    }

    @Override
    public CountrySwiftCodeDto getSwiftCodesByCountry(String countryISO2) {
        List<SwiftCode> swiftCodes = swiftCodeRepo.findSwiftCodesByCountryISO2(countryISO2.toUpperCase());

        if (swiftCodes.isEmpty()) {
            throw new SwiftCodeNotFoundException("No SWIFT codes found for country: " + countryISO2);
        }

        String countryName = swiftCodes.get(0).getCountryName().toUpperCase();
        List<Branch> branchList = swiftCodes.stream()
                .map(swiftCode -> new Branch(
                        swiftCode.getAddress(),
                        swiftCode.getBankName(),
                        swiftCode.getCountryISO2().toUpperCase(),
                        swiftCode.getSwiftCode().endsWith("XXX"),
                        swiftCode.getSwiftCode()
                ))
                .collect(Collectors.toList());

        return new CountrySwiftCodeDto(countryISO2.toUpperCase(), countryName, branchList);
    }


    @Override
    public Branch addSwiftCode(SwiftCodeDto swiftCodeDto) {
        if (swiftCodeRepo.findBySwiftCode(swiftCodeDto.getSwiftCode()) != null) {
            throw new IllegalArgumentException("SWIFT Code already exists: " + swiftCodeDto.getSwiftCode());
        }

        SwiftCode swiftCode = new SwiftCode();
        swiftCode.setAddress(swiftCodeDto.getAddress());
        swiftCode.setBankName(swiftCodeDto.getBankName());
        swiftCode.setCountryISO2(swiftCodeDto.getCountryISO2().toUpperCase());
        swiftCode.setCountryName(swiftCodeDto.getCountryName().toUpperCase());
        swiftCode.setSwiftCode(swiftCodeDto.getSwiftCode());

        SwiftCode savedSwiftCode = swiftCodeRepo.save(swiftCode);

        return new Branch(
                savedSwiftCode.getAddress(),
                savedSwiftCode.getBankName(),
                savedSwiftCode.getCountryISO2(),
                savedSwiftCode.getSwiftCode().endsWith("XXX"),
                savedSwiftCode.getSwiftCode()
        );
    }

    @Override
    public void deleteSwiftCode(String swiftCode) {
        SwiftCode swift = swiftCodeRepo.findBySwiftCode(swiftCode);
        if (swift == null) {
            throw new SwiftCodeNotFoundException("SWIFT Code not found: " + swiftCode);
        }
        swiftCodeRepo.delete(swift);
    }

}
