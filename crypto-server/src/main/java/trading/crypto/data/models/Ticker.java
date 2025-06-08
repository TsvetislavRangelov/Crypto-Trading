package trading.crypto.data.models;

public class Ticker {
    private final String symbol;
    private final double price;
    private final double volume;

    public Ticker(String symbol, double lastTradedPrice, double volume) {
        this.symbol = symbol;
        this.price = lastTradedPrice;
        this.volume = volume;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public double getVolume() {
        return volume;
    }
}
