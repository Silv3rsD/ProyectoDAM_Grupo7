USE authentifpcar;

INSERT IGNORE INTO users (username, email, password_hash, role, account_status, email_verified, enabled)
VALUES
('admin', 'admin@ejemplo.com',
 '$2b$10$laQe5sYyp9Sabw1c2CqhvOFALhOgevmIUmPMSBNnYABWePn1tERMO',
 'ADMIN', 'ACTIVE', TRUE, TRUE),
('individual1', 'individual1@ejemplo.com',
 '$2b$10$laQe5sYyp9Sabw1c2CqhvOFALhOgevmIUmPMSBNnYABWePn1tERMO',
 'INDIVIDUAL', 'ACTIVE', TRUE, TRUE),
('tecnico1', 'tecnico1@ejemplo.com',
 '$2b$10$laQe5sYyp9Sabw1c2CqhvOFALhOgevmIUmPMSBNnYABWePn1tERMO',
 'TECHNICIAN', 'PENDING', TRUE, TRUE);