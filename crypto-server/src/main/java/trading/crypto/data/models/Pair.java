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
        return name.contains("/") ? name : convertSymbolNameToIsoStandard(name);
    }

    private String convertSymbolNameToIsoStandard(String name){
        if(name == null || name.length() < 3){
            throw new IllegalArgumentException("Invalid symbol format: " + name);
        }
        // in order to comply with ISO 4217-A3 as expected by Kraken
        // first half of each pair is the base currency
        String quote = name.substring(name.length() - 3);
        // second half is the quote
        String base = name.substring(0, name.length() - 3);
        return base + "/" + quote;
    }
}
