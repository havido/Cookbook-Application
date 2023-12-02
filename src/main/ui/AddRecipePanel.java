package ui;

import layout.SpringUtilities;
import model.*;
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

        JButton newRecipe = new JButton("Create new draft... +");
        newRecipe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configureMain(new Recipe("", "", RecipeTag.DRAFT));
            }
        });
        menu.add(newRecipe);

        JLabel drafts = new JLabel("Your current drafts:");
        if (library.getDrafts().isEmpty()) {
            drafts.setText("You don't have any drafts");
        }
        menu.add(drafts);

        for (Recipe r : library.getDrafts()) {
            menuButtonExistingDraft(r);
        }
    }

    private void menuButtonExistingDraft(Recipe r) {
        JButton recipeButton = new JButton(r.getName());
        recipeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configureMain(r);
            }
        });
        menu.add(recipeButton);
    }

    @SuppressWarnings("methodlength")
    private void configureMain(Recipe r) {
        resetDisplayMenu();

        String[] labels = {"Name of recipe: ", "Name of author: ", "Total time needed: ",
                "Add an ingredient: ", "Add step: "};
        int numPairs = labels.length;
        inputFields = new ArrayList<>();
        // 0 = name, 1 = author, 2 = time, 3 = ingredientList, 4 = stepList

        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            main.add(l);

            switch (labels[i]) {
                case "Name of recipe: ": {
                    createRowsWithTextField(r.getName(), l);
                    break;
                } case "Name of author: ": { // author
                    createRowsWithTextField(r.getAuthor(), l);
                    break;
                } case "Total time needed: ": { // time
                    createRowsWithSlider(r.getTime(), l);
                    break;
                }
                case "Add an ingredient: ": { // ingredient
                    createRowsWithIngredients(r, l);
                    break;
                } case "Add step: ": { // step
                    createRowsWithSteps(r, l);
                    break;
                }
            }
        }
        SpringUtilities.makeCompactGrid(main, numPairs, 2, 6, 6, 6, 6);
        main.setOpaque(true);
        main.revalidate();
        main.repaint();
        saveToLibrary(r, (ArrayList<Ingredient>) inputFields.get(3), (ArrayList<String>) inputFields.get(4));
    }

    private void createRowsWithSteps(Recipe r, JLabel l) {
        ArrayList<JTextField> stepList = new ArrayList<>(); // i need an array to store each input
        JPanel stepPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(stepPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        main.add(scrollPane);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        stepPanel.add(BorderLayout.CENTER, inputPanel);
        l.setLabelFor(stepPanel);

        for (int a = 0; a < r.getSteps().size(); a++) {
            JTextField input = new JTextField(r.getSteps().get(a));
            inputPanel.add(input);
            stepList.add(input);
        }

        JButton addStep = getAddStepButton(stepList, stepPanel, inputPanel);
        stepPanel.add(BorderLayout.SOUTH, addStep);
        inputPanel.add(Box.createVerticalGlue());
        inputFields.add(stepList); // index 5
    }

    private static JButton getAddStepButton(ArrayList<JTextField> stepList, JPanel stepPanel, JPanel inputPanel) {
        JButton addStep = new JButton("Add a new step... +");
        addStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JTextField newInput = new JTextField();
                inputPanel.add(newInput);
                stepList.add(newInput);
                stepPanel.revalidate();
                stepPanel.repaint();
            }
        });
        return addStep;
    }

    private void createRowsWithIngredients(Recipe r, JLabel l) {
        ArrayList<JTextField> ingList = new ArrayList<>(); // i need an array to store each input
        ArrayList<JComboBox> dietList = new ArrayList<>();
        JPanel ingredientPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(ingredientPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        main.add(scrollPane);

        JPanel ingNamePanel = new JPanel();
        JPanel ingCatPanel = new JPanel();
        ingNamePanel.setLayout(new BoxLayout(ingNamePanel, BoxLayout.Y_AXIS));
        ingCatPanel.setLayout(new BoxLayout(ingCatPanel, BoxLayout.Y_AXIS));
        ingredientPanel.add(BorderLayout.EAST, ingCatPanel);
        ingredientPanel.add(BorderLayout.CENTER, ingNamePanel);
        l.setLabelFor(ingredientPanel);

        printOriginalIngList(r, ingList, dietList, ingNamePanel, ingCatPanel);

        JButton addIng = getAddIngButton(ingList, dietList, ingredientPanel, ingNamePanel, ingCatPanel);
        ingredientPanel.add(BorderLayout.SOUTH, addIng);
        ingNamePanel.add(Box.createVerticalGlue());
        ingCatPanel.add(Box.createVerticalGlue());
        inputFields.add(ingList); // index 3
        inputFields.add(dietList); // index 4
    }

    private static void printOriginalIngList(Recipe r, ArrayList<JTextField> ingList, ArrayList<JComboBox> dietList,
                                             JPanel ingNamePanel, JPanel ingCatPanel) {
        if (!r.getIngredients().isEmpty()) {
            for (int a = 0; a < r.getIngredients().size(); a++) {
                JTextField ingName = new JTextField(r.getIngredients().get(a).getName());
                JComboBox dietChoice = new JComboBox<>(IngredientCategories.values());
                dietChoice.setSelectedItem(r.getIngredients().get(a).getCategory());
                ingNamePanel.add(ingName);
                ingCatPanel.add(dietChoice);

                ingList.add(ingName);
                dietList.add(dietChoice);
            }
        }
    }

    private static JButton getAddIngButton(ArrayList<JTextField> ingList, ArrayList<JComboBox> dietList,
                                           JPanel ingredientPanel, JPanel ingNamePanel, JPanel ingCatPanel) {
        JButton addIng = new JButton("Add a new ingredient... +");
        addIng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JTextField newIngInput = new JTextField();
                JComboBox newDietChoice = new JComboBox<>(IngredientCategories.values());
                ingNamePanel.add(newIngInput);
                ingCatPanel.add(newDietChoice);

                ingList.add(newIngInput);
                dietList.add(newDietChoice);

                ingredientPanel.revalidate();
                ingredientPanel.repaint();
            }
        });
        return addIng;
    }

    private void createRowsWithSlider(int r, JLabel l) {
        JSlider input = new JSlider(JSlider.HORIZONTAL, 0, 1000, r);
        input.setMajorTickSpacing(100);
        input.setMinorTickSpacing(10);
        input.setPaintTicks(true);
        input.setPaintLabels(true);
        l.setLabelFor(input);
        main.add(input);
        inputFields.add(input); // index 2
    }

    private void createRowsWithTextField(String r, JLabel l) {
        JTextField input = new JTextField(r);
        l.setLabelFor(input);
        main.add(input);
        inputFields.add(input); // index 0
    }

    private void resetDisplayMenu() {
        main.removeAll();
        savePanel.removeAll();
        main.setBackground(new Color(241, 235, 225));
        savePanel.setBackground(new Color(241, 235, 225));
        SpringLayout layout = new SpringLayout();
        main.setLayout(layout);
    }

    public void saveToLibrary(Recipe r, ArrayList<Ingredient> ingList, ArrayList<String> stepList) {
        JLabel status = new JLabel();
        JButton saveToDraft = new JButton("Save to draft");
        saveToDraft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRecipe(r);
                if (!library.getDrafts().contains(r)) {
                    library.addRecipeToLibrary(r);
                    menuButtonExistingDraft(r);
                }
                menu.revalidate();
                menu.repaint();
                saveToJson();
                status.setText("Successfully saved!");
            }
        });

        JButton saveForReal = new JButton("Save for real");
        configureSaveForRealButton(r, status, saveForReal);
        savePanel.add(saveToDraft);
        savePanel.add(saveForReal);
        savePanel.add(status);
        savePanel.revalidate();
        savePanel.repaint();
    }

    private void configureSaveForRealButton(Recipe r, JLabel status, JButton saveForReal) {
        saveForReal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRecipe(r);
                if (!r.checkNotNull()) {
                    status.setText("One or more fields are incomplete!");
                } else {
                    r.setTag(RecipeTag.DEFAULT);
                    if (!library.getDrafts().contains(r)) {
                        library.addRecipeToLibrary(r);
                    } else {
                        library.getDrafts().remove(r);
                        library.getLibrary().add(r);
                    }
                    saveToJson();
                    status.setText("Successfully saved!");
                }
            }
        });
    }

    private ArrayList updateIngList() {
        ArrayList<JTextField> ingNames = (ArrayList<JTextField>) inputFields.get(3);
        ArrayList<JComboBox> dietChoices = (ArrayList<JComboBox>) inputFields.get(4);
        ArrayList<Ingredient> updatedIngList = new ArrayList<>();
        for (int i = 0; i < ingNames.size(); i++) {
            if (!ingNames.get(i).getText().isBlank()) {
                Ingredient ing = new Ingredient(ingNames.get(i).getText(),
                        IngredientCategories.valueOf(String.valueOf(dietChoices.get(i).getSelectedItem())));
                updatedIngList.add(ing);
            }
        }
        return updatedIngList;
    }

    private ArrayList updateStepList() {
        ArrayList<JTextField> steps = (ArrayList<JTextField>) inputFields.get(5);
        ArrayList<String> updatedStepList = new ArrayList<>();
        for (int i = 0; i < steps.size(); i++) {
            if (!steps.get(i).getText().isBlank()) {
                updatedStepList.add(steps.get(i).getText());
            }
        }
        return updatedStepList;
    }

    private void updateRecipe(Recipe r) {
        r.setName(((JTextField) inputFields.get(0)).getText());
        r.setAuthor(((JTextField) inputFields.get(1)).getText());
        r.setTime(((JSlider) inputFields.get(2)).getValue());

        ArrayList<Ingredient> ingList = updateIngList();
        r.getIngredients().clear();
        for (Ingredient i : ingList) {
            r.addIngredients(i);
        }

        ArrayList<String> stepList = updateStepList();
        r.getSteps().clear();
        r.getSteps().addAll(stepList);
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
        savePanel.add(saveStatus);
        savePanel.revalidate();
        savePanel.repaint();
    }
}

