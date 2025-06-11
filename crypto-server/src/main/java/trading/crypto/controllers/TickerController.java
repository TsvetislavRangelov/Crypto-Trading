package trading.crypto.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.util.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import trading.crypto.data.models.Ticker;
import trading.crypto.services.KrakenWebSocketClientService;
import trading.crypto.services.TickerService;

@Controller
@CrossOrigin(origins = "*")
public class TickerController {

    private final TickerService tickerService;
    private final KrakenWebSocketClientService krakenWebSocketClientService;

    public TickerController(TickerService tickerService, KrakenWebSocketClientService krakenWebSocketClientService) {
        this.krakenWebSocketClientService = krakenWebSocketClientService;
        this.tickerService = tickerService;
    }

    @MessageMapping("/tickers")
    @SendTo("/channel/tickers")
    public List<Ticker> subscribeToTickers() {
        return tickerService.getTop20Tickers();
    }
}
