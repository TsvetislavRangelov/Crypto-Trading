package trading.crypto.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trading.crypto.services.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody String username){
        userService.tryRegisterUser(username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset")
    public double reset(@RequestBody String username){
        return userService.resetCashBalance(username);
    }

    @GetMapping("/cash/{username}")
    public double getCashBalance(@PathVariable String username){
        return userService.getCashBalance(username);
    }
}
