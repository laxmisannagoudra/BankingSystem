package banking;

/** Current account: no minimum balance, but overdrafts are still never allowed. */
public class CurrentAccount extends Account {

    public CurrentAccount(String accountNumber, Customer owner, double openingBalance) throws InvalidAmountException {
        super(accountNumber, owner, openingBalance);
    }

    @Override
    public double getWithdrawableAmount() {
        return getBalance();
    }

    @Override
    public String getAccountType() {
        return "Current";
    }
}
