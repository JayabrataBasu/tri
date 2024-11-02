import javax.swing.*;

public class FitnessApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Start with the login page
            new LoginPage();
        });
    }
}