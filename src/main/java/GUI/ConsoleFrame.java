package GUI;

import commands.*;
import utils.DBUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class ConsoleFrame extends BaseFrame {
    private JTextField commandField;
    private JTextArea consoleArea;
    private JLabel consoleLabel;
    private JLabel commandLabel;
    private JPanel consolePanel;
    private JButton commandButton;
    private JScrollPane scroll;
    @Override
    void initialize() {
        commandField = new JTextField();
        commandField.setPreferredSize(new Dimension(1280, 70));
        consoleArea = new JTextArea();
        consoleArea.setPreferredSize(new Dimension(1280,640));
        consoleArea.setEditable(false);
        consoleLabel = new JLabel("Console: ");
        commandLabel = new JLabel("Enter the command you want to execute: ");
        commandButton = new JButton("Execute");
        commandButton.setPreferredSize(new Dimension(200,100));
        scroll = new JScrollPane(consoleArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        commandButton.addActionListener(ev -> {
                    String[] command;
                        command = commandField.getText().split(" ");
                        switch (command[0]) {
                            case "help" -> {
                                Help help = new Help();
                                help.helper();
                            }
                            case "ingreds" -> {
                                Ingredients ingred = new Ingredients();
                                try {
                                    if (command.length > 1) {
                                        consoleArea.setText(ingred.ingredientDescription(command[1]));
                                    } else {
                                        consoleArea.setText(String.join("\n", ingred.ingredient()));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            case "recipe" -> {
                                Recipes recipe = new Recipes();
                                try {
                                    if (command.length > 1) {
                                        recipe.recipeDescription(command[1]);
                                    } else {
                                        recipe.recipe();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            case "addrecipe" -> {
                                AddRecipe addrecipe = new AddRecipe();
                                consoleArea.setText("You typed addrecipe so now we will create a recipe lets start.");
                                addrecipe.addRecipeName();
                            }
                            case "orders" -> {
                                Orders order = new Orders();
                                try {
                                    if (command.length > 1) {
                                        order.orderDescription(command[1]);
                                    } else {
                                        order.orders();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            case "customers" -> {
                                Customers cust = new Customers();
                                cust.passwordManager(command);
                            }
                            case "categories" -> {
                                Categories categ = new Categories();
                                try {
                                    if (command.length > 1) {
                                        categ.categoryRecipes(command[1]);
                                    } else {
                                        categ.categories();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            case "allergens" -> {
                                Allergens allerg = new Allergens();
                                try {
                                    if (command.length > 1) {
                                        allerg.allergenRecipes(command[1]);
                                    } else {
                                        allerg.allergens();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            case "recreate" -> {
                                Recreate rec = new Recreate();
                                rec.recreate();
                            }
                            case "reload" -> {
                                Reload rel = new Reload();
                                rel.reload();
                            }
                            case "exit" -> {
                                Shutdown shut = new Shutdown();
                                shut.shutdown();
                            }
                            case "error" -> {
                                DBUtils dbUtils = new DBUtils();
                                dbUtils.error();
                            }
                            default -> consoleArea.setText("YEET: " + command[0] + " isnt a command type help to get the command list.");
                        }
                });

        consolePanel = new JPanel();
        consolePanel.setPreferredSize(new Dimension(1280, 720));
        consolePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridLayout layout = new GridLayout(0,2);
        layout.setVgap(20);
        layout.setHgap(10);
        consolePanel.setLayout(layout);
        consolePanel.add(consoleLabel);
        consolePanel.add(scroll);
        consolePanel.add(commandLabel);
        consolePanel.add(commandField);
        consolePanel.add(new JLabel());
        consolePanel.add(commandButton);

        add(consolePanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Kraut und Rüben Console");
    }
}
