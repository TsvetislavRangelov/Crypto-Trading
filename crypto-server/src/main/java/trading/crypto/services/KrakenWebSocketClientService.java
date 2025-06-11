package trading.crypto.services;

import org.springframework.stereotype.Service;
import trading.crypto.websocket.client.KrakenWebSocketClient;

import java.util.*;

// used to handle subscribe/unsubscribe to individual tickers from external
// clients. We need a facility to somehow handle subscriptions that are coming
// outside of the server, such as is the case with the angular client.
// Also, this should implement an interface to facilitate DI but I
// have my final exam in a week and a half and I don't have time to bother with this.
@Service
public class KrakenWebSocketClientService {

    private final KrakenWebSocketClient krakenWebSocketClient;

    public KrakenWebSocketClientService(KrakenWebSocketClient krakenWebSocketClient) {
        this.krakenWebSocketClient = krakenWebSocketClient;
    }


    public void subscribeToIndividualTicker(List<String> symbols){
        this.krakenWebSocketClient.handleSubscriptionForTickerPairs(symbols, "subscribe");
    }

    public void unsubscribeFromTickers(List<String> symbols){
        this.krakenWebSocketClient.handleSubscriptionForTickerPairs(symbols, "unsubscribe");
    }

}
