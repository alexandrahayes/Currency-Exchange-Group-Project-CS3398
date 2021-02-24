import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class KashMoneyUserPortal extends JFrame {
    private KashMoneyServer server;
    private KashMoneyUserAccount account;
    private JPanel navBar;

    // state variables
    private boolean firstRun = true;

    // Used for storing currency information
    private ArrayList<Fiat> currency = new ArrayList<Fiat>();

    // Core Swing Components
    private JLabel statusLabel = new JLabel("");

    // Display Transaction page
    private void displayTransactionPage() {
        if (!firstRun) {
            getContentPane().removeAll(); // remove all components.
        }

        // Recreate our objects.
        ArrayList<JPanel> panel = new ArrayList<JPanel>();
        int idx = 0;

        // Title
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Transactions"));
        idx++;

        // TODO: Implement function to display transactions
        ArrayList<KashMoneyTransaction> transactions = server.getTransactions(account);
        

        // Create Transaction Button
        panel.add(new JPanel());
        JButton createTransactionButton = new JButton("New Transaction");
        createTransactionButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCreateTransactionPage();
            }
        });
        panel.get(idx).add(createTransactionButton);

        // Add components to frame.
        JPanel contentPanel = new JPanel();
        for (JPanel p : panel) {
            contentPanel.add(p);
        }
        contentPanel.setLayout(new GridLayout(idx+1, 1));
        add(contentPanel);
        
        // Create navbar
        getNavBar();

        setLayout(new GridLayout(2, 1));
        setVisible(true);

        if (!firstRun) {
            revalidate();
            repaint();
        } else {
            validate();
            firstRun = false;
        }

        pack();
        setLocationRelativeTo(null);
    }

    // Display Transaction page.
    private void displayCreateTransactionPage() {
        if (!firstRun) {
            getContentPane().removeAll(); // remove all components.
        }

        // Recreate our objects.
        ArrayList<JPanel> panel = new ArrayList<JPanel>();
        statusLabel = new JLabel("");
        int idx = 0;

        // Title
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Create Transaction"));
        idx++;

        // Convert From ... To ...
        ArrayList<String> currencyList = getCurrencyList();
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Exchanging from"));
        JComboBox convertFrom = new JComboBox(currencyList.toArray());
        panel.get(idx).add(convertFrom);
        panel.get(idx).add(new JLabel("to"));
        JComboBox convertTo = new JComboBox(currencyList.toArray());
        panel.get(idx).add(convertTo);
        idx++;

        // Denomination mix.
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Denomination(s)"));
        JTextField denominationField = new JTextField(20);
        panel.get(idx).add(denominationField);
        idx++;
        
        // Delivery fee.
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Delivery Fee"));
        JTextField deliveryFeeField = new JTextField(20);
        // TODO: Calculate fee and display proper symbol.
        deliveryFeeField.setEditable(false);
        panel.get(idx).add(deliveryFeeField);
        idx++;

        // Transaction fee.
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Transaction Fee"));
        JTextField transactionFeeField = new JTextField(10);
        // TODO: Calculate fee and display proper symbol.
        transactionFeeField.setEditable(false);
        panel.get(idx).add(transactionFeeField);
        idx++;

        // Tax.
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Tax"));
        JTextField taxField = new JTextField(10);
        // TODO: Calculate fee and display proper symbol.
        taxField.setEditable(false);
        panel.get(idx).add(taxField);
        idx++;

        // Total amount.
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Total"));
        JTextField totalAmountField = new JTextField(20);
        // TODO: Calculate total and display proper symbol.
        totalAmountField.setEditable(false);
        panel.get(idx).add(totalAmountField);
        idx++;

        // Setup for the buttons used
        panel.add(new JPanel());
        JButton submitButton = new JButton("Submit");
        // submitButton.addActionListener(new java.awt.event.ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         // TODO: Implement Transactions and a method to submit them.
        //     }
        // });
        panel.get(idx).add(submitButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayTransactionPage();
            }
        });
        panel.get(idx).add(cancelButton);

        // Add components to frame.
        JPanel contentPanel = new JPanel();
        for (JPanel p : panel) {
            contentPanel.add(p);
        }
        contentPanel.setLayout(new GridLayout(idx+1, 1));
        add(contentPanel);

        // Create navbar
        getNavBar();

        setLayout(new GridLayout(2, 1));
        setVisible(true);
        
        if (!firstRun) {
            revalidate();
            repaint();
        } else {
            validate();
            firstRun = false;
        }
        pack();
        setLocationRelativeTo(null);
    }

    // Display Account page.
    private void displayAccountPage() {
        if (!firstRun) {
            getContentPane().removeAll(); // remove all components.
        }

        // Recreate our objects.
        ArrayList<JPanel> panel = new ArrayList<JPanel>();
        int idx = 0;

        // Title
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Manage Account"));
        idx++;
        
        // Username field
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Username"));
        JTextField username = new JTextField(20);
        username.setText(account.getUsername());
        username.setEditable(false);
        panel.get(idx).add(username);
        idx++;
        
        // Password Field
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Password"));
        JPasswordField password = new JPasswordField(20);
        password.setEchoChar('*');
        panel.get(idx).add(password);
        idx++;

        // Name
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Name"));
        // First name
        JTextField firstName = new JTextField(15);
        firstName.setText(account.getFirstName());
        panel.get(idx).add(firstName);
        // Middle initial
        JTextField middleInitial = new JTextField(1);
        middleInitial.setText(account.getMiddleInitial());
        panel.get(idx).add(middleInitial);
        // Last name
        JTextField lastName = new JTextField(15);
        lastName.setText(account.getLastName());
        panel.get(idx).add(lastName);
        idx++;

        // mailingAddress
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Mailing Address"));
        JTextField mailingAddress = new JTextField(20);
        mailingAddress.setText(account.getMailingAddress());
        panel.get(idx).add(mailingAddress);
        idx++;

        // emailAddress
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Email Address"));
        JTextField emailAddress = new JTextField(20);
        emailAddress.setText(account.getEmailAddress());
        panel.get(idx).add(emailAddress);
        idx++;

        // phoneNumber
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Phone Number"));
        JTextField phoneNumber = new JTextField(10);
        phoneNumber.setText(account.getPhoneNumber());
        panel.get(idx).add(phoneNumber);
        idx++;

        // paymentInfo
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Payment Info"));
        JTextField paymentInfo = new JTextField(20);
        paymentInfo.setText(account.getPaymentInfo());
        panel.get(idx).add(paymentInfo);
        idx++;

        // Buttons
        panel.add(new JPanel());
        JButton submitButton = new JButton("Save Changes");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!new String(password.getPassword()).equals("")) {
                    account.setPAssword(new String(password.getPassword()));
                }

                account.setFirstName(firstName.getText());
                account.setMiddleInitial(middleInitial.getText());
                account.setLastName(lastName.getText());
                account.setMailingAddress(mailingAddress.getText());
                account.setEmailAddress(emailAddress.getText());
                account.setPhoneNumber(phoneNumber.getText());
                account.setPaymentInfo(paymentInfo.getText());
                statusLabel.setText("Changes saved.");
            }
        });
        panel.get(idx).add(submitButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayTransactionPage();
            }
        });
        panel.get(idx).add(cancelButton);
        idx++;

        // Status Label
        panel.add(new JPanel());
        statusLabel = new JLabel("");
        panel.get(idx).add(statusLabel);

        // Add JPanels to JFrame.
        for (JPanel p : panel) {
            this.add(p);
        }
        
        setLayout(new GridLayout(idx+1, 1));
        setVisible(true);
        
        if (!firstRun) {
            revalidate();
            repaint();
        } else {
            validate();
            firstRun = false;
        }
        pack();
        setLocationRelativeTo(null);
    }

    private void displayIssueCreation(){
        if (!firstRun) {
            getContentPane().removeAll(); // remove all components.
        }

        // Recreate our objects.
        ArrayList<JPanel> panel = new ArrayList<JPanel>();
        statusLabel = new JLabel("");
        int idx = 0;

        // Title
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Submit Issue"));
        idx++;

        // Description box
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Issue"));
        JTextArea issueArea = new JTextArea(3,20);
        panel.get(idx).add(issueArea);
        idx++;

        // Buttons
        panel.add(new JPanel());
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.submitIssue(KashMoneyUserPortal.this.account, issueArea.getText());
                statusLabel.setText("Issue submitted!");
            }
        });
        panel.get(idx).add(submitButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayIssuePage();
            }
        });
        panel.get(idx).add(cancelButton);
        idx++;

        panel.add(new JPanel());
        panel.get(idx).add(statusLabel);

        // Add components to frame.
        JPanel contentPanel = new JPanel();
        for (JPanel p : panel) {
            contentPanel.add(p);
        }
        contentPanel.setLayout(new GridLayout(idx+1, 1));
        add(contentPanel);

        // Create navbar
        getNavBar();

        setLayout(new GridLayout(2, 1));
        setVisible(true);
        
        if (!firstRun) {
            revalidate();
            repaint();
        } else {
            validate();
            firstRun = false;
        }
        pack();
        setLocationRelativeTo(null);
    }

    private void displayIssuePage() {
        if (!firstRun) {
            getContentPane().removeAll(); // remove all components.
        }

        // Recreate our objects.
        ArrayList<JPanel> panel = new ArrayList<JPanel>();
        statusLabel = new JLabel("");
        int idx = 0;

        // Title
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Your Issues"));
        idx++;

        // Label columns
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("ID"));
        panel.get(idx).add(new JLabel("Status"));
        panel.get(idx).add(new JLabel("Responder"));
        panel.get(idx).add(new JLabel("Response Type"));
        panel.get(idx).add(new JLabel("Date"));
        idx++;

        ArrayList<KashMoneyIssue> issues = server.getIssues(account);
        for (KashMoneyIssue issue : issues) {
            panel.add(new JPanel());
            // ID
            JTextField idField = new JTextField(String.valueOf(issue.getId()), 3);
            idField.setEditable(false);
            panel.get(idx).add(idField);
            // Status
            JTextField statusField = new JTextField(issue.getStatus().toString(), 8);
            statusField.setEditable(false);
            panel.get(idx).add(statusField);
            // Responder
            String responderUsername = new String ("");
            if (issue.getResponder() != null) {
                responderUsername = issue.getResponder().getUsername();
            }
            JTextField responderField = new JTextField(responderUsername, 8);
            responderField.setEditable(false);
            panel.get(idx).add(responderField);
            // Response Type
            String responseType = new String ("");
            if (issue.getResponder() != null) {
                responseType = issue.getResponseType().toString();
            }
            JTextField responseTypeField = new JTextField(responseType, 8);
            responseTypeField.setEditable(false);
            panel.get(idx).add(responseTypeField);
            // Date
            String responseDate = new String ("");
            if (issue.getResponseDate() != null) {
                responseDate = issue.getResponseDate().toString();
            }
            JTextField responseDateField = new JTextField(responseDate, 8);
            responseDateField.setEditable(false);
            panel.get(idx).add(responseDateField);
            idx++;
        }

        // Create Issue button
        panel.add(new JPanel());
        JButton createIssueButton = new JButton("Create Issue");
        createIssueButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayIssueCreation();
            }
        });
        panel.get(idx).add(createIssueButton);
        idx++;

        panel.add(new JPanel());
        panel.get(idx).add(statusLabel);

        // Add components to frame.
        JPanel contentPanel = new JPanel();
        for (JPanel p : panel) {
            contentPanel.add(p);
        }
        contentPanel.setLayout(new GridLayout(idx+1, 1));
        add(contentPanel);

        // Create navbar
        getNavBar();

        setLayout(new GridLayout(2, 1));
        setVisible(true);
        
        if (!firstRun) {
            revalidate();
            repaint();
        } else {
            validate();
            firstRun = false;
        }
        pack();
        setLocationRelativeTo(null);
    }

    // Helper function: Creates navbar and adds it to frame.
    private void getNavBar() {
        navBar = new JPanel();
        navBar.setLayout(new GridBagLayout());

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.PAGE_END;
        left.gridx = 0;
        left.weightx = 0.5D;
        left.weighty = 0.5D;
        left.fill = GridBagConstraints.NONE;

        GridBagConstraints middle1 = new GridBagConstraints();
        middle1.anchor = GridBagConstraints.PAGE_END;
        middle1.gridx = 1;
        middle1.weightx = 0.5D;
        middle1.weighty = 0.5D;
        middle1.fill = GridBagConstraints.NONE;

        GridBagConstraints middle2 = new GridBagConstraints();
        middle2.anchor = GridBagConstraints.PAGE_END;
        middle2.gridx = 2;
        middle2.weightx = 0.5D;
        middle2.weighty = 0.5D;
        middle2.fill = GridBagConstraints.NONE;

        GridBagConstraints right = new GridBagConstraints();
        right.anchor = GridBagConstraints.PAGE_END;
        right.gridx = 3;
        right.weightx = 0.5D;
        right.weighty = 0.5D;
        right.fill = GridBagConstraints.NONE;

        JButton transactionPageButton = new JButton("View Transactions");
        transactionPageButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayTransactionPage();
            }
        });
        navBar.add(transactionPageButton, left);

        JButton issuePageButton = new JButton("Issues");
        issuePageButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayIssuePage();
            }
        });
        navBar.add(issuePageButton, middle1);

        JButton accountPageButton = new JButton("Manage Account");
        accountPageButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAccountPage();
            }
        });
        navBar.add(accountPageButton, middle2);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KashMoneyUserPortal.this.server.logout(account);
                WindowEvent closingEvent = new WindowEvent(KashMoneyUserPortal.this, WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);
            }
        });
        navBar.add(logoutButton, right);

        add(BorderLayout.SOUTH, navBar);
    }

    // Helper function: Build a list of currencies to use in dropdowns.
    private ArrayList<String> getCurrencyList() {
        ArrayList<String> currencyList = new ArrayList<String>();
        for (Fiat c : currency) {
            currencyList.add(c.getName());
        }
        return currencyList;
    }

    // Constructor
    public KashMoneyUserPortal(KashMoneyServer server, KashMoneyUserAccount account){
        this.server = server;
        this.account = account;

        // Window options
        setTitle("KashMoneyUserPortal");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // TODO: Implement WindowListener to log us out on close.

        // Default page
        displayTransactionPage();
    }
}