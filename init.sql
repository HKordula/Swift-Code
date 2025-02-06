CREATE DATABASE IF NOT EXISTS swiftcodesdb;
USE swiftcodesdb;

LOAD DATA INFILE '/var/lib/mysql-files/codes.csv'
INTO TABLE swift_codes
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(country_iso2, swift_code, code_type, bank_name, address, town_name, country_name, time_zone);
