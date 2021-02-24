import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class KashMoneyDeliveryPortal extends JFrame{
    private KashMoneyServer server;
    private KashMoneyDeliveryAccount account;
    
    // state variables
    private boolean firstRun = true;

    // Core Swing Components
    private JPanel navBar;
    private JLabel statusLabel;
    
    private void displayDeliveryPage() {
        if (!firstRun) {
            getContentPane().removeAll(); // remove all components.
        }

        // Recreate our objects.
        ArrayList<JPanel> panel = new ArrayList<JPanel>();
        statusLabel = new JLabel("");
        int idx = 0;

        // Title
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Deliveries"));
        idx++;

        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Transaction ID"));
        panel.get(idx).add(new JLabel("Customer Address"));
        panel.get(idx).add(new JLabel("Status"));
        idx++;
        
        // Display list of issues
        ArrayList<KashMoneyTransaction> transactions = server.getTransactions(account);
        for (KashMoneyTransaction transaction : transactions) {
            panel.add(new JPanel());
            JTextField transactionIDField = new JTextField(String.valueOf(transaction.getId()), 20);
            transactionIDField.setEditable(false);
            panel.get(idx).add(transactionIDField);
            JTextField customerAddressField = new JTextField(transaction.getCustomer().getMailingAddress(), 20);
            customerAddressField.setEditable(false);
            panel.get(idx).add(customerAddressField);
            JButton manageDeliveryButton = new JButton("Manage Delivery");
            manageDeliveryButton.addActionListener(new java.awt.event.ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    //displayViewDeliveryPage(transaction);
                }
            });
            panel.get(idx).add(manageDeliveryButton);
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

        if (transactions.size() == 0) {
            setSize(400, 400);
            statusLabel.setText("No transactions to display.");
        }
        else {
            pack();
        }
        setLocationRelativeTo(null);
    }

    
    private void displayManageDeliveryPage() {
        if (!firstRun) {
            getContentPane().removeAll(); // remove all components.
        }

        // Recreate our objects.
        ArrayList<JPanel> panel = new ArrayList<JPanel>();
        statusLabel = new JLabel("");
        int idx = 0;

        // Title
        panel.add(new JPanel());
        panel.get(idx).add(new JLabel("Manage Deliveries"));
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
            // Add a button to mark delivery as complete
            JButton manageButton = new JButton("Mark as Complete");
            manageButton.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    JFrame signatureFrame = new JFrame("Delivery Confirmation");
                    JTextField signatureField = new JTextField(20);
                    signatureFrame.add(signatureField);
                    JButton submitButton = new JButton("Submit");
                    submitButton.addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            transaction.setSignature(signatureField.getText());
                            signatureFrame.setVisible(false);
                            signatureFrame.dispose();
                        }
                    });
                    signatureFrame.add(submitButton);

                    signatureFrame.setVisible(true);
                    signatureFrame.pack();

                    transaction.setStatus(KashMoneyTransaction.KashMoneyTransactionStatus.COMPLETE);
                    statusLabel.setText("Delivery marked as completed!");
                }
            });
            panel.get(idx).add(manageButton);
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

        GridBagConstraints center = new GridBagConstraints();
        center.anchor = GridBagConstraints.PAGE_END;
        center.gridx = 0;
        center.weightx = 0.5D;
        center.weighty = 0.5D;
        center.fill = GridBagConstraints.NONE;

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KashMoneyDeliveryPortal.this.server.logout(account);
                WindowEvent closingEvent = new WindowEvent(KashMoneyDeliveryPortal.this, WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);
            }
        });
        navBar.add(logoutButton, center);

        add(BorderLayout.SOUTH, navBar);
    }

    // Constructor
    public KashMoneyDeliveryPortal(KashMoneyServer server, KashMoneyDeliveryAccount account){
        this.server = server;
        this.account = account;

        // Window options
        setTitle("KashMoneyDeliveryPortal");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Default page
        displayDeliveryPage();
    }
}