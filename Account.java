package banking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract base class for all accounts.
 * Common behaviour lives here; subclasses supply their own withdrawal rules (polymorphism).
 */
public abstract class Account {
    private final String accountNumber;
    private final Customer owner;
    private double balance;
    private final List<Transaction> transactions = new ArrayList<>();

    protected Account(String accountNumber, Customer owner, double openingBalance) throws InvalidAmountException {
        if (Double.isNaN(openingBalance) || openingBalance < 0) {
            throw new InvalidAmountException("Opening balance cannot be negative.");
        }
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = openingBalance;
        if (openingBalance > 0) {
            record(TransactionType.DEPOSIT, openingBalance, "Opening deposit");
        }
    }

    // ---- Subclass hooks (abstraction / polymorphism) ----
    /** Funds that may leave this account right now, according to the account type's rules. */
    public abstract double getWithdrawableAmount();

    public abstract String getAccountType();

    // ---- Public operations ----
    public void deposit(double amount) throws InvalidAmountException {
        validateAmount(amount);
        balance += amount;
        record(TransactionType.DEPOSIT, amount, "Cash deposit");
    }

    public void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        debit(amount);
        record(TransactionType.WITHDRAWAL, amount, "Cash withdrawal");
    }

    /** Debit side of a transfer. Package-private so only Bank can orchestrate transfers. */
    void transferOut(Account to, double amount) throws InvalidAmountException, InsufficientBalanceException {
        debit(amount);
        record(TransactionType.TRANSFER_OUT, amount, "Transfer to " + to.getAccountNumber());
    }

    /** Credit side of a transfer. */
    void transferIn(Account from, double amount) throws InvalidAmountException {
        validateAmount(amount);
        balance += amount;
        record(TransactionType.TRANSFER_IN, amount, "Transfer from " + from.getAccountNumber());
    }

    // ---- Internals ----
    private void debit(double amount) throws InvalidAmountException, InsufficientBalanceException {
        validateAmount(amount);
        if (amount > getWithdrawableAmount()) {
            throw new InsufficientBalanceException(String.format(
                    "Insufficient balance. Requested %.2f but only %.2f can be withdrawn from this %s account.",
                    amount, getWithdrawableAmount(), getAccountType()));
        }
        balance -= amount;
    }

    private void validateAmount(double amount) throws InvalidAmountException {
        if (Double.isNaN(amount) || Double.isInfinite(amount) || amount <= 0) {
            throw new InvalidAmountException("Amount must be a positive number.");
        }
    }

    private void record(TransactionType type, double amount, String description) {
        transactions.add(new Transaction(type, amount, balance, description));
    }

    // ---- Getters ----
    public String getAccountNumber() { return accountNumber; }
    public Customer getOwner() { return owner; }
    public double getBalance() { return balance; }

    /** Read-only view so callers can't tamper with history. */
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    @Override
    public String toString() {
        return String.format("%s Account [%s] Owner: %s | Balance: %.2f",
                getAccountType(), accountNumber, owner.getName(), balance);
    }
}
