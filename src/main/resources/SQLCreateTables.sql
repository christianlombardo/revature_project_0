CREATE TABLE customers (
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name CHAR(50),
    username CHAR(50),
    password CHAR(50)
);


CREATE TABLE employees (
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name CHAR(50),
    username CHAR(50),
    password CHAR(50)
);


CREATE TABLE accounts (
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, # Bank account number.
    balance DECIMAL (20, 2),
    customer_id INTEGER NOT NULL,
    active tinyint(1) DEFAULT NULL,
    status enum('approved','rejected') DEFAULT NULL,
);
ALTER TABLE accounts ADD FOREIGN KEY (customer_id) REFERENCES customers(id);	# Accounts table must be empty to run this command.


CREATE TABLE transactions (
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	transaction_type ENUM('deposit', 'withdrawal', 'posted_transfer_to', 'posted_transfer_from', 'accepted_transfer_from', 'accepted_transfer_to'),
	amount DECIMAL(20,4),
    customer_id INTEGER NOT NULL,
    account_id INTEGER NOT NULL
);
ALTER TABLE transactions ADD FOREIGN KEY (customer_id) REFERENCES customers(id);
ALTER TABLE transactions ADD FOREIGN KEY (account_id) REFERENCES accounts(id);


CREATE TABLE transfer_to_account (
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, # Bank account number.
    from_account_id INTEGER,
    to_account_id INTEGER,
    transfer_amount DECIMAL (20, 4),
    approved BOOLEAN DEFAULT 0
);
ALTER TABLE transfer_to_account ADD FOREIGN KEY (from_account_id) REFERENCES accounts(id);	# transfer_to_acccount table must be empty to run this command.
ALTER TABLE transfer_to_account ADD FOREIGN KEY (to_account_id) REFERENCES accounts(id);	# transfer_to_acccount table must be empty to run this command.
