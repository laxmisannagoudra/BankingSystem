package banking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

/** Immutable record of a single balance-changing event. */
public final class Transaction {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final long id;
    private final TransactionType type;
    private final double amount;
    private final double balanceAfter;
    private final String description;
    private final LocalDateTime timestamp;

    public Transaction(TransactionType type, double amount, double balanceAfter, String description) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public long getId() { return id; }
    public TransactionType getType() { return type; }
    public double getAmount() { return amount; }
    public double getBalanceAfter() { return balanceAfter; }
    public String getDescription() { return description; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("#%-4d %s  %-12s %10.2f  Balance: %10.2f  %s",
                id, timestamp.format(FMT), type, amount, balanceAfter, description);
    }
}
