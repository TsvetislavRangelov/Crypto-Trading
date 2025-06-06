package trading.crypto.websocket.client;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import trading.crypto.services.MarketDataService;

@Service
public class KrakenWebSocketSubscriptionHandler {

    private final MarketDataService marketDataService;
    private final KrakenWebSocketClient krakenWebSocketClient;

    public KrakenWebSocketSubscriptionHandler(
            KrakenWebSocketClient krakenWebSocketClient,
            MarketDataService marketDataService) {
        this.krakenWebSocketClient = krakenWebSocketClient;
        this.marketDataService = marketDataService;
    }

    // cron job to periodically resubscribe to top 20 coins.
    @Scheduled(fixedRate = 3600000)
    public void resubscribe(){
        var topCoins = marketDataService.getTopCryptoPairs(20);
        krakenWebSocketClient.subscribeToPairs(topCoins);
    }
}
