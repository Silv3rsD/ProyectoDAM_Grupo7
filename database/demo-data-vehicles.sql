USE authentifpcar;

INSERT IGNORE INTO vehicles (vin_number, license_plate, brand, model, manufacturing_year, current_mileage, validated)
VALUES
('WVWZZZ1JZXW000001', '1234ABC', 'Toyota', 'Corolla', 2020, 68500, TRUE),
('WVWZZZ1JZXW000002', '5678DEF', 'Seat', 'Ibiza', 2018, 92000, FALSE);