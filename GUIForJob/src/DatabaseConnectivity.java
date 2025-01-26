import javax.swing.*;
import java.sql.*;

public class DatabaseConnectivity {


        private static final String URL = "jdbc:mysql://localhost:3306/JobApplication";
        private static final String USERNAME = "root";
        private static final String PASSWORD = "70506496";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

        public static void saveJobToDatabase(/* parameters */) {
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                // Perform database operations here
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    public static void saveJobToDatabase(String jobId, String title, String description, String requirements, String priorityCriteria, double minSalary, double maxSalary) {
        String sql = "INSERT INTO JOB (Job_id, Title, Description, Requirements, Priority_criteria, MinSalary, MaxSalary) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            {
                statement.setString(1, jobId);
                statement.setString(2, title);
                statement.setString(3, description);
                statement.setString(4, requirements);
                statement.setString(5, priorityCriteria);
                statement.setDouble(6, minSalary);
                statement.setDouble(7, maxSalary);
                int rowsAffected = statement.executeUpdate();
                System.out.println("Job posting saved successfully!");
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while saving the job posting.");
        }
    }

    public static void updateApplicationStatus(String applicantId, String newStatus) {
        String sql = "UPDATE JOB SET Application_status = ? WHERE Applicant_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD))
        {
             PreparedStatement statement = connection.prepareStatement(sql); {
            statement.setString(1, newStatus);
            statement.setString(2, applicantId);
            int rowsAffected = statement.executeUpdate();
            System.out.println("Application status updated successfully!");
        }
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while updating the application status.");
        }
    }

    public void viewJobPostings() {
        String sql = "SELECT * FROM JOB";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD))
        {
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery();{
            while (resultSet.next()) {
                String jobId = resultSet.getString("Job_id");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String requirements = resultSet.getString("Requirements");
                String priorityCriteria = resultSet.getString("Priority_criteria");
                double minSalary = resultSet.getDouble("MinSalary");
                double maxSalary = resultSet.getDouble("MaxSalary");

                // Display the job posting details in the GUI
                System.out.println("Job ID: " + jobId);
                System.out.println("Title: " + title);
                System.out.println("Description: " + description);
                System.out.println("Requirements: " + requirements);
                System.out.println("Priority Criteria: " + priorityCriteria);
                System.out.println("Salary Range: " + minSalary + " - " + maxSalary);
                System.out.println();
            }
        } }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertUserDataForApp(String fullName, String password, Date dob, String contactInformation, String levelOfQualification, String areaOfSpecialization, byte[] academicCredential, byte[] photo, byte[] recommendation) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            conn.setAutoCommit(false);

            if (fullName == null || fullName.trim().isEmpty() || password == null || password.trim().isEmpty() || levelOfQualification == null || levelOfQualification.trim().isEmpty() || academicCredential == null || photo == null || recommendation == null) {
                throw new IllegalArgumentException("Invalid input parameters. Please provide valid data.");
            }

            String insertUserAuthQuery = "INSERT INTO User_Authentication (Username, Hashed_password) VALUES (?, ?)";
            try (PreparedStatement userAuthStmt = conn.prepareStatement(insertUserAuthQuery)) {
                userAuthStmt.setString(1, fullName);
                userAuthStmt.setString(2, password);

                int rowsAffected;
                rowsAffected = userAuthStmt.executeUpdate();

                if (rowsAffected >= 1) {
                    int userId = getUserIdFromUsername(conn, fullName);
                    insertIntoApplicant(conn, userId, fullName, dob, contactInformation, levelOfQualification, areaOfSpecialization, academicCredential, photo, recommendation);
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Failed to insert user data. Please try again later.");
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database. Please try again later.");
            throw e;
        }
    }

    private int getUserIdFromUsername(Connection conn, String username) throws SQLException {
        String getUserIdQuery = "SELECT User_id FROM User_Authentication WHERE Username = ?";
        try (PreparedStatement getUserIdStmt = conn.prepareStatement(getUserIdQuery)) {
            getUserIdStmt.setString(1, username);
            try (ResultSet rs = getUserIdStmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("User_id");
                } else {
                    throw new SQLException("User not found in the database.");
                }
            }
        }
    }

    private void insertIntoApplicant(Connection conn, int userId, String fullName, Date dob, String contactInformation, String levelOfQualification, String areaOfSpecialization, byte[] academicCredential, byte[] photo, byte[] recommendation) throws SQLException {
        String insertApplicantQuery = "INSERT INTO Applicant (Applicant_id, FullName, DOb, ContactInformation, LevelOfQualification, AreaOfSpecialization, AcademicCredential, Photo, Recommendation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement applicantStmt = conn.prepareStatement(insertApplicantQuery)) {
            applicantStmt.setInt(1, userId);
            applicantStmt.setString(2, fullName);
            applicantStmt.setDate(3, dob);
            applicantStmt.setString(4, contactInformation);
            applicantStmt.setString(5, levelOfQualification);
            applicantStmt.setString(6, areaOfSpecialization);
            applicantStmt.setBytes(7, academicCredential);
            applicantStmt.setBytes(8, photo);
            applicantStmt.setBytes(9, recommendation);

            int rowsAffected = applicantStmt.executeUpdate();

            if (rowsAffected < 1) {
                throw new SQLException("Failed to insert user data into the Applicant table.");
            }
        }
    }

    private int generateEmployeeId() {
        try (Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD)) {
            // 1. Execute a query to get the maximum numeric value from the 'id' column
            String query = "SELECT MAX(CAST(SUBSTRING(id, 2) AS INT)) FROM users";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            int maxId = 0;
            if (resultSet.next()) {
                // 2. Retrieve the maximum value from the result set
                maxId = resultSet.getInt(1);
            }

            // 3. Increment the maximum value by 1
            return maxId + 1;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");        }
    return -1;
    }

    public void insertUserData(String fullName, String password, String companyName) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            connection.setAutoCommit(false);

            // Validate input parameters
            if (fullName == null || fullName.trim().isEmpty() || password == null || password.trim().isEmpty() || companyName == null || companyName.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid input parameters. Please provide valid data.");
            }

            String insertUserAuthQuery = "INSERT INTO User_Authentication (Username, Hashed_password) VALUES (?, ?)";
            try (PreparedStatement userAuthStmt = connection.prepareStatement(insertUserAuthQuery, Statement.RETURN_GENERATED_KEYS)) {
                userAuthStmt.setString(1, fullName);
                userAuthStmt.setString(2, password);

                int rowsAffected = userAuthStmt.executeUpdate();

                // Retrieve the generated User_id value
                ResultSet generatedKeys = userAuthStmt.getGeneratedKeys();
                int userId = -1;
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                    System.out.println("Generated User_id: " + userId);
                }
