package trading.crypto.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trading.crypto.data.models.Transaction;
import trading.crypto.services.TransactionService;
import java.util.*;

@RestController
@CrossOrigin("*")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/registerTransaction")
    public ResponseEntity<Boolean> registerTransaction(@RequestBody Transaction transaction) {
        transactionService.createTransaction(transaction);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/transactions/{userId}")
    public List<Transaction> getTransactions(@PathVariable long userId){
        return transactionService.getAllForUser(userId);
    }
}
