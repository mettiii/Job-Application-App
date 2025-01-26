//signup window for Applicant 



import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static java.lang.Integer.parseInt;

class SignupGUI extends JFrame implements ActionListener {
    private JTextField nameField;
    private JPasswordField passwordField;
    private JDateChooser ageField;
    private JTextField contactField;
    private JComboBox<String> qualificationComboBox;
    private JTextField specializationField;
    private JTextField credentialsField;
    private JTextField photoPathField;
    private JTextField recommendationPathField;
    private JButton browseButtonCredentials;
    private JButton browseButtonPhoto;
    private JButton browseButtonRecommendation;
    private JButton submitButton;
    private JButton togglePasswordButton;
    private boolean isPasswordVisible = false;

    private JLabel photoLabel;

    private Map<String, String> userData;
    private String fullName;
    private String pass;
    private java.util.Date dob;
    private String contactInformation;
    private byte[] academic;
    private String levelOfQualification;
    private String areaOfSpecialization;
    private byte[] recommendation;
    private byte[] photo;
DatabaseConnectivity db=new DatabaseConnectivity();

    public SignupGUI() {
        setTitle("Registration window for Applicant");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        userData = new HashMap<>();

        getContentPane().setBackground(new Color(255, 244, 100));

        JLabel nameLabel = new JLabel("Enter your full name:");
        nameField = new JTextField(30);
        JLabel passwordLabel = new JLabel("Enter your password:");
        passwordField = new JPasswordField(30);
        togglePasswordButton = new JButton("Show");
        togglePasswordButton.addActionListener(this);

        JLabel ageLabel = new JLabel("Enter your birthdate:");
        ageField = new JDateChooser();
        JLabel contactLabel = new JLabel("Enter your contact information:");
        contactField = new JTextField("+251", 30);
        ((AbstractDocument) contactField.getDocument()).setDocumentFilter(new ContactDocumentFilter());
        JLabel qualificationLabel = new JLabel("Enter your level of qualification:");
        String[] qualifications = {"None", "8th grade", "10th grade", "12th grade", "Diploma", "Degree", "Masters", "PhD"};
        qualificationComboBox = new JComboBox<>(qualifications);
        JLabel specializationLabel = new JLabel("Enter your area of specialization:");
        specializationField = new JTextField(30);
        JLabel credentialsLabel = new JLabel("Enter the path to your academic credentials:");
        credentialsField = new JTextField(20);
        browseButtonCredentials = new JButton("Browse");
        JLabel photoPathLabel = new JLabel("Select your photo:");
        photoPathField = new JTextField(20);
        browseButtonPhoto = new JButton("Browse");
        JLabel recommendationPathLabel = new JLabel("Enter the path to your recommendation letter:");
        recommendationPathField = new JTextField(20);
        browseButtonRecommendation = new JButton("Browse");
        submitButton = new JButton("Submit");
        submitButton.setForeground(Color.DARK_GRAY);
        submitButton.setBackground(Color.GRAY);
        submitButton.setHorizontalAlignment(SwingConstants.CENTER);

        photoLabel = new JLabel();
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setVerticalAlignment(SwingConstants.CENTER);
        photoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        photoLabel.setPreferredSize(new Dimension(150, 150));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(passwordField, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(togglePasswordButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(ageLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(contactLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(contactField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        add(qualificationLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(qualificationComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        add(specializationLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(specializationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        add(credentialsLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(credentialsField, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(browseButtonCredentials, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        add(photoPathLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(photoPathField, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(browseButtonPhoto, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.CENTER;
        add(photoLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.EAST;
        add(recommendationPathLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(recommendationPathField, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(browseButtonRecommendation, gbc);

        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);

        submitButton.addActionListener(this);
        browseButtonCredentials.addActionListener(this);
        browseButtonPhoto.addActionListener(this);
        browseButtonRecommendation.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String name = nameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String age = sdf.format(ageField.getDate());
            String contact = contactField.getText();
            String qualification = (String) qualificationComboBox.getSelectedItem();
            String specialization = specializationField.getText();
            String credentials = credentialsField.getText();
            String photoPath = photoPathField.getText();
            String recommendationPath = recommendationPathField.getText();
            submitButton.addActionListener(this::actionPerformed);
            try {
                signup();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            userData.put("Name", name);
            userData.put("Password", password);
            userData.put("Age", age);
            userData.put("Contact", contact);
            userData.put("Qualification", qualification);
            userData.put("Specialization", specialization);
            userData.put("Credentials", credentials);
            userData.put("Photo Path", photoPath);
            userData.put("Recommendation Path", recommendationPath);

            for (Map.Entry<String, String> entry : userData.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } else {
            if (e.getSource() == browseButtonCredentials) {
                handleCredentialsBrowse();
            } else if (e.getSource() == browseButtonPhoto) {
                handlePhotoBrowse();
            } else if (e.getSource() == browseButtonRecommendation) {
                handleRecommendationBrowse();
            } else if (e.getSource() == togglePasswordButton) {
                togglePasswordVisibility();
            } else if (e.getSource() == submitButton) {
                try {
                    signup();
                } catch (SQLException ex) {
                    showErrorMessage("Error occurred while signing up: " + ex.getMessage());
                } catch (IllegalArgumentException ex) {
                    showErrorMessage("Error: " + ex.getMessage());
                } catch (Exception ex) {
                    showErrorMessage("An unexpected error occurred: " + ex.getMessage());
                }
            }
        }
    }

    private void handleCredentialsBrowse() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            credentialsField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void handlePhotoBrowse() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            photoPathField.setText(selectedFile.getAbsolutePath());
            displayPhoto(selectedFile.getAbsolutePath());
        }
    }

    private void handleRecommendationBrowse() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            recommendationPathField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordField.setEchoChar('*');
            togglePasswordButton.setText("Show");
        } else {
            passwordField.setEchoChar((char) 0);
            togglePasswordButton.setText("Hide");
        }
        isPasswordVisible = !isPasswordVisible;
    }

    private void displayPhoto(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(photoLabel.getWidth(), photoLabel.getHeight(), Image.SCALE_SMOOTH);
        photoLabel.setIcon(new ImageIcon(image));
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }


    private static class ContactDocumentFilter extends DocumentFilter {
        private static final String PREFIX = "+251";

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (offset < PREFIX.length()) {
                return;
            }
            super.insertString(fb, offset, string, attr);
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            if (offset < PREFIX.length()) {
                return;
            }
            super.remove(fb, offset, length);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (offset < PREFIX.length()) {
                return;
            }
            super.replace(fb, offset, length, text, attrs);
        }

    }
    public static byte[] convertPictureToByteArray(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return null;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fis.read(bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void signup() throws SQLException {
        fullName = nameField.getText().trim();
        pass = Arrays.toString(passwordField.getPassword());
        dob = ageField.getDate();

        // Handle the case where the date of birth is not provided
        if (dob == null) throw new IllegalArgumentException("Date of birth is required.");

        java.sql.Date sqlDate = new java.sql.Date(dob.getTime());
        contactInformation = contactField.getText();
        levelOfQualification = (String) qualificationComboBox.getSelectedItem();
        areaOfSpecialization = specializationField.getText();
        academic = convertPictureToByteArray(credentialsField.getText());
        photo = convertPictureToByteArray(photoPathField.getText());
        recommendation = convertPictureToByteArray(recommendationPathField.getText());

        db.insertUserDataForApp(fullName, pass, sqlDate, contactInformation, levelOfQualification, areaOfSpecialization, academic, photo, recommendation);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignupGUI::new);
    }
}
