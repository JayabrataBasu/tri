import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class ExercisesPage extends JFrame {
    private final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel Blue
    private final Color SECONDARY_COLOR = new Color(240, 248, 255); // Alice Blue
    private final Color ACCENT_COLOR = new Color(30, 144, 255); // Dodge Blue
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);

    private JPanel exerciseListPanel;
    private Map<String, List<Exercise>> exerciseCategories;
    private JComboBox<String> categoryFilter;

    public ExercisesPage() {
        setTitle("Health & Fitness Tracker - Exercise Suggestions");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 500));

        initializeExerciseData();

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(SECONDARY_COLOR);

        // Add components
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);
        mainPanel.add(createFooterPanel(), BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel logoLabel = new JLabel("💪");
        logoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Exercise Suggestions");
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(logoLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(titleLabel);

        return headerPanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(SECONDARY_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Filter panel
        JPanel filterPanel = createFilterPanel();
        
        // Exercise list panel (scrollable)
        exerciseListPanel = new JPanel();
        exerciseListPanel.setLayout(new BoxLayout(exerciseListPanel, BoxLayout.Y_AXIS));
        exerciseListPanel.setBackground(SECONDARY_COLOR);
        
        JScrollPane scrollPane = new JScrollPane(exerciseListPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Display all exercises initially
        updateExerciseList("All Categories");

        // Add components to content panel
        contentPanel.add(filterPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        return contentPanel;
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(SECONDARY_COLOR);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel filterLabel = new JLabel("Filter by Category:");
        filterLabel.setFont(MAIN_FONT);
        
        // Get categories from our data
        String[] categories = new String[exerciseCategories.size() + 1];
        categories[0] = "All Categories";
        int i = 1;
        for (String category : exerciseCategories.keySet()) {
            categories[i++] = category;
        }
        
        categoryFilter = new JComboBox<>(categories);
        categoryFilter.setFont(MAIN_FONT);
        categoryFilter.addActionListener(e -> {
            String selectedCategory = (String) categoryFilter.getSelectedItem();
            updateExerciseList(selectedCategory);
        });

        filterPanel.add(filterLabel);
        filterPanel.add(categoryFilter);

        return filterPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        footerPanel.setBackground(SECONDARY_COLOR);

        JButton backButton = createStyledButton("Back to Main");
        backButton.addActionListener(e -> {
            new MainPage();
            dispose();
        });

        footerPanel.add(backButton);

        return footerPanel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(MAIN_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
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

    private void initializeExerciseData() {
        exerciseCategories = new HashMap<>();
        
        // Cardio Exercises
        List<Exercise> cardio = new ArrayList<>();
        cardio.add(new Exercise("Running", "Improves cardiovascular health and builds endurance", 
                "Start with 10-15 minutes of jogging and gradually increase. Aim for 150 minutes per week.", 
                "Beginner to Advanced", 300));
        cardio.add(new Exercise("Cycling", "Low-impact cardio that strengthens the lower body", 
                "30-60 minutes of moderate cycling, 3-5 times per week.", 
                "Beginner to Advanced", 250));
        cardio.add(new Exercise("Swimming", "Full-body workout with minimal joint impact", 
                "Start with 20 minutes and build up to 30-45 minutes, 2-3 times per week.", 
                "Beginner to Advanced", 350));
        cardio.add(new Exercise("Jump Rope", "Improves coordination and burns calories quickly", 
                "Begin with 5-10 minutes and work up to 15-20 minutes.", 
                "Beginner to Advanced", 320));
        exerciseCategories.put("Cardio", cardio);
        
        // Strength Training
        List<Exercise> strength = new ArrayList<>();
        strength.add(new Exercise("Push-ups", "Builds upper body and core strength", 
                "Start with 3 sets of 5-10 push-ups, gradually increasing reps as you get stronger.", 
                "Beginner to Advanced", 100));
        strength.add(new Exercise("Squats", "Strengthens legs, glutes, and core", 
                "3 sets of 12-15 reps. Add weights as you progress.", 
                "Beginner to Advanced", 150));
        strength.add(new Exercise("Deadlifts", "Works the entire posterior chain", 
                "3 sets of 8-12 reps with proper form. Start with light weights.", 
                "Intermediate to Advanced", 180));
        strength.add(new Exercise("Dumbbell Rows", "Targets back and biceps", 
                "3 sets of 10-12 reps on each side with appropriate weight.", 
                "Beginner to Advanced", 120));
        exerciseCategories.put("Strength Training", strength);
        
        // Flexibility & Balance
        List<Exercise> flexibility = new ArrayList<>();
        flexibility.add(new Exercise("Yoga", "Improves flexibility, balance, and mental well-being", 
                "15-60 minute sessions, 2-5 times per week. Start with beginner-friendly poses.", 
                "Beginner to Advanced", 180));
        flexibility.add(new Exercise("Pilates", "Focuses on core strength and body alignment", 
                "30-45 minute sessions, 2-3 times per week.", 
                "Beginner to Advanced", 200));
        flexibility.add(new Exercise("Dynamic Stretching", "Prepares the body for exercise", 
                "5-10 minutes before workouts. Include arm circles, hip rotations, and walking lunges.", 
                "Beginner", 80));
        flexibility.add(new Exercise("Static Stretching", "Improves overall flexibility", 
                "Hold each stretch for 15-30 seconds, 2-3 times. Best after workouts.", 
                "Beginner", 60));
        exerciseCategories.put("Flexibility & Balance", flexibility);
        
        // HIIT Workouts
        List<Exercise> hiit = new ArrayList<>();
        hiit.add(new Exercise("Tabata Protocol", "Intense intervals that boost metabolism", 
                "20 seconds of max effort followed by 10 seconds of rest, repeated 8 times (4 minutes total).", 
                "Intermediate to Advanced", 240));
        hiit.add(new Exercise("Circuit Training", "Combines strength and cardio elements", 
                "Perform 6-10 exercises in sequence with minimal rest, then repeat 2-3 times.", 
                "Beginner to Advanced", 300));
        hiit.add(new Exercise("Burpees", "Full-body exercise that builds strength and endurance", 
                "Start with 3 sets of 8-10 reps with rest between sets.", 
                "Intermediate", 200));
        hiit.add(new Exercise("Mountain Climbers", "Works core while elevating heart rate", 
                "30 seconds on, 15 seconds rest. Repeat 4-6 times.", 
                "Beginner to Intermediate", 160));
        exerciseCategories.put("HIIT Workouts", hiit);
    }

    private void updateExerciseList(String category) {
        exerciseListPanel.removeAll();
        
        if (category.equals("All Categories")) {
            // Display all exercises from all categories
            for (String cat : exerciseCategories.keySet()) {
                exerciseListPanel.add(createCategoryHeader(cat));
                for (Exercise exercise : exerciseCategories.get(cat)) {
                    exerciseListPanel.add(createExercisePanel(exercise));
                    exerciseListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                }
            }
        } else {
            // Display exercises from selected category
            exerciseListPanel.add(createCategoryHeader(category));
            for (Exercise exercise : exerciseCategories.get(category)) {
                exerciseListPanel.add(createExercisePanel(exercise));
                exerciseListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        
        exerciseListPanel.revalidate();
        exerciseListPanel.repaint();
    }

    private JPanel createCategoryHeader(String category) {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(200, 220, 240));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel categoryLabel = new JLabel(category);
        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        categoryLabel.setForeground(PRIMARY_COLOR);
        
        headerPanel.add(categoryLabel);
        return headerPanel;
    }

    private JPanel createExercisePanel(Exercise exercise) {
        JPanel panel = new JPanel(new BorderLayout(15, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Left panel with name and benefits
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        
        JLabel nameLabel = new JLabel(exercise.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        JLabel benefitsLabel = new JLabel("<html><b>Benefits:</b> " + exercise.getBenefits() + "</html>");
        benefitsLabel.setFont(MAIN_FONT);
        
        leftPanel.add(nameLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        leftPanel.add(benefitsLabel);
        
        // Right panel with instructions and details
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        
        JLabel instructionsLabel = new JLabel("<html><b>Instructions:</b> " + exercise.getInstructions() + "</html>");
        instructionsLabel.setFont(MAIN_FONT);
        
        JPanel detailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        detailsPanel.setBackground(Color.WHITE);
        
        JLabel levelLabel = new JLabel("Level: " + exercise.getDifficultyLevel());
        levelLabel.setFont(MAIN_FONT);
        
        JLabel caloriesLabel = new JLabel("Est. Calories/30min: " + exercise.getCaloriesBurnedPerHalfHour());
        caloriesLabel.setFont(MAIN_FONT);
        
        detailsPanel.add(levelLabel);
        detailsPanel.add(caloriesLabel);
        
        rightPanel.add(instructionsLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(detailsPanel);
        
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    // Inner class for exercise data
    private class Exercise {
        private String name;
        private String benefits;
        private String instructions;
        private String difficultyLevel;
        private int caloriesBurnedPerHalfHour;
        
        public Exercise(String name, String benefits, String instructions, String difficultyLevel, int caloriesBurnedPerHalfHour) {
            this.name = name;
            this.benefits = benefits;
            this.instructions = instructions;
            this.difficultyLevel = difficultyLevel;
            this.caloriesBurnedPerHalfHour = caloriesBurnedPerHalfHour;
        }
        
        public String getName() { return name; }
        public String getBenefits() { return benefits; }
        public String getInstructions() { return instructions; }
        public String getDifficultyLevel() { return difficultyLevel; }
        public int getCaloriesBurnedPerHalfHour() { return caloriesBurnedPerHalfHour; }
    }
    
    // Main method for testing
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new ExercisesPage());
    }
}
