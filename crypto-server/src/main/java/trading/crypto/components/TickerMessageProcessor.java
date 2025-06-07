package trading.crypto.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import trading.crypto.data.models.Ticker;

@Component
public class TickerMessageProcessor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Ticker deserializeTicker(String tickerMessageString) throws JsonProcessingException {
        Ticker ticker = null;
        var root = objectMapper.readTree(tickerMessageString);
        var channelNode = root.get("channel");
        if(channelNode != null){
            String channel = channelNode.asText();
            // the data tree is wrapped in a list, so get the list with index 0 and then access values.
            var dataNode = root.get("data").get(0);
            if(channel.equals("ticker") && dataNode != null) {
                String symbol = dataNode.get("symbol").asText();
                double price = dataNode.get("last").asDouble(); // last traded price
                double volume = dataNode.get("volume").asDouble();
                ticker = new Ticker(symbol, price, volume);
            }
        }
        return ticker;
    }
}
