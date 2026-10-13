package banking;

/** Savings account: must always keep a minimum balance. */
public class SavingsAccount extends Account {
    public static final double MINIMUM_BALANCE = 500.00;

    public SavingsAccount(String accountNumber, Customer owner, double openingBalance) throws InvalidAmountException {
        super(accountNumber, owner, openingBalance);
        if (openingBalance < MINIMUM_BALANCE) {
            throw new InvalidAmountException(
                    String.format("Savings accounts require an opening balance of at least %.2f.", MINIMUM_BALANCE));
        }
    }

    @Override
    public double getWithdrawableAmount() {
        return Math.max(0, getBalance() - MINIMUM_BALANCE);
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }
}
