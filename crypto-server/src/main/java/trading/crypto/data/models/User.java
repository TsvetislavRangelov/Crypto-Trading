package trading.crypto.data.models;

public class User {
    private final long id;
    private final String username;
    private final double cash;

    public User(long id, String username, double cash) {
        this.id = id;
        this.username = username;
        this.cash = cash;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public double getCash() {
        return cash;
    }
}
