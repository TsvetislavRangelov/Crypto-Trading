package trading.crypto.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trading.crypto.data.models.User;
import trading.crypto.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User tryRegisterUser(String username) {
        boolean exists = exists(username);
        if(!exists){
            // 10k initial cash balance.
            userRepository.save(new User(0, username, 10000));
        }
        User user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public boolean exists(String username) {
        return userRepository.exists(username);
    }

    @Override
    public double resetCashBalance(String username){
        return userRepository.resetCashBalance(username);
    }

    @Override
    public double getCashBalance(String username) {
        return userRepository.getCashBalance(username);
    }

}
