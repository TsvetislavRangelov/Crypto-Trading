package trading.crypto.services;

import trading.crypto.data.models.Ticker;
import java.util.*;

public interface TickerService {
    // forwards the update responses from kraken to the STOMP controller
    void forwardTickerMessage(String topic, String message);
    // gets the top 20 cryptos for the initial client subscription.
    List<Ticker> getTop20Tickers();
}
