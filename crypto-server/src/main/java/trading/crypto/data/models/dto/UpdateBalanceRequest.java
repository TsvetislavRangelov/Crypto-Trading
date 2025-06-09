package trading.crypto.data.models.dto;

public class UpdateBalanceRequest {
    private String username;
    private double newAmount;

    public String getUsername() {
        return username;
    }

    public double getNewAmount() {
        return newAmount;
    }
}
