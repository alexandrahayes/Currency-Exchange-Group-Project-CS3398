public class KashMoneyDeliveryAccount extends KashMoneyAccount {
    // Attributes

    // Methods
    KashMoneyDeliveryAccount(String username, String password) {
        this.username = username;
        this.password = password;
        type = KashMoneyAccountType.DELIVERY;
    }
}