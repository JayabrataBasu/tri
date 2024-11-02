import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.regex.Pattern;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLIntegrityConstraintViolationException;

public class RegisterPage extends JFrame {
    private final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel Blue
    private final Color SECONDARY_COLOR = new Color(240, 248, 255); // Alice Blue
    private final Color ACCENT_COLOR = new Color(30, 144, 255); // Dodge Blue
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);

    public RegisterPage() {
        setTitle("Health & Fitness Tracker - Register");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(SECONDARY_COLOR);

        // Add panels
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createFormPanel(), BorderLayout.CENTER);
        mainPanel.add(createFooterPanel(), BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel logoLabel = new JLabel("👥");
        logoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(logoLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(titleLabel);

        return headerPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(SECONDARY_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Create form fields
        JTextField userText = createStyledTextField("Username");
        JTextField emailText = createStyledTextField("Email");
        JPasswordField passwordText = createStyledPasswordField("Password");
        JPasswordField confirmPasswordText = createStyledPasswordField("Confirm Password");

        // Additional fields
        JTextField fullNameText = createStyledTextField("Full Name");
        JTextField ageText = createStyledTextField("Age");

        // Terms and conditions checkbox
        JCheckBox termsCheckBox = new JCheckBox("I agree to the Terms and Conditions");
        termsCheckBox.setFont(MAIN_FONT);
        termsCheckBox.setBackground(SECONDARY_COLOR);
        termsCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Register button
        JButton registerButton = createStyledButton("Create Account");
        registerButton.addActionListener(e -> {
            if (validateRegistration(userText.getText(),
                    emailText.getText(),
                    new String(passwordText.getPassword()),
                    new String(confirmPasswordText.getPassword()),
                    fullNameText.getText(),
                    ageText.getText(),
                    termsCheckBox.isSelected())) {
                new MainPage();
                dispose();
            }
        });

        // Back to login button
        JButton loginButton = createStyledButton("Back to Login");
        loginButton.setBackground(SECONDARY_COLOR);
        loginButton.setForeground(PRIMARY_COLOR);
        loginButton.addActionListener(e -> {
            new LoginPage();
            dispose();
        });

        // Add components to panel
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(fullNameText);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(userText);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(emailText);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(ageText);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(passwordText);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(confirmPasswordText);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(termsCheckBox);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(registerButton);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(loginButton);

        return formPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(SECONDARY_COLOR);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel footerLabel = new JLabel("© 2023 Health & Fitness Tracker");
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footerLabel.setForeground(Color.GRAY);
        footerPanel.add(footerLabel);

        return footerPanel;
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField textField = new JTextField(15);
        textField.setFont(MAIN_FONT);
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        textField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PRIMARY_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Placeholder functionality
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });

        return textField;
    }

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(MAIN_FONT);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PRIMARY_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        passwordField.setEchoChar((char)0);
        passwordField.setText(placeholder);
        passwordField.setForeground(Color.GRAY);

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setEchoChar('•');
                    passwordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setEchoChar((char)0);
                    passwordField.setText(placeholder);
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });

        return passwordField;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(MAIN_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(ACCENT_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    private boolean validateRegistration(String username, String email, String password, String confirmPassword, String fullName, String age, boolean termsAccepted) {
        // Basic validation
        if (username.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty() ||
                confirmPassword.trim().isEmpty() || fullName.trim().isEmpty() || age.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        // Email validation
        if (!Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$").matcher(email).matches()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid email address.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        // Password validation
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        // Age validation
        try {
            int ageNum = Integer.parseInt(age);
            if (ageNum < 13 || ageNum > 120) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid age (13-120).",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid age.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        // Terms and conditions validation
        if (!termsAccepted) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please agree to the Terms and Conditions.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        // Database insertion
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO users (username, password, email, full_name, age) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);  // In a real application, you should hash the password
            pstmt.setString(3, email);
            pstmt.setString(4, fullName);
            pstmt.setInt(5, Integer.parseInt(age));

            int rowsAffected = pstmt.executeUpdate();

            pstmt.close();
            conn.close();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Registration successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return true;
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Registration failed. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return false;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Username or email already exists!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Database error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }



    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new RegisterPage();
        });
    }
}