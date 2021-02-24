import java.util.ArrayList;

public class KashMoneyServer {
////////////////
/// Memebers ///
////////////////

    private KashMoneyLoginPortal loginPortal; // Note: We can simulate simultaneous clients by making this an ArrayList
    private ArrayList<KashMoneyAccount> account;
    private ArrayList<KashMoneyIssue> issue = new ArrayList<KashMoneyIssue>();
    private ArrayList<KashMoneyTransaction> transactions = new ArrayList<KashMoneyTransaction>();
    private float greyPeriod;

///////////////
/// Methods ///
///////////////

    ///////////////////
    // Account Login //
    ///////////////////
    public String login(String username, String password) {
        for (KashMoneyAccount acc : account) {
            if (acc.getUsername().equals(username) && acc.getPassword().equals(password)) {
                if (acc.getLoginStatus()==false) {
                    acc.setLoginStatus(true);
                    loginPortal.setAccount(acc);
                    return "Account logged in successfully!";
                }
                else {
                    return "Account is already logged in";
                }
            }
        }
        return "Invalid login";
    }

    public void logout(KashMoneyAccount acc){
        if (acc!= null){
            acc.setLoginStatus(false);
            for (KashMoneyAccount a : account) {
                if (acc.getUsername().equals(a.getUsername())) {
                    a.setLoginStatus(false);
                    loginPortal.logout();
                }
            }
        }
    }
    
    ////////////////////////
    // Account Management //
    ////////////////////////
    public void createUserAccount(ArrayList<String> registrationInfo) {
        account.add(new KashMoneyUserAccount(registrationInfo));
    }

    public void createDeliveryAccount(String username, String password) {
        account.add(new KashMoneyDeliveryAccount(username, password));
    }
    
    public void createAdminAccount(String username, String password) {
        account.add(new KashMoneyAdminAccount(username, password));
    }

    public void deleteAccount(KashMoneyAccount account) {
        this.account.remove(account);
    }

    public ArrayList<KashMoneyAccount> getAccounts() {
        return account;
    }

    public KashMoneyAccount getAccount(String username, String password) {
        KashMoneyAccount acc = null;

        for (KashMoneyAccount a : account) {
            if (a.getUsername().equals(username) && a.getPassword().equals(password)) {
                acc = a;
                break;
            }
        }
    
        return acc;
    }

    //////////////////////
    // Issue Management //
    //////////////////////
    public void submitIssue(KashMoneyUserAccount account, String content) {
        issue.add(new KashMoneyIssue(account.getUsername(), content, String.valueOf(transactions.size())));
    }

    public void editIssue(KashMoneyIssue issue, KashMoneyAdminAccount responder, KashMoneyIssue.KashMoneyIssueResponseType responseType) {
        issue.setResponder(responder);
        issue.setResponseType(responseType);
        issue.setStatus(KashMoneyIssue.KashMoneyIssueStatus.CLOSED);
    }

    public ArrayList<KashMoneyIssue> getIssues(KashMoneyUserAccount account) {
        if (account == null) {
            return issue;
        }
        else {
            ArrayList<KashMoneyIssue> issueList = new ArrayList<KashMoneyIssue>();
            for (KashMoneyIssue iss : issue) {
                if (account.getUsername().equals(iss.getIssuer())) {
                    issueList.add(iss);
                }
            }
            return issueList;
        }
    }

    ////////////////////////////
    // Transaction Management //
    ////////////////////////////
    private float getGreyPeriod() {
        return greyPeriod;
    }

    private void setGreyPeriod(float greyPeriod) {
        this.greyPeriod = greyPeriod;
    }

    public void submitTransaction(KashMoneyUserAccount account, String content) {
        // Pick a delivery agent to deliver the transaction.
        KashMoneyDeliveryAccount deliveryAgent = null;
        int leastDeliveries = 0;
        int deliveries;
        int idx = 0;
        for (KashMoneyAccount acc : this.account) {
            if (acc.getAccountType().equals(KashMoneyAccount.KashMoneyAccountType.DELIVERY)) {
                if (idx==0) {
                    leastDeliveries = getTransactions(acc).size();
                }
                else {
                    deliveries = getTransactions(acc).size();
                    if (deliveries <= leastDeliveries) {
                        deliveryAgent = (KashMoneyDeliveryAccount)acc;
                    }
                }
            }
            idx++;
        }
        transactions.add(new KashMoneyTransaction(account, deliveryAgent, transactions.size(), getGreyPeriod()));
    }

    public void cancelTransaction(KashMoneyTransaction transaction) {
        if (transaction.getStatus().equals(KashMoneyTransaction.KashMoneyTransactionStatus.GREYPERIOD)) {
            transaction.setStatus(KashMoneyTransaction.KashMoneyTransactionStatus.CANCELLED);
            // TODO: Get canellation cost.
            //float cost = transaction.getCancellationFee();
        }
    }

    public ArrayList<KashMoneyTransaction> getTransactions(KashMoneyAccount account) {
        if (account == null) {
            return transactions;
        }
        else {
            ArrayList<KashMoneyTransaction> transactionList = new ArrayList<KashMoneyTransaction>();
            // Search by User / Customer
            if (account.getAccountType().equals(KashMoneyAccount.KashMoneyAccountType.USER)) {
                for (KashMoneyTransaction transaction : transactions) {
                    if (account.getUsername().equals(transaction.getCustomer().getUsername())) {
                        transactionList.add(transaction);
                    }
                }
            }
            // Search by Delivery Agent
            else if (account.getAccountType().equals(KashMoneyAccount.KashMoneyAccountType.DELIVERY)) {
                for (KashMoneyTransaction transaction : transactions) {
                    if (account.getUsername().equals(transaction.getDeiveryAgent().getUsername())) {
                        transactionList.add(transaction);
                    }
                }
            }
            return transactionList;
        }
    }

    ////////////////////
    // Constructor(s) //
    ////////////////////
    public KashMoneyServer() {
        loginPortal = new KashMoneyLoginPortal(this);
        account = new ArrayList<KashMoneyAccount>();
        createAdminAccount("root", "toor");
    }

    public static void main(String [] args) {
        KashMoneyServer server = new KashMoneyServer();
    }
}