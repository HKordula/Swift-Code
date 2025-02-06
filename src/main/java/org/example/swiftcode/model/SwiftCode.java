package org.example.swiftcode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "swift_codes")
public class SwiftCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="country_iso2")
    private String countryISO2;

    @Column(name="swift_code")
    private String swiftCode;

    @Column(name="code_type")
    private String codeType;

    @Column(name="bank_name")
    private String bankName;

    @Column(name="address")
    private String address;

    @Column(name="town_name")
    private String townName;

    @Column(name="country_name")
    private String countryName;

    @Column(name="time_zone")
    private String timeZone;
}
