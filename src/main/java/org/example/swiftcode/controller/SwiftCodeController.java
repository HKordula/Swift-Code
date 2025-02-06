package org.example.swiftcode.controller;

import lombok.AllArgsConstructor;
import org.example.swiftcode.dto.CountrySwiftCodeDto;
import org.example.swiftcode.dto.SwiftCodeDto;
import org.example.swiftcode.service.SwiftCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftCodeController {

    private SwiftCodeService swiftCodeService;

    @GetMapping("{swift-code}")
    public ResponseEntity<SwiftCodeDto> getSwiftCode(@PathVariable("swift-code") String swiftCode) {
        SwiftCodeDto swiftCodeDto = swiftCodeService.getSwiftCode(swiftCode);
        return ResponseEntity.ok(swiftCodeDto);
    }

    @GetMapping("/country/{countryISO2code}")
    public ResponseEntity<CountrySwiftCodeDto> getSwiftCodesByCountry(@PathVariable("countryISO2code") String countryISO2code) {
        CountrySwiftCodeDto response = swiftCodeService.getSwiftCodesByCountry(countryISO2code);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<String> addSwiftCode(@RequestBody SwiftCodeDto swiftCodeDto) {
        swiftCodeService.addSwiftCode(swiftCodeDto);
        return new ResponseEntity<>("Swift code added successfully", HttpStatus.CREATED);
    }

   @DeleteMapping("{swift-code}")
   public ResponseEntity<String> deleteSwiftCode(@PathVariable("swift-code") String swiftCode) {
       swiftCodeService.deleteSwiftCode(swiftCode);
       return ResponseEntity.ok("Swift code deleted successfully");
   }
}
