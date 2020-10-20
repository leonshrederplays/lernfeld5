package GUI;

import commands.Shutdown;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainFrame extends BaseFrame {
    private JPanel mainFramePanel;
    private JLabel welcomeLabel;
    private JButton recipeButton;
    private JButton ordersButton;
    private JButton customersButton;
    private JButton ingredientsButton;
    private JButton consoleButton;
    private JButton dbButton;
    private JButton allergensButton;
    private JButton categoriesButton;
    private JButton exitButton;


    @Override
    void initialize() {
        welcomeLabel = new JLabel("Welcome to Kraut und Rüben!");

        exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(200, 100));
        exitButton.addActionListener(e -> {
            new Shutdown().shutdown();
        });
        consoleButton = new JButton("Open Console");
        consoleButton.setPreferredSize(new Dimension(200, 100));
        consoleButton.addActionListener(e -> {
            new ConsoleFrame().showFrame();
        });
        ingredientsButton = new JButton("Ingredient Search");
        ingredientsButton.setPreferredSize(new Dimension(200,100));
        ingredientsButton.addActionListener(e -> {
            new IngredsFrame().showFrame();
        });

        mainFramePanel = new JPanel();
        mainFramePanel.setPreferredSize(new Dimension(1280, 720));
        mainFramePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridLayout layout = new GridLayout(0, 2);
        layout.setVgap(20);
        layout.setHgap(10);
        mainFramePanel.setLayout(layout);
        mainFramePanel.add(welcomeLabel);
        mainFramePanel.add(new JLabel());
        mainFramePanel.add(new JButton());
        mainFramePanel.add(ingredientsButton);
        mainFramePanel.add(new JButton());
        mainFramePanel.add(new JButton());
        mainFramePanel.add(new JButton());
        mainFramePanel.add(new JButton());
        mainFramePanel.add(new JButton());
        mainFramePanel.add(consoleButton);
        mainFramePanel.add(new JLabel());
        mainFramePanel.add(exitButton);
        add(mainFramePanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Kraut und Rüben");
    }
}
