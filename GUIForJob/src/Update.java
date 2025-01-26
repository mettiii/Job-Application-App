import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Update extends JFrame {
    private JLabel titleLabel;
    private JTextField applicationIdField;
    private JTextArea newStatusArea;
    String applicantId;
    String newStatus;

    public Update() {
        setTitle("Update Application Status");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Use JFrame.EXIT_ON_CLOSE instead of EXIT_ON_CLOSE

        // Creating main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Adding title label to the main panel
        titleLabel = new JLabel("Update Application Status", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Adding Application ID label and field
        JLabel applicationIdLabel = new JLabel("Enter the application ID: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(applicationIdLabel, gbc);

        applicationIdField = new JTextField(20); // Set preferred width for the field
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(applicationIdField, gbc);

        // Adding New Status label and area
        JLabel newStatusLabel = new JLabel("Enter the new status: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(newStatusLabel, gbc);

        newStatusArea = new JTextArea(4, 20);
        JScrollPane scrollPane = new JScrollPane(newStatusArea);
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(scrollPane, gbc);

        // Adding gradient background
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();

                // Fill half with brown color
                g2d.setColor(new Color(165, 42, 42));
                g2d.fillRect(0, 0, w / 2, h);

                // Fill half with red color
                g2d.setColor(Color.RED);
                g2d.fillRect(w / 2, 0, w / 2, h);
            }
        };
        gradientPanel.setLayout(new BorderLayout());
        gradientPanel.add(mainPanel, BorderLayout.CENTER);

        // Adding Update button
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> update()); // Call update() method directly
        // Decrease the size of the button
        updateButton.setFont(updateButton.getFont().deriveFont(14.0f));
        gradientPanel.add(updateButton, BorderLayout.SOUTH);

        // Setting background color of the gradient panel to red
        gradientPanel.setBackground(Color.RED);

        // Adding main panel to the content pane
        getContentPane().add(gradientPanel);

        // Set size of the window
        setSize(600, 400);

        // Center the window on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void update() {
        applicantId = applicationIdField.getText().trim();
        newStatus = newStatusArea.getText().trim();
        DatabaseConnectivity.updateApplicationStatus(applicantId, newStatus);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Update::new);
    }
}