import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GoalsPage extends JFrame {
    private final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private final Color SECONDARY_COLOR = new Color(240, 248, 255);
    private final Color ACCENT_COLOR = new Color(30, 144, 255);
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);

    private JTextArea shortTermGoalsArea;
    private JTextArea longTermGoalsArea;
    private List<GoalItem> goalsList;
    private JPanel goalsListPanel;

    public GoalsPage() {
        setTitle("Health & Fitness Tracker - Goals");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 500));

        goalsList = new ArrayList<>();

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

        JLabel logoLabel = new JLabel("🎯");
        logoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Fitness Goals");
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

        // Goals input section
        JPanel goalsInputPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        goalsInputPanel.setBackground(SECONDARY_COLOR);

        // Short-term goals
        JPanel shortTermPanel = createGoalPanel("Short-Term Goals", "What do you want to achieve in the next 30 days?");
        shortTermGoalsArea = (JTextArea) shortTermPanel.getComponent(1);

        // Long-term goals
        JPanel longTermPanel = createGoalPanel("Long-Term Goals", "What do you want to achieve in the next 6 months?");
        longTermGoalsArea = (JTextArea) longTermPanel.getComponent(1);

        goalsInputPanel.add(shortTermPanel);
        goalsInputPanel.add(longTermPanel);

        // Goals list section
        goalsListPanel = new JPanel();
        goalsListPanel.setLayout(new BoxLayout(goalsListPanel, BoxLayout.Y_AXIS));
        goalsListPanel.setBackground(SECONDARY_COLOR);
        goalsListPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR),
                "Current Goals",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                MAIN_FONT,
                PRIMARY_COLOR
        ));

        // Add goal button
        JButton addGoalButton = createStyledButton("Add New Goal");
        addGoalButton.addActionListener(e -> showAddGoalDialog());

        // Combine all sections
        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setBackground(SECONDARY_COLOR);
        centerPanel.add(goalsInputPanel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(goalsListPanel), BorderLayout.CENTER);
        centerPanel.add(addGoalButton, BorderLayout.SOUTH);

        contentPanel.add(centerPanel, BorderLayout.CENTER);

        return contentPanel;
    }

    private JPanel createGoalPanel(String title, String placeholder) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(SECONDARY_COLOR);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                MAIN_FONT,
                PRIMARY_COLOR
        ));

        JTextArea textArea = new JTextArea(5, 20);
        textArea.setFont(MAIN_FONT);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(placeholder);
        textArea.setForeground(Color.GRAY);

        textArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textArea.getText().equals(placeholder)) {
                    textArea.setText("");
                    textArea.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textArea.getText().isEmpty()) {
                    textArea.setText(placeholder);
                    textArea.setForeground(Color.GRAY);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        footerPanel.setBackground(SECONDARY_COLOR);

        JButton saveButton = createStyledButton("Save Goals");
        saveButton.addActionListener(e -> saveGoals());

        JButton backButton = createStyledButton("Back to Main");
        backButton.setBackground(SECONDARY_COLOR);
        backButton.setForeground(PRIMARY_COLOR);
        backButton.addActionListener(e -> {
            new MainPage();
            dispose();
        });

        footerPanel.add(saveButton);
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

    private void showAddGoalDialog() {
        JDialog dialog = new JDialog(this, "Add New Goal", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(SECONDARY_COLOR);

        // Goal title
        JTextField titleField = new JTextField(20);
        styleTextField(titleField, "Goal Title");

        // Goal description
        JTextArea descriptionArea = new JTextArea(3, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        styleTextArea(descriptionArea, "Goal Description");

        // Target date
        JTextField targetDateField = new JTextField(20);
        styleTextField(targetDateField, "Target Date (YYYY-MM-DD)");

        // Priority dropdown
        String[] priorities = {"High", "Medium", "Low"};
        JComboBox<String> priorityCombo = new JComboBox<>(priorities);
        priorityCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Add buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(SECONDARY_COLOR);

        JButton addButton = createStyledButton("Add Goal");
        JButton cancelButton = createStyledButton("Cancel");
        cancelButton.setBackground(SECONDARY_COLOR);
        cancelButton.setForeground(PRIMARY_COLOR);

        addButton.addActionListener(e -> {
            if (validateGoalInput(titleField.getText(), descriptionArea.getText(), targetDateField.getText())) {
                addGoal(titleField.getText(), descriptionArea.getText(),
                        targetDateField.getText(), (String)priorityCombo.getSelectedItem());
                dialog.dispose();
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        // Add components to panel
        panel.add(new JLabel("Goal Title:"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(titleField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Description:"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(new JScrollPane(descriptionArea));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Target Date:"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(targetDateField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Priority:"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(priorityCombo);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void styleTextField(JTextField field, String placeholder) {
        field.setFont(MAIN_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void styleTextArea(JTextArea area, String placeholder) {
        area.setFont(MAIN_FONT);
        area.setText(placeholder);
        area.setForeground(Color.GRAY);

        area.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (area.getText().equals(placeholder)) {
                    area.setText("");
                    area.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (area.getText().isEmpty()) {
                    area.setText(placeholder);
                    area.setForeground(Color.GRAY);
                }
            }
        });
    }

    private boolean validateGoalInput(String title, String description, String targetDate) {
        if (title.isEmpty() || description.isEmpty() || targetDate.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            LocalDate.parse(targetDate);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid date in YYYY-MM-DD format.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void addGoal(String title, String description, String targetDate, String priority) {
        GoalItem goal = new GoalItem(title, description, targetDate, priority);
        goalsList.add(goal);
        updateGoalsList();
    }

    private void updateGoalsList() {
        goalsListPanel.removeAll();
        for (GoalItem goal : goalsList) {
            goalsListPanel.add(createGoalItemPanel(goal));
            goalsListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        goalsListPanel.revalidate();
        goalsListPanel.repaint();
    }

    private JPanel createGoalItemPanel(GoalItem goal) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Goal information
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(new JLabel("Title: " + goal.getTitle()));
        infoPanel.add(new JLabel("Due: " + goal.getTargetDate()));
        infoPanel.add(new JLabel("Priority: " + goal.getPriority()));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        // Edit button
        JButton editButton = new JButton("✏️");
        styleIconButton(editButton);
        editButton.addActionListener(e -> editGoal(goal));

        // Delete button
        JButton deleteButton = new JButton("🗑️");
        styleIconButton(deleteButton);
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this goal?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                goalsList.remove(goal);
                updateGoalsList();
            }
        });

        // Complete button
        JCheckBox completeBox = new JCheckBox("Complete");
        completeBox.setBackground(Color.WHITE);
        completeBox.setFont(MAIN_FONT);
        completeBox.addActionListener(e -> {
            goal.setCompleted(completeBox.isSelected());
            updateGoalsList();
        });

        buttonPanel.add(completeBox);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private void styleIconButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(SECONDARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
            }
        });
    }

    private void editGoal(GoalItem goal) {
        JDialog dialog = new JDialog(this, "Edit Goal", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(SECONDARY_COLOR);

        // Goal title
        JTextField titleField = new JTextField(goal.getTitle(), 20);
        styleTextField(titleField, "Goal Title");

        // Goal description
        JTextArea descriptionArea = new JTextArea(goal.getDescription(), 3, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        styleTextArea(descriptionArea, "Goal Description");

        // Target date
        JTextField targetDateField = new JTextField(goal.getTargetDate(), 20);
        styleTextField(targetDateField, "Target Date (YYYY-MM-DD)");

        // Priority dropdown
        String[] priorities = {"High", "Medium", "Low"};
        JComboBox<String> priorityCombo = new JComboBox<>(priorities);
        priorityCombo.setSelectedItem(goal.getPriority());
        priorityCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(SECONDARY_COLOR);

        JButton saveButton = createStyledButton("Save Changes");
        JButton cancelButton = createStyledButton("Cancel");
        cancelButton.setBackground(SECONDARY_COLOR);
        cancelButton.setForeground(PRIMARY_COLOR);

        saveButton.addActionListener(e -> {
            if (validateGoalInput(titleField.getText(), descriptionArea.getText(), targetDateField.getText())) {
                goal.setTitle(titleField.getText());
                goal.setDescription(descriptionArea.getText());
                goal.setTargetDate(targetDateField.getText());
                goal.setPriority((String)priorityCombo.getSelectedItem());
                updateGoalsList();
                dialog.dispose();
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        // Add components to panel
        panel.add(new JLabel("Goal Title:"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(titleField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Description:"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(new JScrollPane(descriptionArea));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Target Date:"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(targetDateField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Priority:"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(priorityCombo);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void saveGoals() {
        // Here you would implement the database saving logic
        // For now, we'll just show a success message
        JOptionPane.showMessageDialog(
                this,
                "Goals saved successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Inner class to represent a goal item
    private class GoalItem {
        private String title;
        private String description;
        private String targetDate;
        private String priority;
        private boolean completed;

        public GoalItem(String title, String description, String targetDate, String priority) {
            this.title = title;
            this.description = description;
            this.targetDate = targetDate;
            this.priority = priority;
            this.completed = false;
        }

        // Getters and setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getTargetDate() { return targetDate; }
        public void setTargetDate(String targetDate) { this.targetDate = targetDate; }
        public String getPriority() { return priority; }
        public void setPriority(String priority) { this.priority = priority; }
        public boolean isCompleted() { return completed; }
        public void setCompleted(boolean completed) { this.completed = completed; }
    }
}


