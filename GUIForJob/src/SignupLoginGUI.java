//login and signup page 





import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupLoginGUI extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signupButton;
    private JButton loginButton;
    private JButton exitButton;
    private JToggleButton toggleButton;
private String username;
private String password;

DatabaseConnectivity db=new DatabaseConnectivity();
    public SignupLoginGUI() {
        setTitle("Welcome to the job application system");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null); // Center the window 

        // Create components 
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(18); // Adjusted the width of the text field 
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(18); // Adjusted the width of the password field 
        signupButton = new JButton("Signup");
        loginButton = new JButton("Login");
        exitButton = new JButton("Exit");
        toggleButton = new JToggleButton("Toggle Background");

        // Set layout to null for absolute positioning 
        setLayout(null);

        // Add components to the frame with absolute positioning 
        int labelX = 50;
        int labelY = 50;
        int fieldX = 150;
        int fieldY = 50;
        int buttonX = 150;
        int buttonY = 150;
        int gap = 30;

        usernameLabel.setBounds(labelX, labelY, 100, 30);
        add(usernameLabel);
        usernameField.setBounds(fieldX, fieldY, 150, 30);
        add(usernameField);

        // Adjust Y position for the "Password" label to create vertical space 
        passwordLabel.setBounds(labelX, labelY + gap + 30, 100, 30); // Add gap + height of username field 
        add(passwordLabel);
        passwordField.setBounds(fieldX, fieldY + gap + 30, 150, 30); // Add gap + height of username field 
        add(passwordField);

        loginButton.setBounds(buttonX, buttonY, 80, 30);
        add(loginButton);

        signupButton.setBounds(100, 250, 80, 30);
        add(signupButton);

        exitButton.setBounds(200, 250, 80, 30);
        add(exitButton);

        toggleButton.setBounds(100, 290, 180, 30);
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (toggleButton.isSelected()) {
                    getContentPane().setBackground(Color.BLUE); // Change background color to blue 
                } else {
                    getContentPane().setBackground(null); // Reset background color 
                }
            }
        });
        add(toggleButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signupButton) {
            signUp();
            // Optional: Close the current window
           // this.dispose();
        } else if (e.getSource() == loginButton) {
            loginButton.addActionListener(e1->login());
            // Open the JobManagementSystemGUI window
            new JobManagementSystemGUI();
            this.dispose();
        }
        else if (e.getSource() == exitButton) {
            // Handle exit button click
            System.exit(0);
        }
    }
    private void login() {
        username = usernameField.getText();
        password = new String(passwordField.getPassword());

        db.checkLogin(username, password);
    }

    private void signUp() {
         username = usernameField.getText();
         password = new String(passwordField.getPassword());

        db.signUp(username, password);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignupLoginGUI::new);
    }
}

