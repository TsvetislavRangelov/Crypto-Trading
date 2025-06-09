package trading.crypto.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trading.crypto.data.models.User;
import trading.crypto.services.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody String username){
        var user = userService.tryRegisterUser(username);
        return ResponseEntity.ok().body(user);
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
