package trading.crypto.data.models;

public class Pair {
    private String name;
    private String base;
    private String quote;
    private double volume;

    public Pair(String name, String base, String quote, double volume) {
        this.name = name;
        this.base = base;
        this.quote = quote;
        this.volume = volume;
    }

    public double getVolume() {
        return volume;
    }

    public String getName() {
        return name;
    }
}
