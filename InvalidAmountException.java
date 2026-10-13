package banking;

/** Thrown when an amount is zero, negative, NaN or otherwise unusable. */
public class InvalidAmountException extends BankException {
    public InvalidAmountException(String message) {
        super(message);
    }
}
