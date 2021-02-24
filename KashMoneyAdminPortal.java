import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class KashMoneyAdminPortal extends JFrame{
    private KashMoneyServer server;
    private KashMoneyAdminAccount account;
    private JPanel navBar;
    
    // state variables
    private boolean firstRun = true;

    // Core Swing Components
    private JLabel statusLabel;
    
    private void displayAccountPage() {
        if (!firstRun) {
            getContentPane().removeAll(); // remove all components.
        }

        // Recreate our objects.
        ArrayList<JPanel> panel = new ArrayList<JPanel>();
        statusLabel = new JLabel();
        int idx = 0;

        // Title
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Accounts"));
        idx++;

        // TODO: Implement method to list accounts.
        ArrayList<KashMoneyAccount> accounts = server.getAccounts();
        for (KashMoneyAccount acc : accounts) {
            panel.add(new JPanel());
            JTextField usernameField = new JTextField(acc.getUsername(), 20);
            usernameField.setEditable(false);
            panel.get(idx).add(usernameField);
            JTextField typeField = new JTextField(acc.getAccountType().toString(), 8);
            typeField.setEditable(false);
            panel.get(idx).add(typeField);
            JButton deleteButton = new JButton("Delete");
            deleteButton.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String ownUsername = KashMoneyAdminPortal.this.account.getUsername();
                    String targetUsername = usernameField.getText();
                    if (ownUsername.equals(targetUsername)) {
                        statusLabel.setText("Error: Cannot delete yourself");
                    }
                    else {
                        server.deleteAccount(acc);
                        statusLabel.setText("Account deleted");
                    }
                }
            });
            panel.get(idx).add(deleteButton);
            idx++;
        }

        // Display User Account Creation Button
        panel.add(new JPanel());
        JButton addAccountButton = new JButton("Create Account");
        addAccountButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAccountCreation();
            }
        });
        panel.get(idx).add(addAccountButton);
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
    
    private void displayAccountCreation() {
        if (!firstRun) {
            getContentPane().removeAll(); // remove all components.
        }

        // Recreate our objects.
        ArrayList<JPanel> panel = new ArrayList<JPanel>();
        statusLabel = new JLabel();
        int idx = 0;

        // Title
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Account Creation"));
        idx++;

        // Username
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Username"));
        JTextField usernameField = new JTextField(20);
        panel.get(idx).add(usernameField);
        idx++;

        // Password
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Password"));
        JPasswordField passwordField = new JPasswordField(20);
        panel.get(idx).add(passwordField);
        idx++;

        // Type
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Type"));
        JComboBox<KashMoneyAccount.KashMoneyAccountType> typeField = new JComboBox<KashMoneyAccount.KashMoneyAccountType>();
        typeField.addItem(KashMoneyAccount.KashMoneyAccountType.ADMIN);
        typeField.addItem(KashMoneyAccount.KashMoneyAccountType.DELIVERY);

        panel.get(idx).add(typeField);
        idx++;

        // Display User Account Creation Button
        panel.add(new JPanel());
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("") || password.equals("")) {
                    statusLabel.setText("Please set a username and password.");
                }
                else {
                    if (typeField.getSelectedItem().toString().equals("DELIVERY")) {
                        KashMoneyAdminPortal.this.server.createDeliveryAccount(username, password);
                        statusLabel.setText("Delivery Account Created");
                    }
                    else if (typeField.getSelectedItem().toString().equals("ADMIN")) {
                        KashMoneyAdminPortal.this.server.createAdminAccount(username, password);
                        statusLabel.setText("Admin Account Created");
                    }
                }
            }
        });
        panel.get(idx).add(submitButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAccountPage();
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
        int idx = 0;

        // Title
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Issues"));
        idx++;

        // Label columns
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Transaction ID"));
        panel.get(idx).add(new JLabel("Status"));
        panel.get(idx).add(new JLabel("Response Type"));
        idx++;

        ArrayList<KashMoneyIssue> issues = server.getIssues(null);
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
            // Response Type
            String responseType = new String("");
            if (issue.getResponseType() != null) {
                responseType = issue.getResponseType().toString();
            }
            JTextField responseTypeField = new JTextField(7);
            responseTypeField.setText(responseType);
            responseTypeField.setEditable(false);
            panel.get(idx).add(responseTypeField);
            // Add a button to Manage issue
            JButton manageButton = new JButton("Manage");
            manageButton.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayIssueViewPage(issue);
                }
            });
            panel.get(idx).add(manageButton);
            idx++;
        }

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

    public void displayIssueViewPage(KashMoneyIssue issue) {
        if (!firstRun) {
            getContentPane().removeAll(); // remove all components.
        }

        // Recreate our objects.
        ArrayList<JPanel> panel = new ArrayList<JPanel>();
        statusLabel = new JLabel("");
        int idx = 0;

        // Title
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Manage Issue"));
        idx++;

        // ID
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Transaction ID"));
        JTextField idField = new JTextField(3);
        idField.setText(issue.getId());
        idField.setEditable(false);
        panel.get(idx).add(idField);
        idx++;

        // Description box
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Issue"));
        JTextArea issueArea = new JTextArea(3,20);
        issueArea.setText(issue.getContent());
        issueArea.setEditable(false);
        panel.get(idx).add(issueArea);
        idx++;

        // Response Type selection.
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Response type"));
        JComboBox<KashMoneyIssue.KashMoneyIssueResponseType> responseTypeBox = new JComboBox<KashMoneyIssue.KashMoneyIssueResponseType>(KashMoneyIssue.KashMoneyIssueResponseType.values());
        panel.get(idx).add(responseTypeBox);
        idx++;

        // Buttons
        panel.add(new JPanel());
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.editIssue(issue, KashMoneyAdminPortal.this.account, (KashMoneyIssue.KashMoneyIssueResponseType)responseTypeBox.getSelectedItem());
                statusLabel.setText("Issue Resolved.");
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

    //Display Transactions Page
    private void displayTransactionsPage() {
        if (!firstRun) {
            getContentPane().removeAll(); // remove all components.
        }

        // Recreate our objects.
        ArrayList<JPanel> panel = new ArrayList<JPanel>();
        statusLabel = new JLabel("");
        int idx = 0;

        // Title
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("View Transactions"));
        idx++;

        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Transaction ID"));
        panel.get(idx).add(new JLabel("Customer Address"));
        panel.get(idx).add(new JLabel("Status"));
        idx++;

        ArrayList<KashMoneyTransaction> transactions = server.getTransactions(account);
        for (KashMoneyTransaction transaction : transactions) {
            panel.add(new JPanel());
            // ID
            JTextField idField = new JTextField(String.valueOf(transaction.getId()), 3);
            idField.setEditable(false);
            panel.get(idx).add(idField);
            // Address
            JTextField customerAddressField = new JTextField(transaction.getCustomer().getMailingAddress(), 20);
            customerAddressField.setEditable(false);
            panel.get(idx).add(customerAddressField);
            // Status
            JTextField deliveryStatusField = new JTextField(transaction.getStatus().toString(), 8);
            deliveryStatusField.setEditable(false);
            panel.get(idx).add(deliveryStatusField);
            idx++;
        }
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

    GridBagConstraints midLeft = new GridBagConstraints();
    midLeft.anchor = GridBagConstraints.PAGE_END;
    midLeft.gridx = 1;
    midLeft.weightx = 0.5D;
    midLeft.weighty = 0.5D;
    midLeft.fill = GridBagConstraints.NONE;

    GridBagConstraints midRight = new GridBagConstraints();
    midRight.anchor = GridBagConstraints.PAGE_END;
    midRight.gridx = 2;
    midRight.weightx = 0.5D;
    midRight.weighty = 0.5D;
    midRight.fill = GridBagConstraints.NONE;

    GridBagConstraints right = new GridBagConstraints();
    right.anchor = GridBagConstraints.PAGE_END;
    right.gridx = 3;
    right.weightx = 0.5D;
    right.weighty = 0.5D;
    right.fill = GridBagConstraints.NONE;

    JButton viewAccountPageButton = new JButton("Accounts");
    viewAccountPageButton.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayAccountPage();
        }
    });
    navBar.add(viewAccountPageButton, left);

    JButton issuePageButton = new JButton("Issues");
    issuePageButton.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayIssuePage();
        }
    });
    navBar.add(issuePageButton, midLeft);

    JButton viewTransactionsButton = new JButton("View Transactions");
    viewTransactionsButton.addActionListener(new java.awt.event.ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            displayTransactionsPage();
        }
    });
    navBar.add(viewTransactionsButton, midRight);

    JButton logoutButton = new JButton("Logout");
    logoutButton.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            KashMoneyAdminPortal.this.server.logout(account);
            WindowEvent closingEvent = new WindowEvent(KashMoneyAdminPortal.this, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);
        }
    });
    navBar.add(logoutButton, right);

    add(BorderLayout.SOUTH, navBar);
}

    // Constructor
    public KashMoneyAdminPortal(KashMoneyServer server, KashMoneyAdminAccount account){
        this.server = server;
        this.account = account;

        // Window options
        setTitle("KashMoneyAdminPortal");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // TODO: Implement WindowListener to log us out on close.

        // Default page
        displayAccountPage();
    }
}