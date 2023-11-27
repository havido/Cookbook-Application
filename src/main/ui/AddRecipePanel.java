package ui;

import model.Ingredient;
import model.IngredientCategories;
import model.Recipe;
import model.RecipeLibrary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class AddRecipePanel extends JPanel {
//    private RecipeApp app;
    private MainPanel mainPanel;
    private RecipeLibrary library;
    private JPanel menu;
    private JPanel main;
    private JLabel name;
    private JTextField nameInput;
    private JLabel author;
    private JTextField authorInput;
    private JLabel time;
    private JTextField timeInput;
    private JLabel ing;
    private ArrayList<Ingredient> ingredientList;
    private JTextField ingInput;
    private JComboBox dietChoice;
    private JButton addIng;
    private ArrayList<String> stepList;
    private JLabel step;
    private JTextArea stepInput;
    private JButton addStep;

    public AddRecipePanel(MainPanel mainPanel) {
//        this.app = app;
        this.mainPanel = mainPanel;
        library = LibraryPanel.getLibrary();
        setBackground(new Color(241,235,225));
        setVisible(true);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        configureMenu();
        configureMain();

        add(menu);
        add(main);
    }

    public void configureMain() {
        main = new JPanel();
        main.setBackground(new Color(241, 235, 225));
        SpringLayout layout = new SpringLayout();
        main.setLayout(layout);

        name = new JLabel("Name of recipe: ");
        nameInput = new JTextField("");
        author = new JLabel("Name of author: ");
        authorInput = new JTextField("");
        time = new JLabel("Total time needed: ");
        timeInput = new JTextField("");
        ingredientList = new ArrayList<Ingredient>();
        ing = new JLabel("Add an ingredient: ");
        addIng = new JButton("Add a new ingredient... +");
        addIng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JTextField newIngInput = new JTextField();
                JComboBox newDietChoice = new JComboBox<>(IngredientCategories.values());
                if (!String.valueOf(newIngInput).isEmpty()) {
                    Ingredient i = new Ingredient(String.valueOf(newIngInput),
                            IngredientCategories.valueOf(String.valueOf(newDietChoice)));
                    ingredientList.add(i);
                }
                main.add(newIngInput);
                main.add(newDietChoice);
                // layout.putConstraint...
            }
        });
        stepList = new ArrayList<String>();
        step = new JLabel("Add a new step: ");
        addStep = new JButton("Add a new step... +");
        addStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JTextField newStepInput = new JTextField("");
                if (!String.valueOf(newStepInput).isEmpty()) {
                    stepList.add(String.valueOf(newStepInput));
                }
                main.add(newStepInput);
                // layout.putConstraint...
            }
        });

        main.add(name);
        main.add(nameInput);
        main.add(author);
        main.add(authorInput);
        main.add(time);
        main.add(timeInput);
        main.add(ing);
        main.add(addIng);
        main.add(step);
        main.add(addStep);

        layout.putConstraint(SpringLayout.WEST, name, 10, SpringLayout.WEST, main);
        layout.putConstraint(SpringLayout.NORTH, name, 10, SpringLayout.NORTH, main);
        layout.putConstraint(SpringLayout.WEST, nameInput, 5, SpringLayout.EAST, name);
        layout.putConstraint(SpringLayout.NORTH, nameInput, 10, SpringLayout.NORTH, main);

        layout.putConstraint(SpringLayout.WEST, author, 10, SpringLayout.WEST, main);
        layout.putConstraint(SpringLayout.NORTH, author, 10, SpringLayout.SOUTH, name);
        layout.putConstraint(SpringLayout.WEST, authorInput, 5, SpringLayout.EAST, author);
        layout.putConstraint(SpringLayout.NORTH, authorInput, 10, SpringLayout.SOUTH, nameInput);

        layout.putConstraint(SpringLayout.WEST, time, 10, SpringLayout.WEST, main);
        layout.putConstraint(SpringLayout.NORTH, time, 10, SpringLayout.SOUTH, author);
        layout.putConstraint(SpringLayout.WEST, timeInput, 5, SpringLayout.EAST, time);
        layout.putConstraint(SpringLayout.NORTH, timeInput, 10, SpringLayout.SOUTH, authorInput);

        layout.putConstraint(SpringLayout.WEST, ing, 10, SpringLayout.WEST, main);
        layout.putConstraint(SpringLayout.NORTH, ing, 10, SpringLayout.SOUTH, time);
        layout.putConstraint(SpringLayout.WEST, ingInput, 5, SpringLayout.EAST, ing);
        layout.putConstraint(SpringLayout.NORTH, ingInput, 10, SpringLayout.SOUTH, timeInput);
        layout.putConstraint(SpringLayout.NORTH, dietChoice, 10, SpringLayout.SOUTH, timeInput);
        layout.putConstraint(SpringLayout.WEST, dietChoice, 5, SpringLayout.EAST, ingInput);
        layout.putConstraint(SpringLayout.SOUTH, ingInput, 10, SpringLayout.NORTH, addIng);

        layout.putConstraint(SpringLayout.WEST, step, 10, SpringLayout.WEST, main);
        layout.putConstraint(SpringLayout.WEST, stepInput, 5, SpringLayout.EAST, step);
        layout.putConstraint(SpringLayout.NORTH, stepInput, 10, SpringLayout.SOUTH, addIng);
        layout.putConstraint(SpringLayout.SOUTH, stepInput, 10, SpringLayout.NORTH, addStep);
        layout.putConstraint(SpringLayout.SOUTH, addStep, 10, SpringLayout.SOUTH, main);
    }

    public void configureMenu() {
        menu = new JPanel();
        menu.setSize(100,400);
        menu.setBackground(new Color(241, 235, 225));
        menu.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        menu.setVisible(true);

        JLabel drafts = new JLabel("Your current drafts:");
        if (library.getDrafts().isEmpty()) {
            drafts.setText("You don't have any drafts");
        }
        menu.add(drafts);

        for (Recipe r : library.getDrafts()) {
            JButton recipeButton = new JButton(r.getName());
            recipeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    nameInput.setText(r.getName());
                    authorInput.setText(r.getAuthor());
                    timeInput.setText(String.valueOf(r.getTime()));
                    for (Ingredient i : r.getIngredients()) {
                        ingInput = new JTextField(i.getName());
                        dietChoice = new JComboBox<>(IngredientCategories.values());
                        dietChoice.setSelectedItem(i.getCategory());
                        main.add(ingInput);
                        main.add(dietChoice);
                        Ingredient j = new Ingredient(String.valueOf(ingInput),
                                IngredientCategories.valueOf(String.valueOf(dietChoice)));
                        ingredientList.add(j);
                    }
                    for (String s : r.getSteps()) {
                        stepInput = new JTextArea(s);
                        main.add(stepInput);
                        stepList.add(String.valueOf(stepInput));
                    }
                }
            });
            menu.add(recipeButton);
        }

        JButton newRecipe = new JButton("Create new draft... +");
        newRecipe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setVisible(true);
            }
        });
    }

    public void saveRecipe(Recipe r) {
        if (library.getDrafts().contains(r)) {
            r.setName(String.valueOf(nameInput));
            r.setAuthor(String.valueOf(authorInput));
            r.setTime(Integer.valueOf(String.valueOf(timeInput)));

            r.getIngredients().removeAll(r.getIngredients());
            for (Ingredient i : ingredientList) {
                r.addIngredients(i);
            }

            r.getSteps().removeAll(r.getSteps());
            for (String s : stepList) {
                r.getSteps().add(s);
            }
        } else {
            library.addRecipeToLibrary(r);
        }
    }
}
