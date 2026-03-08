Banking System Console Application
Overview

This project is a console-based banking system built using Java, JDBC, and MySQL.
It demonstrates core backend development concepts including:

Object Oriented Design

Layered Architecture

Database integration using JDBC

Transaction management (commit / rollback)

Service layer business logic

DAO pattern

Console-based user interface

The goal of this project is to practice backend fundamentals before using frameworks like Spring Boot.

Architecture

The project follows a layered backend architecture.

app
   BankingApp (Console UI)

service
   BankingService (Business logic + transactions)

dao
   CustomerDAO
   AccountDAO
   TransactionDAO

entity
   Customer
   Account
   Transaction

util
   DBConnection
   Mappers
Layer Responsibilities

Entity Layer
Represents database tables as Java objects.

DAO Layer
Handles database operations using JDBC.

Service Layer
Implements business logic and manages database transactions.

Console UI Layer
Handles user input and displays results.

Technologies Used

Java

JDBC

MySQL

IntelliJ IDEA

Git

GitHub

Database Schema
Customers Table
CREATE TABLE customers(
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,
    address VARCHAR(100)
);
Accounts Table
CREATE TABLE accounts(
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    account_no BIGINT UNIQUE NOT NULL,
    customer_id INT NOT NULL,
    account_type ENUM('CURRENT','SAVINGS'),
    balance DECIMAL(10,2) DEFAULT 100 CHECK(balance >= 0),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);
Transactions Table
CREATE TABLE transactions(
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    transaction_type ENUM('WITHDRAW','DEPOSIT','TRANSFER'),
    from_account_id INT NULL,
    to_account_id INT NULL,
    amount DECIMAL(10,2) NOT NULL CHECK(amount > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (from_account_id) REFERENCES accounts(account_id),
    FOREIGN KEY (to_account_id) REFERENCES accounts(account_id)
);
Features
Customer Management

Create customer

Search customers

View customer details

Account Management

Open account

View accounts by customer

Banking Operations

Deposit

Withdraw

Transfer money between accounts

Transaction History

View transaction history for an account

Transaction Management

The service layer implements manual JDBC transaction control to ensure data consistency.

Example flow for a transfer:

Start Transaction
↓
Update sender balance
↓
Update receiver balance
↓
Insert transaction record
↓
Commit

If any step fails:

Rollback

This ensures the system maintains ACID properties.

Example Console Menu
====== Banking System ======

1 Create Customer
2 Open Account
3 Deposit
4 Withdraw
5 Transfer
6 View Balance
7 Transaction History
0 Exit
Example Transaction Flow

Transfer operation:

Account A → deduct amount
Account B → add amount
Transaction record inserted
Commit

If any error occurs:

Rollback transaction
Learning Outcomes

This project helped practice:

JDBC database connectivity

DAO pattern

Service layer architecture

Database transactions

BigDecimal for financial calculations

Console UI design

Git workflow

Future Improvements

Possible enhancements:

Input validation layer

Pagination for transaction history

Logging framework

REST API using Spring Boot

Authentication system

Unit testing

Author

Yogeshkar P

Project Status

Completed — Console backend banking system.
