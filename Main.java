package banking;

import java.util.List;
import java.util.Scanner;

/** Console UI. */
public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final Bank bank = new Bank();

    public static void main(String[] args) {
        System.out.println("=== Welcome to Simple Bank ===");
        while (true) {
            System.out.println("\n1. Create account\n2. Login\n3. Exit");
            switch (prompt("Choose: ")) {
                case "1": createAccount(); break;
                case "2": login(); break;
                case "3": System.out.println("Thank you for banking with us. Goodbye!"); return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private static void createAccount() {
        try {
            String type = prompt("Account type (savings/current): ");
            String name = prompt("Full name: ");
            String email = prompt("Email: ");
            String pin = prompt("Set 4-digit PIN: ");
            double opening = readAmount("Opening deposit: ");
            Account acc = bank.createAccount(type, name, email, pin, opening);
            System.out.println("Account created! Your account number is: " + acc.getAccountNumber());
        } catch (BankException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void login() {
        try {
            String accNo = prompt("Account number: ");
            String pin = prompt("PIN: ");
            Account acc = bank.login(accNo, pin);
            System.out.println("Welcome, " + acc.getOwner().getName() + "!");
            accountMenu(acc);
        } catch (BankException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void accountMenu(Account acc) {
        while (true) {
            System.out.println("\n--- " + acc.getAccountType() + " Account " + acc.getAccountNumber() + " ---");
            System.out.println("1. Check balance\n2. Deposit\n3. Withdraw\n4. Transfer\n5. Transaction history\n6. Logout");
            String choice = prompt("Choose: ");
            try {
                switch (choice) {
                    case "1":
                        System.out.println(acc);
                        break;
                    case "2":
                        acc.deposit(readAmount("Amount to deposit: "));
                        System.out.printf("Deposited. New balance: %.2f%n", acc.getBalance());
                        break;
                    case "3":
                        acc.withdraw(readAmount("Amount to withdraw: "));
                        System.out.printf("Withdrawn. New balance: %.2f%n", acc.getBalance());
                        break;
                    case "4":
                        String to = prompt("Destination account number: ");
                        bank.transfer(acc.getAccountNumber(), to, readAmount("Amount to transfer: "));
                        System.out.printf("Transfer successful. New balance: %.2f%n", acc.getBalance());
                        break;
                    case "5":
                        printHistory(acc);
                        break;
                    case "6":
                        System.out.println("Logged out.");
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (BankException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printHistory(Account acc) {
        List<Transaction> history = acc.getTransactions();
        if (history.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        System.out.println("--- Transaction History ---");
        history.forEach(System.out::println);
    }

    private static String prompt(String msg) {
        System.out.print(msg);
        return in.hasNextLine() ? in.nextLine().trim() : "3"; // EOF -> exit cleanly
    }

    /** Reads a number; bad input becomes an InvalidAmountException rather than a crash. */
    private static double readAmount(String msg) throws InvalidAmountException {
        String s = prompt(msg);
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new InvalidAmountException("'" + s + "' is not a valid number.");
        }
    }
}
