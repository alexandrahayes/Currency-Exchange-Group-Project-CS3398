public class KashMoneyAdminAccount extends KashMoneyAccount {
    // Attributes

    // Methods
    KashMoneyAdminAccount(String username, String password) {
        this.username = username;
        this.password = password;
        type = KashMoneyAccountType.ADMIN;
    }
}