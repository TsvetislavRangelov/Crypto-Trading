package trading.crypto.services;


public interface UserService {
    void tryRegisterUser(String username);
    boolean exists(String username);
    double resetCashBalance(String username);
    double getCashBalance(String username);
}
