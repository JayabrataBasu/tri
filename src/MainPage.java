import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainPage extends JFrame {
    private JLabel dateTimeLabel;
    private JLabel welcomeLabel;
    private Timer timer;

    public MainPage() {
        setTitle("Health & Fitness Tracker - Main Page");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main container with BorderLayout
        Container mainContainer = getContentPane();
        mainContainer.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        mainContainer.add(headerPanel, BorderLayout.NORTH);

        // Center Panel with Buttons
        JPanel centerPanel = createCenterPanel();
        mainContainer.add(centerPanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = createFooterPanel();
        mainContainer.add(footerPanel, BorderLayout.SOUTH);

        // Start the timer to update date/time
        startTimer();

        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.setBackground(new Color(230, 230, 250));

        welcomeLabel = new JLabel("Welcome to Health & Fitness Tracker!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        dateTimeLabel = new JLabel();
        dateTimeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(welcomeLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(dateTimeLabel);

        return headerPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        centerPanel.setBackground(new Color(240, 240, 245));

        // Create buttons with icons (you'll need to add actual icons)
        JButton foodChoicesButton = createStyledButton("Food Choices", "Track your meals and nutrition");
        JButton goalsButton = createStyledButton("Goals", "Set and monitor your fitness goals");
        JButton graphsButton = createStyledButton("Graphs", "View your progress charts");
        JButton exercisesButton = createStyledButton("Exercises", "Discover workout suggestions");
        JButton settingsButton = createStyledButton("Settings", "Adjust your preferences");
        JButton profileButton = createStyledButton("Profile", "View and edit your profile");

        // Add buttons to panel with GridBagLayout
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0; centerPanel.add(foodChoicesButton, gbc);
        gbc.gridx = 1; gbc.gridy = 0; centerPanel.add(goalsButton, gbc);
        gbc.gridx = 0; gbc.gridy = 1; centerPanel.add(graphsButton, gbc);
        gbc.gridx = 1; gbc.gridy = 1; centerPanel.add(exercisesButton, gbc); 
        gbc.gridx = 0; gbc.gridy = 2; centerPanel.add(settingsButton, gbc);
        gbc.gridx = 1; gbc.gridy = 2; centerPanel.add(profileButton, gbc);

        // Add button listeners
        addButtonListeners(foodChoicesButton, goalsButton, graphsButton, exercisesButton, settingsButton, profileButton);

        return centerPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(230, 230, 250));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel footerLabel = new JLabel("© 2023 Health & Fitness Tracker");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerPanel.add(footerLabel);

        return footerPanel;
    }

    private JButton createStyledButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 80));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setToolTipText(tooltip);
        button.setFocusPainted(false);
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createRaisedBevelBorder());

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(65, 105, 225));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }
        });

        return button;
    }

    private void addButtonListeners(JButton... buttons) {
        for (JButton button : buttons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (button.getText()) {
                        case "Food Choices":
                            new FoodChoicesPage();
                            break;
                        case "Goals":
                            new GoalsPage();
                            break;
                        case "Graphs":
                            new GraphsPage();
                            break;
                        case "Exercises":
                            new ExercisesPage();
                            break;
                        case "Settings":
                            JOptionPane.showMessageDialog(MainPage.this,
                                    "Settings page coming soon!",
                                    "Settings",
                                    JOptionPane.INFORMATION_MESSAGE);
                            return;
                        case "Profile":
                            JOptionPane.showMessageDialog(MainPage.this,
                                    "Profile page coming soon!",
                                    "Profile",
                                    JOptionPane.INFORMATION_MESSAGE);
                            return;
                    }
                    dispose();
                }
            });
        }
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDateTime();
            }
        });
        timer.start();
    }

    private void updateDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy HH:mm:ss");
        dateTimeLabel.setText(now.format(formatter));
    }

    // Method to clean up resources
    @Override
    public void dispose() {
        if (timer != null) {
            timer.stop();
        }
        super.dispose();
    }

    // Main method for testing
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainPage();
            }
        });
    }
}