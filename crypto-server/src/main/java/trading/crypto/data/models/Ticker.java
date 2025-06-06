package trading.crypto.data.models;

public class Ticker {
    private String symbol;
    private double lastTradedPrice;
    private double volume;

    public Ticker(String symbol, double lastTradedPrice, double volume) {
        this.symbol = symbol;
        this.lastTradedPrice = lastTradedPrice;
        this.volume = volume;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getLastTradedPrice() {
        return lastTradedPrice;
    }

    public double getVolume() {
        return volume;
    }
}
