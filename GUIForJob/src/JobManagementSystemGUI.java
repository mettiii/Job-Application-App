//Employer window

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JobManagementSystemGUI extends JFrame implements ActionListener {
    private JButton postJobButton, updateStatusButton, viewJobButton, returnButton;
    private DatabaseConnectivity dbconnection;
    public JobManagementSystemGUI() {
        setTitle("Employer window");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //db connection
        dbconnection=new DatabaseConnectivity();

        // Creating main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding JLabel as title in content pane
        JLabel titleLabel = new JLabel("Employer Window");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(titleLabel, BorderLayout.NORTH);

        // Creating buttons with customized appearance
        postJobButton = createStyledButton("Post Job Posting");
        updateStatusButton = createStyledButton("Update Application Status");
        viewJobButton = createStyledButton("View Job Postings");
        returnButton = createStyledButton("Back to main");

        // Adding ActionListener to buttons
        postJobButton.addActionListener(this);
        updateStatusButton.addActionListener(this);
        viewJobButton.addActionListener(this);
        returnButton.addActionListener(this);

        // Adding buttons to main panel
        gbc.gridy = 0;
        mainPanel.add(postJobButton, gbc);

        gbc.gridy = 1;
        mainPanel.add(updateStatusButton, gbc);

        gbc.gridy = 2;
        mainPanel.add(viewJobButton, gbc);

        gbc.gridy = 3;
        mainPanel.add(returnButton, gbc);

        // Adding main panel to JFrame
        add(mainPanel);
        setVisible(true);
    }

    // Method to create styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.MAGENTA);
        button.setBackground(null); // Set background color to null
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createRaisedBevelBorder()); // Add border
        button.setPreferredSize(new Dimension(200, 40)); // Set preferred size

        // Add mouse listener to change button color on hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(70, 130, 180)); // Change color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(null); // Reset color when mouse exits
            }
        });

        return button;
    }

    public void actionPerformed(ActionEvent e) {
        // Handling button clicks
        if (e.getSource() == postJobButton) {
            Jobpost job = new Jobpost();
            job.setVisible(true);
            dispose();
        } else if (e.getSource() == updateStatusButton) {
            Update update = new Update();
            update.setVisible(true);
            dispose();
        } else if (e.getSource() == viewJobButton) {
            JOptionPane.showMessageDialog(this, "View Job Postings functionality not implemented yet.");
        } else if (e.getSource() == returnButton) {
            JOptionPane.showMessageDialog(this, "Returning to main menu.");
            // Add code to handle returning to main menu
        }
    }

    public static void main(String[] args) {
        // Running GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(JobManagementSystemGUI::new);
    }
}

