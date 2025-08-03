CREATE DATABASE IF NOT EXISTS accounts_db;
USE accounts_db;

-- Tabla accounts
CREATE TABLE IF NOT EXISTS accounts (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    client_id BIGINT NOT NULL,
    account_number VARCHAR(50) UNIQUE NOT NULL,
    account_type VARCHAR(50),
    initial_balance DECIMAL(10,2),
    status BOOLEAN
);

-- Tabla transactions
CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL,
    transaction_type VARCHAR(50),
    value DECIMAL(10,2),
    balance DECIMAL(10,2),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);

-- Datos de prueba para accounts
INSERT INTO accounts (client_id, account_number, account_type, initial_balance, status) VALUES
(1, 'ACC001', 'SAVINGS', 1000.00, true),   -- Vinculada al cliente demo
(2, 'ACC002', 'CHECKING', 500.00, true),
(3, 'ACC003', 'SAVINGS', 250.00, true);

-- Datos de prueba para transactions
INSERT INTO transactions (account_id, transaction_type, value, balance) VALUES
(1, 'deposit', 200.00, 1200.00),
(1, 'withdraw', 50.00, 1150.00),
(2, 'deposit', 300.00, 800.00),
(3, 'withdraw', 100.00, 150.00);
