package trading.crypto.controllers;

import org.springframework.web.bind.annotation.*;
import trading.crypto.data.models.Holding;
import trading.crypto.services.HoldingService;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class HoldingController {

    private final HoldingService holdingService;

    public HoldingController(HoldingService holdingService) {
        this.holdingService = holdingService;
    }

    @GetMapping("/holdings/user/{userId}")
    public List<Holding> getHoldings(@PathVariable long userId) {
        return this.holdingService.getALlForUserId(userId);
    }

    @DeleteMapping("/holdings/delete/{userId}")
    public boolean deleteHoldings(@PathVariable long userId, @RequestParam String symbol) {
        return this.holdingService.deleteHolding(userId, symbol);
    }

    @GetMapping("/holdings/{userId}")
    public Holding getHolding(@PathVariable long userId, @RequestParam String symbol) {
        return this.holdingService.getByUserIdAndSymbol(userId, symbol);
    }

    @PostMapping("/holdings")
    public void registerHolding(@RequestBody Holding holding) {
        var existingHolding = holdingService.getById(holding.getId());
        if(existingHolding != null) {
            this.holdingService.updateHolding(holding);
        }
        else{
            this.holdingService.registerHolding(holding);
        }
    }
}
