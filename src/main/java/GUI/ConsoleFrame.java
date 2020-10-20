package GUI;

import javax.swing.*;
import java.awt.*;

public class ConsoleFrame extends BaseFrame {
    private JTextField commandField;
    private JTextArea consoleArea;
    private JLabel consoleLabel;
    private JLabel commandLabel;
    private JPanel consolePanel;
    @Override
    void initialize() {
        commandField = new JTextField();
        commandField.setPreferredSize(new Dimension(1280, 70));
        consoleArea = new JTextArea();
        consoleArea.setPreferredSize(new Dimension(1280,640));
        consoleLabel = new JLabel("Console: ");
        commandLabel = new JLabel("Enter the command you want to execute: ");

        consolePanel = new JPanel();
        consolePanel.setPreferredSize(new Dimension(1280, 720));
        consolePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridLayout layout = new GridLayout(0, 1);
        layout.setVgap(20);
        layout.setHgap(10);
        consolePanel.setLayout(layout);
        consolePanel.add(consoleLabel);
        consolePanel.add(consoleArea);
        consolePanel.add(commandLabel);
        consolePanel.add(commandField);

        add(consolePanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Kraut und Rüben Console");
    }
}
