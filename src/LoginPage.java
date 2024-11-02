import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;
import java.awt.event.MouseAdapter;

public class LoginPage extends JFrame {
    private final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel Blue
    private final Color SECONDARY_COLOR = new Color(240, 248, 255); // Alice Blue
    private final Color ACCENT_COLOR = new Color(30, 144, 255); // Dodge Blue
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);

    public LoginPage() {
        setTitle("VitalFit - Login");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main Panel with Background Color
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(SECONDARY_COLOR);

        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Login Form Panel
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Logo/Icon (you can replace this with an actual image)
        JLabel logoLabel = new JLabel("🏃‍♂️");
        logoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Welcome Back!");
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

        // Username field
        JTextField userText = createStyledTextField("Username");

        // Password field
        JPasswordField passwordText = createStyledPasswordField("Password");

        // Remember me checkbox
        JCheckBox rememberMe = new JCheckBox("Remember me");
        rememberMe.setFont(MAIN_FONT);
        rememberMe.setBackground(SECONDARY_COLOR);
        rememberMe.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Login button
        JButton loginButton = createStyledButton("Login");
        loginButton.addActionListener(e -> {
            if (validateLogin(userText.getText(), new String(passwordText.getPassword()))) {
                new MainPage();
                dispose();
            }
        });

        // Register button
        JButton registerButton = createStyledButton("Create New Account");
        registerButton.setBackground(SECONDARY_COLOR);
        registerButton.setForeground(PRIMARY_COLOR);
        registerButton.addActionListener(e -> {
            new RegisterPage();
            dispose();
        });

        // Forgot password link
        JLabel forgotPassword = new JLabel("Forgot Password?");
        forgotPassword.setFont(MAIN_FONT);
        forgotPassword.setForeground(ACCENT_COLOR);
        forgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPassword.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to panel
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(userText);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(passwordText);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(rememberMe);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(loginButton);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(registerButton);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(forgotPassword);

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

        // Add placeholder text
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

        // Add placeholder text
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
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
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

    private boolean validateLogin(String username, String password) {
        // Basic validation
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            showError("Please enter both username and password.");
            return false;
        }

        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);  // In a real application, you should compare hashed passwords

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // User found
                showSuccess("Login successful!");
                rs.close();
                pstmt.close();
                conn.close();
                return true;
            } else {
                // User not found
                showError("Invalid username or password.");
                rs.close();
                pstmt.close();
                conn.close();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database error: " + e.getMessage());
            return false;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Main method for testing
    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Custom styling for option panes
            UIManager.put("OptionPane.background", new Color(240, 248, 255));
            UIManager.put("Panel.background", new Color(240, 248, 255));
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new LoginPage();
        });
    }
}

