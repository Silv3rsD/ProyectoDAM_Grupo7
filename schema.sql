CREATE DATABASE authentifpcar;
USE authentifpcar;

CREATE TABLE IF NOT EXISTS vehicles (
    vin_number VARCHAR(17) PRIMARY KEY,
    license_plate VARCHAR(15) NOT NULL UNIQUE,
    brand VARCHAR(50),
    model VARCHAR(50),
    manufacturing_year YEAR,
    current_mileage INT UNSIGNED,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS itv (
    inspection_id INT AUTO_INCREMENT PRIMARY KEY,
    vin_number VARCHAR(17) NOT NULL,
    inspection_date DATE,
    completed BOOLEAN DEFAULT FALSE,
    next_inspection_date DATE,
    result VARCHAR(30),
    document_itv BLOB,
    recorded_mileage INT UNSIGNED,
    FOREIGN KEY (vin_number) 
        REFERENCES vehicles(vin_number)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS fines (
    fine_id INT AUTO_INCREMENT PRIMARY KEY,
    vin_number VARCHAR(17) NOT NULL,
    fine_number VARCHAR(50) NOT NULL,
    description VARCHAR(100),
    completed BOOLEAN DEFAULT FALSE,
    fine_date DATE,
    amount DECIMAL(10,2),
    document_fine BLOB,
    FOREIGN KEY (vin_number) 
        REFERENCES vehicles(vin_number)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS repairs (
    repair_id INT AUTO_INCREMENT PRIMARY KEY,
    vin_number VARCHAR(17) NOT NULL,
    repair_type VARCHAR(100) NOT NULL,
    completed BOOLEAN DEFAULT FALSE,
    reason TEXT,
    repair_date DATE,
    workshop VARCHAR(100),
    document_repairs BLOB,
    FOREIGN KEY (vin_number) 
        REFERENCES vehicles(vin_number)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS others (
    other_record_id INT AUTO_INCREMENT PRIMARY KEY,
    vin_number VARCHAR(17) NOT NULL,
    description TEXT NOT NULL,
    document_others BLOB,
    record_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (vin_number) 
        REFERENCES vehicles(vin_number)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS users (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) NOT NULL UNIQUE,
password_hash VARCHAR(255) NOT NULL,
role VARCHAR(15) NOT NULL,
enabled BOOLEAN NOT NULL DEFAULT TRUE,
created_at DATETIME NOT NULL DEFAULT current_timestamp,
CONSTRAINT chk_user_role CHECK (role IN ('USER', 'ADMIN')) 

);
