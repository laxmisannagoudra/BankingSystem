package banking;

import java.util.HashMap;
import java.util.Map;

/**
 * Service layer. Accounts are kept in a HashMap keyed by account number,
 * giving O(1) average lookup instead of scanning a list.
 */
public class Bank {
    private final Map<String, Account> accounts = new HashMap<>();
    private final Map<String, Customer> customers = new HashMap<>();
    private long nextAccountNumber = 1001000001L;
    private int nextCustomerId = 1;

    public Account createAccount(String type, String name, String email, String pin, double openingBalance)
            throws BankException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidAccountException("Name cannot be empty.");
        }
        if (email == null || !email.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            throw new InvalidAccountException("Please enter a valid email address.");
        }
        if (pin == null || !pin.matches("\\d{4}")) {
            throw new InvalidAccountException("PIN must be exactly 4 digits.");
        }

        Customer customer = new Customer("C" + String.format("%04d", nextCustomerId), name.trim(), email.trim(), pin);
        String accNo = String.valueOf(nextAccountNumber);

        Account account;
        switch (type.toUpperCase()) {
            case "SAVINGS": account = new SavingsAccount(accNo, customer, openingBalance); break;
            case "CURRENT": account = new CurrentAccount(accNo, customer, openingBalance); break;
            default: throw new InvalidAccountException("Unknown account type: " + type);
        }

        // Only commit state once construction succeeded
        nextAccountNumber++;
        nextCustomerId++;
        customers.put(customer.getCustomerId(), customer);
        accounts.put(accNo, account);
        return account;
    }

    public Account login(String accountNumber, String pin) throws InvalidAccountException {
        Account account = accounts.get(accountNumber);
        // Same message for both failures so we don't reveal which accounts exist
        if (account == null || !account.getOwner().verifyPin(pin)) {
            throw new InvalidAccountException("Invalid account number or PIN.");
        }
        return account;
    }

    public Account findAccount(String accountNumber) throws InvalidAccountException {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new InvalidAccountException("Account " + accountNumber + " does not exist.");
        }
        return account;
    }

    public void transfer(String fromAccNo, String toAccNo, double amount) throws BankException {
        if (fromAccNo.equals(toAccNo)) {
            throw new InvalidAccountException("Cannot transfer to the same account.");
        }
        Account from = findAccount(fromAccNo);
        Account to = findAccount(toAccNo);

        from.transferOut(to, amount);   // throws before anything changes if it can't proceed
        to.transferIn(from, amount);
    }

    public int getAccountCount() {
        return accounts.size();
    }
}
