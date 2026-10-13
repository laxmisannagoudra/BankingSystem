package banking;

/** Thrown for unknown accounts, bad credentials, or illegal account operations. */
public class InvalidAccountException extends BankException {
    public InvalidAccountException(String message) {
        super(message);
    }
}
