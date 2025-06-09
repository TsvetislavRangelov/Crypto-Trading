package trading.crypto.services;


import trading.crypto.data.models.User;

public interface UserService {
    User tryRegisterUser(String username);
    boolean exists(String username);
    double getCashBalance(String username);
    void updateCashBalance(String username, double newAmount);
}
