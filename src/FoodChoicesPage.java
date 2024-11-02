import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodChoicesPage extends JFrame {
    private final Color PRIMARY_COLOR = new Color(46, 139, 87); // Forest Green
    private final Color SECONDARY_COLOR = new Color(240, 255, 240); // Honeydew
    private final Color ACCENT_COLOR = new Color(60, 179, 113); // Medium Sea Green
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);

    private JPanel foodListPanel;
    private List<FoodItem> foodItems;
    private JTextField searchField;
    private JComboBox<String> categoryFilter;
    private JSlider calorieSlider;

    public FoodChoicesPage() {
        setTitle("Health & Fitness Tracker - Food Choices");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(700, 500));

        foodItems = new ArrayList<>();

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(SECONDARY_COLOR);

        // Add components
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);
        mainPanel.add(createFooterPanel(), BorderLayout.SOUTH);

        loadFoodItems();
        add(mainPanel);
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel logoLabel = new JLabel("🥗");
        logoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Food Choices");
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

        // Search and Filter Panel
        JPanel searchPanel = createSearchPanel();

        // Food List Panel
        foodListPanel = new JPanel();
        foodListPanel.setLayout(new BoxLayout(foodListPanel, BoxLayout.Y_AXIS));
        foodListPanel.setBackground(SECONDARY_COLOR);

        JScrollPane scrollPane = new JScrollPane(foodListPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Add Panels to Content
        contentPanel.add(searchPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        return contentPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(SECONDARY_COLOR);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Search field
        searchField = new JTextField(20);
        styleTextField(searchField, "Search foods...");
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterFoodItems();
            }
        });

        // Category filter
        String[] categories = {"All Categories", "Breakfast", "Lunch", "Dinner", "Snacks"};
        categoryFilter = new JComboBox<>(categories);
        categoryFilter.setFont(MAIN_FONT);
        categoryFilter.addActionListener(e -> filterFoodItems());

        // Calorie slider
        calorieSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 1000);
        calorieSlider.setBackground(SECONDARY_COLOR);
        calorieSlider.setMajorTickSpacing(200);
        calorieSlider.setMinorTickSpacing(50);
        calorieSlider.setPaintTicks(true);
        calorieSlider.setPaintLabels(true);
        calorieSlider.addChangeListener(e -> filterFoodItems());

        // Add components with GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        searchPanel.add(new JLabel("Search:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        searchPanel.add(searchField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        searchPanel.add(new JLabel("Category:"), gbc);

        gbc.gridx = 3;
        searchPanel.add(categoryFilter, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        searchPanel.add(new JLabel("Max Calories:"), gbc);

        gbc.gridy = 2;
        searchPanel.add(calorieSlider, gbc);

        return searchPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        footerPanel.setBackground(SECONDARY_COLOR);

        JButton addFoodButton = createStyledButton("Add New Food");
        addFoodButton.addActionListener(e -> showAddFoodDialog());

        JButton backButton = createStyledButton("Back to Main");
        backButton.setBackground(SECONDARY_COLOR);
        backButton.setForeground(PRIMARY_COLOR);
        backButton.addActionListener(e -> {
            new MainPage();
            dispose();
        });

        footerPanel.add(addFoodButton);
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

    private void styleTextField(JTextField field, String placeholder) {
        field.setFont(MAIN_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

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

    private void loadFoodItems() {
        foodItems.clear();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM food_choices")) {

            while (resultSet.next()) {
                FoodItem item = new FoodItem(
                        resultSet.getInt("id"),
                        resultSet.getString("food_name"),
                        resultSet.getInt("calories"),
                        resultSet.getString("category"),
                        resultSet.getString("description"),
                        resultSet.getDouble("protein"),
                        resultSet.getDouble("carbs"),
                        resultSet.getDouble("fats")
                );
                foodItems.add(item);
            }
            updateFoodList();
        } catch (Exception e) {
            showError("Database Error", "Could not load food items: " + e.getMessage());
        }
    }

    private void updateFoodList() {
        foodListPanel.removeAll();
        for (FoodItem item : foodItems) {
            foodListPanel.add(createFoodItemPanel(item));
            foodListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        foodListPanel.revalidate();
        foodListPanel.repaint();
    }

    private JPanel createFoodItemPanel(FoodItem item) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Left side - Basic info
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        infoPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(item.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        infoPanel.add(nameLabel);
        infoPanel.add(new JLabel("Calories: " + item.getCalories()));
        infoPanel.add(new JLabel("Category: " + item.getCategory()));

        // Right side - Nutritional info
        JPanel nutritionPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        nutritionPanel.setBackground(Color.WHITE);
        nutritionPanel.add(new JLabel(String.format("Protein: %.1fg", item.getProtein())));
        nutritionPanel.add(new JLabel(String.format("Carbs: %.1fg", item.getCarbs())));
        nutritionPanel.add(new JLabel(String.format("Fats: %.1fg", item.getFats())));

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton editButton = new JButton("✏️");
        styleIconButton(editButton);
        editButton.addActionListener(e -> showEditFoodDialog(item));

        JButton deleteButton = new JButton("🗑️");
        styleIconButton(deleteButton);
        deleteButton.addActionListener(e -> deleteFoodItem(item));

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        panel.add(infoPanel, BorderLayout.WEST);
        panel.add(nutritionPanel, BorderLayout.CENTER);
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

    private void filterFoodItems() {
        String searchText = searchField.getText().toLowerCase();
        String category = (String) categoryFilter.getSelectedItem();
        int maxCalories = calorieSlider.getValue();

        foodListPanel.removeAll();

        for (FoodItem item : foodItems) {
            if (item.matches(searchText, category, maxCalories)) {
                foodListPanel.add(createFoodItemPanel(item));
                foodListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        foodListPanel.revalidate();
        foodListPanel.repaint();
    }

    private void showAddFoodDialog() {
        JDialog dialog = new JDialog(this, "Add New Food", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);

        JPanel panel = createFoodFormPanel(null, dialog);
        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showEditFoodDialog(FoodItem item) {
        JDialog dialog = new JDialog(this, "Edit Food", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);

        JPanel panel = createFoodFormPanel(item, dialog);
        dialog.add(panel);
        dialog.setVisible(true);
    }

    private JPanel createFoodFormPanel(FoodItem item, JDialog dialog) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(SECONDARY_COLOR);



        JTextField nameField = new JTextField(item != null ? item.getName() : "", 20);
        JTextField caloriesField = new JTextField(item != null ? String.valueOf(item.getCalories()) : "", 20);
        JComboBox<String> categoryCombo = new JComboBox<>(new String[]{"Breakfast", "Lunch", "Dinner", "Snacks"});
        JTextField proteinField = new JTextField(item != null ? String.valueOf(item.getProtein()) : "", 20);
        JTextField carbsField = new JTextField(item != null ? String.valueOf(item.getCarbs()) : "", 20);
        JTextField fatsField = new JTextField(item != null ? String.valueOf(item.getFats()) : "", 20);
        JTextArea descriptionArea = new JTextArea(item != null ? item.getDescription() : "", 3, 20);

        // Style the form fields
        Arrays.asList(nameField, caloriesField, proteinField, carbsField, fatsField)
                .forEach(field -> {
                    field.setFont(MAIN_FONT);
                    field.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(PRIMARY_COLOR),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)
                    ));
                });

        // Style the description area
        descriptionArea.setFont(MAIN_FONT);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
        descriptionScroll.setPreferredSize(new Dimension(250, 60));

        // Style the category combo box
        categoryCombo.setFont(MAIN_FONT);
        categoryCombo.setBackground(Color.WHITE);
        categoryCombo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));


        // Set the selected category if editing
        if (item != null) {
            categoryCombo.setSelectedItem(item.getCategory());
        }

        // Buttons
        JButton saveButton = new JButton(item != null ? "Save Changes" : "Add Food");
        saveButton.setBackground(PRIMARY_COLOR);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        saveButton.addActionListener(e -> {
            if (validateFoodInput(nameField, caloriesField, proteinField, carbsField, fatsField)) {
                if (item == null) {
                    addNewFood(nameField, caloriesField, categoryCombo, proteinField, carbsField, fatsField);
                } else {
                    updateFood(item, nameField, caloriesField, categoryCombo, proteinField, carbsField, fatsField);
                }
                dialog.dispose();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(SECONDARY_COLOR);
        cancelButton.setForeground(PRIMARY_COLOR);
        cancelButton.addActionListener(e -> dialog.dispose());

        // Add components to panel
        panel.add(new JLabel("Food Name:"));
        panel.add(nameField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Calories:"));
        panel.add(caloriesField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Category:"));
        panel.add(categoryCombo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Protein (g):"));
        panel.add(proteinField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Carbs (g):"));
        panel.add(carbsField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Fats (g):"));
        panel.add(fatsField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(saveButton);
        panel.add(cancelButton);

        return panel;
    }

    private boolean validateFoodInput(JTextField nameField, JTextField caloriesField, JTextField proteinField, JTextField carbsField, JTextField fatsField) {
        // Basic validation for empty fields and numeric values
        if (nameField.getText().trim().isEmpty()) {
            showError("Input Error", "Food name cannot be empty.");
            return false;
        }
        try {
            Integer.parseInt(caloriesField.getText());
            Double.parseDouble(proteinField.getText());
            Double.parseDouble(carbsField.getText());
            Double.parseDouble(fatsField.getText());
        } catch (NumberFormatException e) {
            showError("Input Error", "Please enter valid numeric values for calories and nutrients.");
            return false;
        }
        return true;
    }

    private void addNewFood(JTextField nameField, JTextField caloriesField, JComboBox<String> categoryCombo, JTextField proteinField, JTextField carbsField, JTextField fatsField) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO food_choices (food_name, calories, category, protein, carbs, fats) VALUES (?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, nameField.getText());
            statement.setInt(2, Integer.parseInt(caloriesField.getText()));
            statement.setString(3, (String) categoryCombo.getSelectedItem());
            statement.setDouble(4, Double.parseDouble(proteinField.getText()));
            statement.setDouble(5, Double.parseDouble(carbsField.getText()));
            statement.setDouble(6, Double.parseDouble(fatsField.getText()));
            statement.executeUpdate();

            loadFoodItems(); // Refresh the list
            JOptionPane.showMessageDialog(this, "Food item added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            showError("Database Error", "Could not add food item: " + e.getMessage());
        }
    }

    private void updateFood(FoodItem item, JTextField nameField, JTextField caloriesField, JComboBox<String> categoryCombo, JTextField proteinField, JTextField carbsField, JTextField fatsField) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE food_choices SET food_name = ?, calories = ?, category = ?, protein = ?, carbs = ?, fats = ? WHERE id = ?")) {

            statement.setString(1, nameField.getText());
            statement.setInt(2, Integer.parseInt(caloriesField.getText()));
            statement.setString(3, (String) categoryCombo.getSelectedItem());
            statement.setDouble(4, Double.parseDouble(proteinField.getText()));
            statement.setDouble(5, Double.parseDouble(carbsField.getText()));
            statement.setDouble(6, Double.parseDouble(fatsField.getText()));
            statement.setInt(7, item.getId());
            statement.executeUpdate();

            loadFoodItems(); // Refresh the list
            JOptionPane.showMessageDialog(this, "Food item updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            showError("Database Error", "Could not update food item: " + e.getMessage());
        }
    }

    private void deleteFoodItem(FoodItem item) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete " + item.getName() + "?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM food_choices WHERE id = ?")) {

                statement.setInt(1, item.getId());
                statement.executeUpdate();
                loadFoodItems(); // Refresh the list
                JOptionPane.showMessageDialog(this, "Food item deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                showError("Database Error", "Could not delete food item: " + e.getMessage());
            }
        }
    }

    private void showError(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    // Inner class to represent a food item
    private static class FoodItem {
        private final int id;
        private final String name;
        private final int calories;
        private final String category;
        private final String description;
        private final double protein;
        private final double carbs;
        private final double fats;

        public FoodItem(int id, String name, int calories, String category, String description, double protein, double carbs, double fats) {
            this.id = id;
            this.name = name;
            this.calories = calories;
            this.category = category;
            this.description = description;
            this.protein = protein;
            this.carbs = carbs;
            this.fats = fats;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public int getCalories() { return calories; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
        public double getProtein() { return protein; }
        public double getCarbs() { return carbs; }
        public double getFats() { return fats; }

        public boolean matches(String searchText, String category, int maxCalories) {
            boolean matchesSearch = searchText.isEmpty() ||
                    name.toLowerCase().contains(searchText) ||
                    description.toLowerCase().contains(searchText);
            boolean matchesCategory = category.equals("All Categories") ||
                    this.category.equals(category);
            boolean matchesCalories = calories <= maxCalories;

            return matchesSearch && matchesCategory && matchesCalories;
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new FoodChoicesPage();
        });
    }
}







