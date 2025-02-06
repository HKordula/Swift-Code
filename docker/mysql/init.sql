CREATE DATABASE IF NOT EXISTS swiftcodesdb;
USE swiftcodesdb;

CREATE USER IF NOT EXISTS 'user'@'%' IDENTIFIED BY 'zaq1@WSX';
GRANT ALL PRIVILEGES ON swiftcodesdb.* TO 'user'@'%';
FLUSH PRIVILEGES;

CREATE TABLE IF NOT EXISTS swift_codes (
                                           id INT AUTO_INCREMENT PRIMARY KEY,
                                           country_iso2 VARCHAR(255),
                                           swift_code VARCHAR(255),
                                           code_type VARCHAR(255),
                                           bank_name VARCHAR(255),
                                           address VARCHAR(255),
                                           town_name VARCHAR(255),
                                           country_name VARCHAR(255),
                                           time_zone VARCHAR(255)
);

LOAD DATA INFILE '/var/lib/mysql-files/codes.csv'
    INTO TABLE swift_codes
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS
    (country_iso2, swift_code, code_type, bank_name, address, town_name, country_name, time_zone);
