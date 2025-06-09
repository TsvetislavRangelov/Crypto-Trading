package trading.crypto.services;


import trading.crypto.data.models.User;

public interface UserService {
    User tryRegisterUser(String username);
    boolean exists(String username);
    double resetCashBalance(String username);
    double getCashBalance(String username);
}
