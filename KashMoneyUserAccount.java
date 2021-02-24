import java.util.ArrayList;

class KashMoneyUserAccount extends KashMoneyAccount {
    // Attributes
    private String firstName;
    private String lastName;
    private String middleInitial;
    private String mailingAddress;
    private String emailAddress;
    private String phoneNumber;
    private String paymentInfo;

    // Methods
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        lastName = name;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String initial) {
        middleInitial = initial;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String address) {
        mailingAddress = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String address) {
        emailAddress = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String number) {
        phoneNumber = number;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String info) {
        paymentInfo = info;
    }

    KashMoneyUserAccount(ArrayList<String> registrationInfo) {
        username = registrationInfo.get(0);
        password = registrationInfo.get(1);
        firstName = registrationInfo.get(2);
        middleInitial = registrationInfo.get(3);
        lastName = registrationInfo.get(4);
        mailingAddress = registrationInfo.get(5);
        emailAddress = registrationInfo.get(6);
        phoneNumber = registrationInfo.get(7);
        paymentInfo = registrationInfo.get(8);
        type = KashMoneyAccountType.USER;
    }
}