import java.util.Date;

public class KashMoneyTransaction {
    // enums
    public enum KashMoneyTransactionStatus {
        GREYPERIOD, ACTIVE, COMPLETE, CANCELLED
    }

    public enum KashMoneyDeliveryOption {
        STANDARD, EXPRESS
    }

    // Attributes
    // Constants
    private final int id;
    private final long postTime;
    private final float greyPeriod;

    // Involved Parties
    private KashMoneyUserAccount customer;
    private KashMoneyDeliveryAccount deliveryAgent;

    // Transaction info
    private KashMoneyTransactionStatus status;
    // TODO: Implement member to hold denomination mix.
    private KashMoneyDeliveryOption deliveryOption;
    private float totalCost;
    private String signature;

    // Methods

    public int getId() {
        return id;
    }

    public KashMoneyUserAccount getCustomer() {
        return customer;
    }

    public KashMoneyDeliveryAccount getDeiveryAgent() {
        return deliveryAgent;
    }

    public KashMoneyTransactionStatus getStatus() {
        return status;
    }

    public void setStatus(KashMoneyTransactionStatus status) {
        this.status = status;
    }

    // TODO: Figure out how to handle denomination mix.

    public KashMoneyDeliveryOption getDeliveryOption() {
        return deliveryOption;
    }

    public void setDeliveryOption(KashMoneyDeliveryOption deliveryOption) {
        this.deliveryOption = deliveryOption;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean inGreyPeriod() {
        long currentTime = new Date().getTime();
        float timeElapsed = currentTime - postTime / 360000;

        if (timeElapsed < greyPeriod) {
            return true;
        }
        else {
            if (status.equals(KashMoneyTransactionStatus.GREYPERIOD)) {
                status = KashMoneyTransactionStatus.ACTIVE;
            }
            return false;
        }
    }

    KashMoneyTransaction(KashMoneyUserAccount customer, KashMoneyDeliveryAccount deliveryAgent, int id, float greyPeriod){
        this.customer = customer;
        this.deliveryAgent = deliveryAgent;
        this.id = id;
        this.greyPeriod = greyPeriod;
        status = KashMoneyTransactionStatus.GREYPERIOD;
        postTime = new Date().getTime();
    }
}