package GUI;

import commands.Ingredients;

import javax.swing.*;
import java.awt.*;

public class IngredsFrame extends BaseFrame {
    private JPanel ingredsPanel;
    private JTextArea ingredsArea;
    private JButton ingredsButton;
    private JTextField ingredsField;
    private JComboBox ingredsComboBox;

    @Override
    void initialize() {
        Ingredients ingred = new Ingredients();
        String[] ingredsChoice = String.join("," , ingred.ingredientChoice()).split(",");

        ingredsComboBox = new JComboBox(ingredsChoice);
        ingredsButton = new JButton("Search");
        ingredsArea = new JTextArea();

        ingredsPanel = new JPanel();
        ingredsPanel.setPreferredSize(new Dimension(1280, 720));
        ingredsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridLayout layout = new GridLayout(0,2);
        layout.setVgap(20);
        layout.setHgap(10);
        ingredsPanel.setLayout(layout);
        ingredsPanel.add(ingredsComboBox);


        add(ingredsPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Ingredient Search");
    }
}
