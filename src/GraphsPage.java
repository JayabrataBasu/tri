import javax.swing.*;
import java.awt.*;

public class GraphsPage extends JFrame {
    public GraphsPage() {
        setTitle("Graphs Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel graphLabel = new JLabel("Graphs will be displayed here...");

        panel.add(graphLabel, BorderLayout.CENTER);
        JButton backButton = new JButton("Back to Main");
        backButton.addActionListener(e -> {
            new MainPage();
            dispose();
        });

        panel.add(backButton, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}