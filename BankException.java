package banking;

/** Base checked exception for every banking-rule violation. */
public class BankException extends Exception {
    public BankException(String message) {
        super(message);
    }
}
