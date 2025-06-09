package trading.crypto.data.models;

import trading.crypto.data.models.enums.TransactionType;

import java.util.Date;

public class Transaction {
    private long id;
    private long userId;
    Date dateProduced;
    private String symbol;
    double pricePerShare;
    double amountOfShares;
    TransactionType action;

    public Transaction(long id,
                       long userId,
                       Date dateProduced,
                       String symbol,
                       double pricePerShare,
                       double amountOfShares,
                       TransactionType action) {
        this.id = id;
        this.userId = userId;
        this.dateProduced = dateProduced;
        this.symbol = symbol;
        this.pricePerShare = pricePerShare;
        this.amountOfShares = amountOfShares;
        this.action = action;
    }

    public long getId() {
        return id;
    }
    public long getUserId() {
        return userId;
    }
    public Date getDateProduced() {
        return dateProduced;
    }
    public String getSymbol() {
        return symbol;
    }
    public double getPricePerShare() {
        return pricePerShare;
    }
    public double getAmountOfShares() {
        return amountOfShares;
    }
    public TransactionType getAction() {
        return action;
    }

}
