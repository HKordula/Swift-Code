package org.example.swiftcode.repo;

import org.example.swiftcode.model.SwiftCode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SwiftCodeRepo extends JpaRepository<SwiftCode, Integer> {
    SwiftCode findBySwiftCode(String swiftCode);

    List<SwiftCode> findSwiftCodesByCountryISO2(String countryISO2);

    List<SwiftCode> findBySwiftCodeStartingWith(String swiftCodePrefix);

}

