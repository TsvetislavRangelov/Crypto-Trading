package trading.crypto.data.models;

public class Pair {
    private String name;
    private double volume;

    public Pair(String name, double volume) {
        this.name = name;
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
        int quoteLength = 3;
        // in order to comply with ISO 4217-A3 as expected by Kraken
        // second half is the quote
        String quote = name.substring(name.length() - 3);
        // poor man's way of handling USDT as quote currency.
        if(quote.endsWith("T")){
            quote = "USDT";
            quoteLength = 4;
        }
        // first half of each pair is the base currency
        String base = name.substring(0, name.length() - quoteLength);
        return base + "/" + quote;
    }
}
