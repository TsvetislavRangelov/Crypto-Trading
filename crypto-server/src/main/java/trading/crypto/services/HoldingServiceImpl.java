package trading.crypto.services;

import org.springframework.stereotype.Service;
import trading.crypto.data.models.Holding;
import trading.crypto.repository.HoldingRepository;

import java.util.List;

@Service
public class HoldingServiceImpl implements HoldingService {

    private final HoldingRepository holdingRepository;

    public HoldingServiceImpl(HoldingRepository holdingRepository) {
        this.holdingRepository = holdingRepository;
    }

    @Override
    public void registerHolding(Holding holding) {
        this.holdingRepository.save(holding);
    }

    @Override
    public boolean deleteHolding(long userId, String symbol) {
        return this.holdingRepository.delete(userId, symbol);
    }

    @Override
    public void deleteForUserId(long userId) {
        this.holdingRepository.deleteForUserId(userId);
    }

    @Override
    public void updateHolding(Holding holding) {
        this.holdingRepository.update(holding);
    }

    @Override
    public List<Holding> getALlForUserId(long id) {
        return this.holdingRepository.findAllForUserId(id);
    }

    @Override
    public Holding getById(long id) {
        return this.holdingRepository.findById(id);
    }

    @Override
    public Holding getByUserIdAndSymbol(long userId, String symbol) {
        return this.holdingRepository.findByUserIdAndSymbol(userId, symbol);
    }
}
