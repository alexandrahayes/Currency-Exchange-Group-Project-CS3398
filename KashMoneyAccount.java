public class KashMoneyAccount {
    // Enums
    public enum KashMoneyAccountType {
        USER, DELIVERY, ADMIN, INVALID
    }

    // Attributes
    protected String username;
    protected String password;
    protected boolean loggedIn;
    protected KashMoneyAccountType type;

    // Methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPAssword(String password) {
        this.password = password;
    }

    public boolean getLoginStatus() {
        return loggedIn;
    }

    public void setLoginStatus(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    // We cannot convert accounts, so no setter has been implemented.
    public KashMoneyAccountType getAccountType() {
        return type;
    }

    KashMoneyAccount() {
        // NOTE: May cause errors.
        type = KashMoneyAccountType.INVALID;
    }
}