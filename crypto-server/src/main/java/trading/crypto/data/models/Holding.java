package trading.crypto.data.models;

public class Holding {
    private long id;
    private long userId;
    private double invested;
    private String symbol;
    private double shareAmount;
    private double avg;

    public long getId() {
        return id;
    }
    public long getUserId() {
        return userId;
    }
    public double getInvested() {
        return invested;
    }
    public String getSymbol() {
        return symbol;
    }
    public double getShareAmount() {
        return shareAmount;
    }
    public double getAvg() {
        return avg;
    }

    public Holding(long id,
                   long userId,
                   double invested,
                   String symbol,
                   double shareAmount,
                   double avg) {
        this.id = id;
        this.userId = userId;
        this.invested = invested;
        this.symbol = symbol;
        this.shareAmount = shareAmount;
        this.avg = avg;
    }
}
