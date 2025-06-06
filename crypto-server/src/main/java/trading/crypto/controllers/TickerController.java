package trading.crypto.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.util.*;
import trading.crypto.data.models.Ticker;
import trading.crypto.services.TickerService;

@Controller
public class TickerController {

    private TickerService tickerService;

    public TickerController(TickerService tickerService) {
        this.tickerService = tickerService;
    }

    @MessageMapping("/ticker")
    @SendTo("/channel/ticker")
    public List<Ticker> subscribeToTickers() {
        return tickerService.getTop20Tickers();
    }
}
