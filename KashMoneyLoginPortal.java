import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class KashMoneyLoginPortal extends JFrame {
    private KashMoneyServer server;
    private KashMoneyAccount account;

    // state variables
    private boolean firstRun = true;
    private boolean loggedIn = false;

    // Managed objects.
    private KashMoneyUserPortal userPortal;
    private KashMoneyDeliveryPortal deliveryPortal;
    private KashMoneyAdminPortal adminPortal;
    
    // Core Swing Components
    private JTextField username;
    private JPasswordField password;
    private JLabel statusLabel;

    public void setAccount(KashMoneyAccount account) {
        this.account = account;
    }

    // Displays Login Page
    private void displayLoginPage(){
        if (!firstRun) {
            getContentPane().removeAll(); // remove all components.
        }

        // Recreate our objects.
        ArrayList<JPanel> panel = new ArrayList<JPanel>();
        int idx = 0;

        // Title
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Login"));
        idx++;

        // Username field
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Username"));
        username = new JTextField(20);
        panel.get(idx).add(username);
        idx++;

        // Password Field
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Password"));
        password = new JPasswordField(20);
        password.setEchoChar('*');
        panel.get(idx).add(password);
        idx++;

        // Login Button
        panel.add(new JPanel());
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        panel.get(idx).add(loginButton);

        // Register Page Button
        JButton registerPageButton = new JButton("Register Account");
        registerPageButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayRegisterPage();
            }
        });
        panel.get(idx).add(registerPageButton);
        idx++;

        // Label
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

    // Displays Registration Page
    private void displayRegisterPage(){
        if (!loggedIn){
            if (!firstRun) {
                getContentPane().removeAll(); // remove all components.
            }
    
            // Recreate our objects.
            JButton registerButton, loginPageButton;
            ArrayList<JPanel> panel = new ArrayList<JPanel>();
            int idx = 0;
    
            JTextField firstName, lastName, middleInitial, mailingAddress, emailAddress, phoneNumber, paymentInfo;
    
            // Title
            panel.add(new JPanel());
            panel.get(idx).add(new JLabel("Account Registration"));
            idx++;
            
            // Username field
            panel.add(new JPanel());
            panel.get(idx).add(new JLabel("Username"));
            username = new JTextField(20);
            panel.get(idx).add(username);
            idx++;
            
            // Password Field
            panel.add(new JPanel());
            panel.get(idx).add(new JLabel("Password"));
            password = new JPasswordField(20);
            password.setEchoChar('*');
            panel.get(idx).add(password);
            idx++;
    
            // Name
            panel.add(new JPanel());
            panel.get(idx).add(new JLabel("Name"));
            // First name
            firstName = new JTextField(15);
            panel.get(idx).add(firstName);
            // Middle initial
            middleInitial = new JTextField(1);
            panel.get(idx).add(middleInitial);
            // Last name
            lastName = new JTextField(15);
            panel.get(idx).add(lastName);
            idx++;
    
            // mailingAddress
            panel.add(new JPanel());
            panel.get(idx).add(new JLabel("Mailing Address"));
            mailingAddress = new JTextField(20);
            panel.get(idx).add(mailingAddress);
            idx++;
    
            // emailAddress
            panel.add(new JPanel());
            panel.get(idx).add(new JLabel("Email Address"));
            emailAddress = new JTextField(20);
            panel.get(idx).add(emailAddress);
            idx++;
    
            // phoneNumber
            panel.add(new JPanel());
            panel.get(idx).add(new JLabel("Phone Number"));
            phoneNumber = new JTextField(10);
            panel.get(idx).add(phoneNumber);
            idx++;
    
            // paymentInfo
            panel.add(new JPanel());
            panel.get(idx).add(new JLabel("Payment Info"));
            paymentInfo = new JTextField(20);
            panel.get(idx).add(paymentInfo);
            idx++;
    
            // Buttons
            panel.add(new JPanel());
            // Register Account Button
            registerButton = new JButton("Register");
            registerButton.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<String> registrationInfo = new ArrayList<String>();
                    registrationInfo.add(username.getText());
                    registrationInfo.add(new String(password.getPassword()));
                    registrationInfo.add(firstName.getText());
                    registrationInfo.add(middleInitial.getText());
                    registrationInfo.add(lastName.getText());
                    registrationInfo.add(mailingAddress.getText());
                    registrationInfo.add(emailAddress.getText());
                    registrationInfo.add(phoneNumber.getText());
                    registrationInfo.add(paymentInfo.getText());
    
                    server.createUserAccount(registrationInfo);
                }
            });
            panel.get(idx).add(registerButton);
    
            // Login Page Button
            loginPageButton = new JButton("Back to Login");
            // Set Button behavior
            loginPageButton.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayLoginPage();
                }
            });
            panel.get(idx).add(loginPageButton);
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
        }
        pack();
        setLocationRelativeTo(null);
    }

    public void login() {
        String status = server.login(username.getText(), new String(password.getPassword()));
        statusLabel.setText(status);

        if (status.equals("Account logged in successfully!")) {
            username.setEditable(false);
            password.setEditable(false);
            loggedIn = true;
            switch (account.getAccountType()) {
                case USER:
                    userPortal = new KashMoneyUserPortal(server, (KashMoneyUserAccount)account);
                    break;
                case DELIVERY:
                    deliveryPortal = new KashMoneyDeliveryPortal(server, (KashMoneyDeliveryAccount)account);
                    break;
                case ADMIN:
                    adminPortal = new KashMoneyAdminPortal(server, (KashMoneyAdminAccount)account);
                    break;
            }
        }
    }

    public void logout() {
        if (account.getLoginStatus() == false) {
            username.setText("");
            password.setText("");
            username.setEditable(true);
            password.setEditable(true);
            statusLabel.setText("You have been logged out");
            loggedIn = false;
            account = null;
        }
    }

    // Constructor
    public KashMoneyLoginPortal(KashMoneyServer server) {
        this.server = server;

        // Window options
        setTitle("KashMoneyLogin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        //setVisible(true);
        displayLoginPage();
    }
}