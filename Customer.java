package banking;

/** Account holder. Fields are private (encapsulation); the PIN is never exposed. */
public class Customer {
    private final String customerId;
    private String name;
    private String email;
    private String pin;

    public Customer(String customerId, String name, String email, String pin) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.pin = pin;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }

    public boolean verifyPin(String attempt) {
        return pin.equals(attempt);
    }

    @Override
    public String toString() {
        return name + " (" + customerId + ", " + email + ")";
    }
}
