# Simple Banking System

A console-based banking application written in **Java**. It lets a user create an account, log in, deposit and withdraw money, transfer funds to another account, and view their transaction history. The project applies core object-oriented programming concepts and the Java Collections Framework.

## Features
- Create a new bank account
- Log in with account details
- Deposit and withdraw money
- Transfer funds between accounts
- View transaction history
- Error handling that blocks overdrafts, insufficient-balance transactions and invalid account operations

## Tech stack
| Area | Used |
|------|------|
| Language | Java |
| Concepts | OOP (encapsulation, inheritance, abstraction, polymorphism) |
| Collections | `HashMap`, `ArrayList` |
| Error handling | Custom exceptions |
| Interface | Console (command line) |

## How it works
- **OOP design:** Accounts, customers and transactions are separate classes. Encapsulation keeps balances private, inheritance and abstraction share common account behavior, and polymorphism lets different account types behave in their own way.
- **Fast lookups:** Accounts are stored in a `HashMap` keyed by account number, so finding an account takes constant time instead of scanning a whole list.
- **Transaction history:** Each account keeps its transactions in an `ArrayList`, in the order they happened.
- **Custom exceptions:** Dedicated exception classes stop overdrafts, insufficient-balance withdrawals and transfers, and operations on accounts that don't exist, and they show a clear message to the user.

## Project structure
> Replace this with your real file names.
```
SimpleBankingSystem/
  src/
    Main.java              (menu and program entry point)
    Account.java
    Customer.java
    Transaction.java
    Bank.java              (HashMap of accounts)
    InsufficientBalanceException.java
    ...
```

## Run it locally

### Prerequisites
Java JDK 8 or later (check with `java -version`).

### Steps
```bash
# 1. Clone the repository
git clone https://github.com/laxmisannagoudra/simple-banking-system.git
cd simple-banking-system

# 2. Compile
javac src/*.java

# 3. Run
java -cp src Main
```
> Change `Main` if your main class has a different name.

## Sample output
> Paste a screenshot or a short copy of the console output here.

## What I learned
- Designing a program with separate classes and applying the four OOP principles
- Choosing a `HashMap` for fast lookups and an `ArrayList` for ordered history
- Writing custom exceptions to handle invalid operations safely

## Possible improvements
- Save accounts and transactions to a file or a MySQL database
- Add PIN hashing and a lock after failed login attempts
- Add a Spring Boot REST API and a React front end

## Author
Laxmi B Sannagoudra: [LinkedIn](https://www.linkedin.com/in/laxmi-sannagoudra-86275a300) | [GitHub](https://github.com/laxmisannagoudra)