if (rowsAffected>=1) {
    insertEmployerData(connection, userId, fullName, companyName);
}
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Failed to insert user data. Please try again later.");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database. Please try again later.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void insertEmployerData(Connection connection, int userId, String fullName, String companyName) throws SQLException {
        String insertEmployerQuery = "INSERT INTO employer (Id, userName, company) VALUES (?, ?, ?)";
        try (PreparedStatement employerStmt = connection.prepareStatement(insertEmployerQuery)) {
            employerStmt.setInt(1, userId);
            employerStmt.setString(2, fullName);
            employerStmt.setString(3, companyName);

            int i = employerStmt.executeUpdate();
        } catch (SQLException e) {
            connection.rollback();
            System.out.println("Failed to insert employer data. Please try again later.");
            e.printStackTrace();
        }
    }

    private void insertApplication(String applicantId, String jobId, String applicationStatus, byte[] coverLetter, byte[] resume) {
        String sql3 = "INSERT INTO Application (Applicant_id, Job_id, Application_status, Cover_letter, Resume) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement appstm=connection.prepareStatement(sql3);
            appstm.setString(1, applicantId);
            appstm.setString(2, jobId);
            appstm.setString(3, applicationStatus);
            appstm.setBytes(4, coverLetter);
            appstm.setBytes(5, resume);
            appstm.executeUpdate();
            System.out.println("Application inserted successfully.");
        } catch (SQLException e) {
            // Handle any SQL-related exceptions
            e.printStackTrace();
        }
    }

    public void checkLogin(String username, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT username, hashed_password FROM user_authentication WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedUsername = resultSet.getString("username");
                String storedPassword = resultSet.getString("hashed_password");

                if (username.equals(storedUsername) && password.equals(storedPassword)) {
                    // Login successful
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    // Open the JobManagementSystemGUI window
                    new JobManagementSystemGUI();
                } else {
                    // Incorrect username or password
                    JOptionPane.showMessageDialog(null, "Incorrect username or password.");
                }
            } else {
                // User not found
                JOptionPane.showMessageDialog(null, "User not found. Please sign up.");
            }
        } catch (SQLException e) {
            // Handle any SQL-related exceptions
            e.printStackTrace();
        }
    }

    public void signUp(String username, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO User_Authentication (username, Hashed_password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password); // Assuming the password is already hashed
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Sign up successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Sign up failed.");
            }
        } catch (SQLException e) {
            // Handle any SQL-related exceptions
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        DatabaseConnectivity db = new DatabaseConnectivity();
        // Test the methods
       // db.saveJobToDatabase("JOB001", "Software Engineer", "Develop and maintain software applications.", "Bachelor's degree in Computer Science or related field.", "Experience, skills, and education.", 60000.0, 90000.0);
      //  db.updateApplicationStatus("APPL001", "Accepted");
       // db.viewJobPostings();
    }
}