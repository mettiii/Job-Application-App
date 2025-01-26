//signup for Employer 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Arrays;

//hirer signup window 
class UserInfoGUI extends JFrame {
    private JTextField fullNameField;
    private JPasswordField passwordField;
    private JTextField companyNameField;
    private JButton submitButton; // Added submit button 
    private DatabaseConnectivity db;

    public UserInfoGUI() {
        db = new DatabaseConnectivity(); // Initialize DatabaseConnectivity object
        setTitle("Hirer registration window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null); // Center the window 

        // Set background color to black 
        getContentPane().setBackground(Color.BLACK);        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setForeground(Color.MAGENTA); // Set label foreground color to white 
        fullNameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.BLUE); // Set label foreground color to white 
        passwordField = new JPasswordField(20);

        JLabel companyNameLabel = new JLabel("Company Name:");
        companyNameLabel.setForeground(Color.CYAN); // Set label foreground color to white 
        companyNameField = new JTextField(20);

        submitButton = new JButton("Submit"); // Added submit button 
        submitButton.setBackground(Color.DARK_GRAY); // Set background color to blue 
        submitButton.setForeground(Color.WHITE); // Set text color to white 

        submitButton.addActionListener(this::actionPerformed);
        // Set layout 
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding 

        // Add components to the frame using GridBagConstraints 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(fullNameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(fullNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(companyNameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(companyNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc); // Added submit button 

        setVisible(true);
    }

    public void user() {
        try {

            String fullName = fullNameField.getText();
            String password = Arrays.toString(passwordField.getPassword());
            String companyName = companyNameField.getText();

            // Validate input parameters
            if (fullName == null || fullName.trim().isEmpty() || password == null || password.trim().isEmpty() || companyName == null || companyName.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid input parameters. Please provide valid data.");
            }

            // Call the insertUserData method from the DatabaseConnectivity class
            DatabaseConnectivity databaseConnectivity = new DatabaseConnectivity();
            databaseConnectivity.insertUserData(fullName, password, companyName);

            // Clear the input fields
            fullNameField.setText("");
            passwordField.setText("");
            companyNameField.setText("");

            // Display a success message
            JOptionPane.showMessageDialog(this, "User data inserted successfully.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UserInfoGUI userInfoGUI = new UserInfoGUI();
    }

    private void actionPerformed(ActionEvent e) {
        user();
    }
}