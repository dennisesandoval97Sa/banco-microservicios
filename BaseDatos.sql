-- ========================================
-- BASE DE DATOS CLIENTS
-- ========================================
CREATE DATABASE IF NOT EXISTS clients_db;
USE clients_db;

CREATE TABLE IF NOT EXISTS person (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    gender VARCHAR(50),
    age INT,
    identification VARCHAR(50) UNIQUE NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS client (
    id BIGINT NOT NULL,
    password VARCHAR(255),
    status BIT,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES person(id)
);

INSERT INTO person (name, gender, age, identification, address, phone)
VALUES ('Nuevo Cliente', 'Male', 29, '1234567890', 'Av Siempre Viva', '0912345678');

INSERT INTO client (id, password, status)
VALUES (1, 'pass123', 1);

-- ========================================
-- BASE DE DATOS ACCOUNTS
-- ========================================
CREATE DATABASE IF NOT EXISTS accounts_db;
USE accounts_db;

CREATE TABLE IF NOT EXISTS accounts (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    client_id BIGINT,
    account_number VARCHAR(50),
    account_type VARCHAR(50),
    initial_balance DECIMAL(10,2),
    status BOOLEAN
);

CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT,
    transaction_type VARCHAR(50),
    value DECIMAL(10,2),
    balance DECIMAL(10,2),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO accounts (client_id, account_number, account_type, initial_balance, status)
VALUES (1, 'ACC-1754205346166', 'savings', 0.00, true);

INSERT INTO transactions (account_id, transaction_type, value, balance)
VALUES
(1, 'deposit', 150.00, 150.00),
(1, 'withdraw', 50.00, 100.00);
