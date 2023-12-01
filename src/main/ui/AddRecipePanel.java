package ui;

import layout.SpringUtilities;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.*;

public class AddRecipePanel extends JPanel {
    private RecipeAppContext context;
    private RecipeLibrary library;
    private JPanel menu;
    private JPanel main;
    private JPanel savePanel;
    private JsonWriter jsonWriter;
    private int counter;
    private ArrayList<Object> inputFields;

    public AddRecipePanel(RecipeAppContext context) {
        this.context = context;
        library = context.getLibrary();
        counter = 0;
        setBackground(new Color(241, 235, 225));
        setLayout(new BorderLayout());

        menu = new JPanel();
        main = new JPanel();
        savePanel = new JPanel();
        configureMenu();

        add(BorderLayout.WEST, menu);
        add(BorderLayout.CENTER, new JScrollPane(main));
        add(BorderLayout.SOUTH, savePanel);
    }

    public void configureMenu() {
        menu.setBackground(new Color(241, 235, 225));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setVisible(true);

        JLabel drafts = new JLabel("Your current drafts:");
        if (library.getDrafts().isEmpty()) {
            drafts.setText("You don't have any drafts");
        }
        menu.add(drafts);

        for (Recipe r : library.getDrafts()) {
            menuButtonExistingDraft(r);
        }

        JButton newRecipe = new JButton("Create new draft... +");
        newRecipe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configureMainEmpty();
            }
        });
        menu.add(newRecipe);
    }

    private void menuButtonExistingDraft(Recipe r) {
        JButton recipeButton = new JButton(r.getName());
        recipeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configureMainExisting(r);
            }
        });
        menu.add(recipeButton);
    }

    public void configureMainExisting(Recipe r) {
        main.removeAll();
        main.setBackground(new Color(241, 235, 225));
        SpringLayout layout = new SpringLayout();
        main.setLayout(layout);

        String[] labels = {"Name of recipe: ", "Name of author: ", "Total time needed: ",
                "Add an ingredient: ", "Add step: "};
        int numPairs = labels.length;
        counter = numPairs;
        inputFields = new ArrayList<>();
        // 0 = name, 1 = author, 2 = time, 3 = ingredientList, 4 = stepList

        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            main.add(l);

            switch (labels[i]) {
                case "Name of recipe: ": { // name
                    JTextField input = new JTextField(r.getName());
                    l.setLabelFor(input);
                    main.add(input);
                    inputFields.add(input); // index 0
                    break;
                }
                case "Name of author: ": { // author
                    JTextField input = new JTextField(r.getAuthor());
                    l.setLabelFor(input);
                    main.add(input);
                    inputFields.add(input); // index 1
                    break;
                }
                case "Total time needed: ": { // time
                    JSlider input = new JSlider(JSlider.HORIZONTAL, 0, 1000, r.getTime());
                    input.setMajorTickSpacing(100);
                    input.setMinorTickSpacing(10);
                    input.setPaintTicks(true);
                    input.setPaintLabels(true);
                    l.setLabelFor(input);
                    main.add(input);
                    inputFields.add(input); // index 2
                    break;
                }
                case "Add an ingredient: ": { // ingredient
                    ArrayList<Ingredient> updatedIngList = new ArrayList<>(); // i need an array to store each input
                    JPanel ingredientPanel = new JPanel(new BorderLayout());

                    for (int a = 0; a < r.getIngredients().size(); a++) {
//                        JPanel group = new JPanel();
                        JTextField ingName = new JTextField(r.getIngredients().get(a).getName());
                        JComboBox dietChoice = new JComboBox<>(IngredientCategories.values());
                        dietChoice.setSelectedItem(r.getIngredients().get(a).getCategory());
                        ingredientPanel.add(BorderLayout.CENTER, ingName);
                        ingredientPanel.add(BorderLayout.EAST, dietChoice);

                        if (!ingName.getText().isEmpty()) {
                            Ingredient ingredient = new Ingredient(ingName.getText(),
                                    IngredientCategories.valueOf(String.valueOf(dietChoice.getSelectedItem())));
                            updatedIngList.add(ingredient); // add all modified changes
                        }
                    }

                    JButton addIng = new JButton("Add a new ingredient... +");
                    addIng.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            JTextField newIngInput = new JTextField();
                            JComboBox newDietChoice = new JComboBox<>(IngredientCategories.values());
                            ingredientPanel.add(BorderLayout.CENTER, newIngInput);
                            ingredientPanel.add(BorderLayout.EAST, newDietChoice);

                            if (!String.valueOf(newIngInput).isEmpty()) {
                                Ingredient i = new Ingredient(String.valueOf(newIngInput),
                                        IngredientCategories.valueOf(String.valueOf(newDietChoice.getSelectedItem())));
                                updatedIngList.add(i); // keep track of changes
                            }
                        }
                    });
                    ingredientPanel.add(BorderLayout.CENTER, addIng);
                    inputFields.add(updatedIngList); // index 3
                    main.add(new JScrollPane(ingredientPanel));
                    break;
                }
                // STEP
                case "Add step: ": { // step
                    ArrayList<String> updatedStepList = new ArrayList<>(); // i need an array to store each input
                    counter += r.getSteps().size() - 1; // add the number of rows

                    for (int a = 0; a < r.getSteps().size(); a++) {
                        JTextField input = new JTextField(r.getSteps().get(a));
                        if (a == 0) {
                            l.setLabelFor(input);
                        } else {
                            JLabel placeholder = new JLabel();
                            placeholder.setLabelFor(input);
                            main.add(placeholder);
                        }
                        main.add(input);
                        if (!input.getText().isEmpty()) {
                            updatedStepList.add(input.getText()); // add all modified changes
                        }
                    }

                    JButton addStep = new JButton("Add a new step... +");
                    addStep.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            JTextField newInput = new JTextField();
                            if (!String.valueOf(newInput).isEmpty()) {
                                updatedStepList.add(newInput.getText()); // keep track of changes
                            }
                            JLabel placeholder2 = new JLabel();
                            placeholder2.setLabelFor(newInput);
                            main.add(placeholder2);
                            main.add(newInput);
                            counter++;
                            SpringUtilities.makeCompactGrid(main, counter, 2,6,6,6,6);
                        }
                    });
                    JLabel placeholder = new JLabel();
                    placeholder.setLabelFor(addStep);
                    counter++;
                    main.add(placeholder);
                    main.add(addStep);
                    inputFields.add(updatedStepList); // index 4
                    break;
                }
            }
        }
        System.out.println(counter);
        SpringUtilities.makeCompactGrid(main, counter, 2, 6, 6, 6, 6);
        main.setOpaque(true);
        main.revalidate();
        main.repaint();
        saveToLibraryExisting(r);
    }

    public void configureMainEmpty() {
        main.removeAll();
        main.setBackground(new Color(241, 235, 225));
        main.setLayout(new SpringLayout());

        String[] labels = {"Name of recipe: ", "Name of author: ", "Total time needed: ",
                "Add an ingredient: ", "Add step: "};
        int numPairs = labels.length;
        inputFields = new ArrayList<>();

        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            main.add(l);

            if (labels[i].equals("Add step: ")) { // step (done?)
                ArrayList<String> stepList = new ArrayList<>();
                JButton addStep = new JButton("Add a new step... +");
                addStep.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        JTextField newInput = new JTextField();
                        if (!String.valueOf(newInput).isEmpty()) {
                            stepList.add(String.valueOf(newInput)); // keep track of changes
                        }
                        JLabel placeholder2 = new JLabel();
                        placeholder2.setLabelFor(newInput);
                        main.add(placeholder2);
                        main.add(newInput);
                        counter++;
                    }
                });
                JLabel placeholder = new JLabel();
                placeholder.setLabelFor(addStep);
                counter++;
                main.add(placeholder);
                main.add(addStep);
                inputFields.add(stepList); // index 4

            } else if (labels[i].equals("Add an ingredient: ")) { // ingredient (done?)
                ArrayList<Ingredient> ingList = new ArrayList<>();

                JButton addIng = new JButton("Add a new ingredient... +");
                addIng.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        JPanel group = new JPanel();
                        JTextField newIngInput = new JTextField();
                        JComboBox newDietChoice = new JComboBox<>(IngredientCategories.values());
                        group.add(newIngInput);
                        group.add(newDietChoice);
                        if (!String.valueOf(newIngInput).isEmpty()) {
                            Ingredient i = new Ingredient(String.valueOf(newIngInput),
                                    IngredientCategories.valueOf(String.valueOf(newDietChoice.getSelectedItem())));
                            ingList.add(i); // keep track of changes
                        }
                        JLabel placeholder2 = new JLabel();
                        placeholder2.setLabelFor(group);
                        main.add(placeholder2);
                        main.add(group);
                        counter++;
                    }
                });

                JLabel placeholder = new JLabel();
                placeholder.setLabelFor(addIng);
                main.add(placeholder);
                main.add(addIng);
                inputFields.add(ingList); // index 3

            } else if (labels[i].equals("Total time needed: ")) { // time (done)
                JSlider input = new JSlider(JSlider.HORIZONTAL, 0, 1000, 1000);
                input.setMajorTickSpacing(100);
                input.setMinorTickSpacing(10);
                input.setPaintTicks(true);
                input.setPaintLabels(true);
                l.setLabelFor(input);
                main.add(input);
                inputFields.add(input); // index 3

            } else { // name, author (done)
                JTextField input = new JTextField();
                l.setLabelFor(input);
                main.add(input);
                inputFields.add(input); // index 1,2
            }
        }

        SpringUtilities.makeCompactGrid(main, numPairs, 2, 6, 6, 6, 6);
        main.setOpaque(true);
        main.revalidate();
        main.repaint();
        saveToLibraryEmpty();
    }

    public void saveToLibraryExisting(Recipe r) {
        savePanel.removeAll();
        savePanel.setBackground(new Color(241, 235, 225));

        JButton saveToDraft = new JButton("Save to draft");
        saveToDraft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveRecipe(r, RecipeTag.DRAFT);
                saveToJson();
            }
        });

        JButton saveForReal = new JButton("Save for real");
        saveForReal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveRecipe(r, RecipeTag.DEFAULT);
                saveToJson();
            }
        });

        savePanel.add(saveToDraft);
        savePanel.add(saveForReal);

        savePanel.revalidate();
        savePanel.repaint();
    }

    public void saveToLibraryEmpty() {
        savePanel.removeAll();
        savePanel.setBackground(new Color(241, 235, 225));

        JButton saveToDraft = new JButton("Save to draft");
        saveToDraft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Recipe r = new Recipe("a", "b", RecipeTag.DRAFT);
                saveRecipe(r, RecipeTag.DRAFT);
                saveToJson();
            }
        });

        JButton saveForReal = new JButton("Save for real");
        saveForReal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Recipe r = new Recipe("a", "b", RecipeTag.DEFAULT);
                saveRecipe(r, RecipeTag.DEFAULT);
                saveToJson();
            }
        });

        savePanel.add(saveToDraft);
        savePanel.add(saveForReal);

        savePanel.revalidate();
        savePanel.repaint();
    }

    private void saveRecipe(Recipe r, RecipeTag tag) {
        updateRecipe(r);
        r.setTag(tag);
        library.updateLibrary(library);

        main.removeAll();
        savePanel.removeAll();
        configureMenu();
        main.revalidate();
        main.repaint();
    }

    private void saveNewRecipe(RecipeTag tag) {
        // Create a new recipe with the user's input
        Recipe newRecipe = createRecipe(tag);

        // Add the new recipe to the library
        library.addRecipeToLibrary(newRecipe);

        // Clear the main panel
        main.removeAll();

        // Clear the save panel
        savePanel.removeAll();

        // Reconfigure the menu with the updated drafts
        configureMenu();

        // Revalidate and repaint the main panel
        main.revalidate();
        main.repaint();
    }

    private void updateRecipe(Recipe r) {
        // Update the recipe fields with the user's input
        r.setName(((JTextField) inputFields.get(0)).getText());
        r.setAuthor(((JTextField) inputFields.get(1)).getText());
        r.setTime(((JSlider) inputFields.get(2)).getValue());

        // Clear existing ingredients and add the updated ones
        r.getIngredients().clear();
        for (int i = 3; i < inputFields.size(); i += 2) {
            JTextField ingNameField = (JTextField) inputFields.get(i);
            JComboBox<String> dietChoice = (JComboBox<String>) inputFields.get(i + 1);

            if (!ingNameField.getText().isEmpty()) {
                Ingredient ingredient = new Ingredient(ingNameField.getText(), IngredientCategories.valueOf(dietChoice.getSelectedItem().toString()));
                r.addIngredients(ingredient);
            }
        }

        // Clear existing steps and add the updated ones
        r.getSteps().clear();
        for (int i = 3; i < inputFields.size(); i++) {
            JTextField stepField = (JTextField) inputFields.get(i);

            if (!stepField.getText().isEmpty()) {
                r.getSteps().add(stepField.getText());
            }
        }
    }

    private Recipe createRecipe(RecipeTag tag) {
        // Create a new recipe with the user's input
        Recipe newRecipe = new Recipe("", "", tag);

        // Update the recipe fields with the user's input
        newRecipe.setName(((JTextField) inputFields.get(0)).getText());
        newRecipe.setAuthor(((JTextField) inputFields.get(1)).getText());
        newRecipe.setTime(((JSlider) inputFields.get(2)).getValue());

        // Add ingredients to the new recipe
        for (int i = 3; i < inputFields.size(); i += 2) {
            JTextField ingNameField = (JTextField) inputFields.get(i);
            JComboBox<String> dietChoice = (JComboBox<String>) inputFields.get(i + 1);

            if (!ingNameField.getText().isEmpty()) {
                Ingredient ingredient = new Ingredient(ingNameField.getText(), IngredientCategories.valueOf(dietChoice.getSelectedItem().toString()));
                newRecipe.addIngredients(ingredient);
            }
        }

        // Add steps to the new recipe
        for (int i = 4; i < inputFields.size(); i++) {
            JTextField stepField = (JTextField) inputFields.get(i);

            if (!stepField.getText().isEmpty()) {
                newRecipe.getSteps().add(stepField.getText());
            }
        }

        return newRecipe;
    }

    public void saveToJson() {
        JLabel saveStatus = new JLabel();
        try {
            jsonWriter = new JsonWriter(context.getSource());
            jsonWriter.open();
            jsonWriter.write(library);
            jsonWriter.close();
            saveStatus.setText("All changes saved to " + context.getSource());
        } catch (FileNotFoundException e) {
            saveStatus.setText("Error saving library to " + context.getSource());
            e.printStackTrace();
        }
        savePanel.removeAll();
        savePanel.setBackground(new Color(241, 235, 225));
        savePanel.add(saveStatus);
        savePanel.revalidate();
        savePanel.repaint();
    }
}

