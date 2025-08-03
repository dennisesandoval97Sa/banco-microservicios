CREATE DATABASE IF NOT EXISTS clients_db;
USE clients_db;

-- Tabla person
CREATE TABLE IF NOT EXISTS person (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(50),
    age INT,
    identification VARCHAR(50) UNIQUE NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(50)
);

-- Tabla client (hereda de person)
CREATE TABLE IF NOT EXISTS client (
    id BIGINT NOT NULL,
    password VARCHAR(255),
    status BIT,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES person(id)
);

-- Datos de ejemplo
INSERT INTO person (name, gender, age, identification, address, phone) VALUES
('Cliente Demo', 'Male', 35, '998877', 'Main Street 123', '0999999999');

INSERT INTO client (id, password, status) VALUES
(1, '12345', 1);
