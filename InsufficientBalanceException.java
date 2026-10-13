package banking;

/** Thrown when a withdrawal/transfer would overdraft the account or break its minimum balance. */
public class InsufficientBalanceException extends BankException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
