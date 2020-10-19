package GUI;

import commands.Shutdown;

import javax.swing.*;

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
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            new Shutdown().shutdown();
        } );
    }
}
