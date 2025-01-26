

//signup window for Employer 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ApplicantHirerGUI extends JFrame {
    private JRadioButton applicantRadioButton;
    private JRadioButton hirerRadioButton;
    private JButton submitButton;

    public ApplicantHirerGUI() {
        setTitle("Applicant or Hirer?");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null); // Center the window 

        // Create components 
        JLabel roleLabel = new JLabel("Select your role:");

        ButtonGroup buttonGroup = new ButtonGroup();

        applicantRadioButton = new JRadioButton("Applicant");
        hirerRadioButton = new JRadioButton("Employer");

        buttonGroup.add(applicantRadioButton);
        buttonGroup.add(hirerRadioButton);

        submitButton = new JButton("Submit");

        // Set layout 
        setLayout(new GridLayout(3, 1));

        // Add components to the frame 
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(roleLabel);
        panel.add(applicantRadioButton);
        panel.add(hirerRadioButton);
        add(panel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitButton);
        add(buttonPanel);

        // Add action listener to the submit button 
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (applicantRadioButton.isSelected()) {
                    new SignupGUI();
                    dispose();
                } else if (hirerRadioButton.isSelected()) {
                     new UserInfoGUI();
                } else {
                    JOptionPane.showMessageDialog(ApplicantHirerGUI.this, "Please select a role.");
                }
            }
        });

        setVisible(true);
    }


    public static void main(String[] args) {
new ApplicantHirerGUI();
    }
}