package trading.crypto.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trading.crypto.services.MarketDataService;
import java.util.*;

@RestController
@RequestMapping("/pairs")
public class CryptoPairsController {
    private final MarketDataService marketDataService;

    public CryptoPairsController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @GetMapping("/top")
    public ResponseEntity<List<String>> getCryptoPairs() {
        var res = marketDataService.getTopCryptoPairs(20);
        return ResponseEntity.ok().body(res);
    }
}
