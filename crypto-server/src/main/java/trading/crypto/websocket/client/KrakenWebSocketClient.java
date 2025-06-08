package trading.crypto.websocket.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import trading.crypto.data.models.Pair;
import trading.crypto.services.MarketDataService;
import trading.crypto.services.TickerService;

import java.util.*;
import java.net.URI;

@Component
public class KrakenWebSocketClient extends WebSocketClient {
    List<Pair> pairs;
    private final MarketDataService marketDataService;
    private final TickerService tickerService;
    private static final Logger logger = LoggerFactory.getLogger(KrakenWebSocketClient.class);

    public KrakenWebSocketClient(
            @Value("${kraken.ws.url}") String krakenWsUrl,
            MarketDataService marketDataService,
            TickerService tickerService)
            throws Exception {
        super(new URI(krakenWsUrl));
        this.marketDataService = marketDataService;
        this.tickerService = tickerService;
        pairs = new ArrayList<>(20);
        connect();
    }
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        logger.info("Connected to kraken ws");
        resubscribe();
    }

    @Override
    public void onMessage(String message) {
        logger.info("Received message: {}", message);
        if(message.contains("update") || message.contains("snapshot")){
            tickerService.forwardTickerMessage("/channel/ticker", message);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.info("Closing websocket: {}", reason);
        handleSubscriptionForTickerPairs(pairs, "unsubscribe");
    }

    @Override
    public void onError(Exception ex) {
        logger.error("Error in KrakenWebSocketClient: {}", ex.getMessage());
    }

    private String buildWsMessage(String method, JSONObject params) {
        return new JSONObject()
                .put("method", method)
                .put("params", params).toString();
    }

    private JSONObject buildRequestJsonObject(String channelParam, List<Pair> symbolParam){
        return new JSONObject().put("channel", channelParam)
                .put("symbol",
                        new JSONArray(symbolParam.stream().map(Pair::getName).toArray()));
    }

    // cron job to periodically resubscribe to top 20 coins.
    @Scheduled(fixedRate = 50000000)
    private void resubscribe(){
        if(isOpen()){
            // avoid race conditions where one thread writes to pairNames and another reads at the same time.
            synchronized (this) {
                // first unsubscribe from the current list of pairs.
                handleSubscriptionForTickerPairs(pairs, "unsubscribe");
                // get the new pairs from kraken
                pairs = marketDataService.getTopCryptoPairs(20);
                // and resubscribe.
                handleSubscriptionForTickerPairs(pairs, "subscribe");
            }
        }
    }

    private void handleSubscriptionForTickerPairs(List<Pair> pairs, String method) {
        if(!pairs.isEmpty()){
            var subscriptionParams = buildRequestJsonObject("ticker", pairs);
            String subscriptionMessage = buildWsMessage(method, subscriptionParams);
            send(subscriptionMessage);
        }
    }
}