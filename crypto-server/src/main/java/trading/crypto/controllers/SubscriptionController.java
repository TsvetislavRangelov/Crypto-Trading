package trading.crypto.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import trading.crypto.services.KrakenWebSocketClientService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class SubscriptionController {

    private final KrakenWebSocketClientService krakenWebSocketClientService;

    public SubscriptionController(KrakenWebSocketClientService krakenWebSocketClientService) {
        this.krakenWebSocketClientService = krakenWebSocketClientService;
    }

    @PostMapping("/subscribe/tickers")
    public void subscribeToTicker(@RequestBody List<String> symbols) {
        this.krakenWebSocketClientService.subscribeToIndividualTicker(symbols);
    }

    @PostMapping("/unsubscribe/tickers")
    public void unsubscribeFromTicker(@RequestBody List<String> symbols) {
        this.krakenWebSocketClientService.unsubscribeFromTickers(symbols);
    }
}
