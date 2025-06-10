package trading.crypto.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import trading.crypto.services.HoldingService;
import trading.crypto.services.TransactionService;
import trading.crypto.services.UserService;

@RestController
@CrossOrigin(origins = "*")
public class ResetController {

    private final HoldingService holdingService;
    private final UserService userService;
    private final TransactionService transactionService;

    public ResetController(HoldingService holdingService, UserService userService, TransactionService transactionService) {
        this.holdingService = holdingService;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/reset/{username}")
    public void resetAccount(@PathVariable String username) {
        var user = userService.tryRegisterUser(username);
        if(user != null) {
            this.userService.updateCashBalance(username, 10000);
            this.transactionService.deleteForUserId(user.getId());
            this.holdingService.deleteForUserId(user.getId());
        }
    }
}
