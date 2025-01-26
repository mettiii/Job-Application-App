//job posting
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Jobpost extends JFrame {
    private final JLabel jobIdLabel;
    private final JLabel titleLabel;
    private final JLabel descriptionLabel;
    private final JLabel requirementsLabel;
    private final JLabel priorityCriteriaLabel;
    private final JLabel salaryRangeLabel;
    private final JTextField jobIdField;
    private final JTextField titleField;
    private final JTextField minSalaryField;
    private final JTextField maxSalaryField;
    private final JTextArea descriptionArea;
    private final JTextArea requirementsArea;
    private final JTextArea priorityCriteriaArea;
    private final JButton postJobButton;
    private final JButton dashboardButton;
    //db connection
    private final DatabaseConnectivity dbconnection;
    private String jobId;
    private String title ;
    private String description;
    private String requirements;
    private String priorityCriteria;
    private double minSalary;
    private double maxSalary;


    public Jobpost() {
        setTitle("Post Job Posting");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        dbconnection = new DatabaseConnectivity();

        // Creating main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Adding Job ID label and field
        jobIdLabel = new JLabel("Enter the Job ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(jobIdLabel, gbc);

        jobIdField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(jobIdField, gbc);

        // Adding Title label and field
        titleLabel = new JLabel("Enter the Title:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(titleLabel, gbc);

        titleField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(titleField, gbc);

        // Adding Description label and area
        descriptionLabel = new JLabel("Enter the Description:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(descriptionLabel, gbc);

        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(descriptionScrollPane, gbc);

        // Adding Requirements label and area
        requirementsLabel = new JLabel("Enter the Requirements:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(requirementsLabel, gbc);

        requirementsArea = new JTextArea(4, 20);
        requirementsArea.setLineWrap(true);
        JScrollPane requirementsScrollPane = new JScrollPane(requirementsArea);
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(requirementsScrollPane, gbc);

        // Adding Priority Criteria label and area
        priorityCriteriaLabel = new JLabel("Enter the Priority Criteria:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(priorityCriteriaLabel, gbc);

        priorityCriteriaArea = new JTextArea(4, 20);
        priorityCriteriaArea.setLineWrap(true);
        JScrollPane priorityCriteriaScrollPane = new JScrollPane(priorityCriteriaArea);
        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(priorityCriteriaScrollPane, gbc);

        // Adding Salary Range label and fields
        salaryRangeLabel = new JLabel("Enter the Salary Range:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(salaryRangeLabel, gbc);

        JPanel salaryRangePanel = new JPanel();
        salaryRangePanel.setLayout(new FlowLayout());

        JLabel minSalaryLabel = new JLabel("Min:");
        salaryRangePanel.add(minSalaryLabel);

        minSalaryField = new JTextField(10);
        salaryRangePanel.add(minSalaryField);

        JLabel maxSalaryLabel = new JLabel("Max:");
        salaryRangePanel.add(maxSalaryLabel);

        maxSalaryField = new JTextField(10);
        Component add = salaryRangePanel.add(maxSalaryField);

        gbc.gridx = 1;
        gbc.gridy = 5;
        mainPanel.add(salaryRangePanel, gbc);

        // Adding Post Job button
        postJobButton = new JButton("Post Job");
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(postJobButton, gbc);

        postJobButton.addActionListener(this::actionPerformed);
        // Adding Dashboard button
        dashboardButton = new JButton("Back");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(dashboardButton, gbc);

        dashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a new window or dialog for the menu
                // For now, just closing the current window
                setVisible(false);
                dispose(); // Release the resources
            }
        });

        // Adding main panel to JFrame
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void postJob() {
         jobId = jobIdField.getText().trim();
         title = titleField.getText().trim();
         description = descriptionArea.getText().trim();
         requirements = requirementsArea.getText().trim();
         priorityCriteria = priorityCriteriaArea.getText().trim();

        try {
             minSalary = Double.parseDouble(minSalaryField.getText().trim());
             maxSalary = Double.parseDouble(maxSalaryField.getText().trim());

            if (jobId.isEmpty() || title.isEmpty() || description.isEmpty() || requirements.isEmpty() || priorityCriteria.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all the required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (minSalary >= maxSalary) {
                JOptionPane.showMessageDialog(this, "Invalid salary range. Minimum salary must be less than maximum salary.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DatabaseConnectivity.saveJobToDatabase(jobId, title, description, requirements, priorityCriteria, minSalary, maxSalary);
            JOptionPane.showMessageDialog(this, "Job posting saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid salary format. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred while saving the job posting: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Jobpost::new);    }

    private void actionPerformed(ActionEvent e) {
        postJob();
    }
}


 