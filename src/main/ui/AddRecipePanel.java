package ui;

import layout.SpringUtilities;
import model.Ingredient;
import model.IngredientCategories;
import model.Recipe;
import model.RecipeLibrary;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class AddRecipePanel extends JPanel {
    private RecipeLibrary library;
    private JPanel menu;
    private JPanel main;

    public AddRecipePanel(RecipeAppContext context) {
        library = context.getLibrary();
        setBackground(new Color(241, 235, 225));
        setVisible(true);
        setLayout(new BorderLayout());

        menu = new JPanel();
        main = new JPanel();
        configureMenu();

        add(menu, BorderLayout.WEST);
        add(main, BorderLayout.CENTER);
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
            JButton recipeButton = new JButton(r.getName());
            recipeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    configureMainExisting(r);
                }
            });
            menu.add(recipeButton);
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

    public void configureMainExisting(Recipe r) {
        main.removeAll();
        main.setBackground(new Color(241, 235, 225));
        main.setLayout(new SpringLayout());

        String[] labels = {"Name of recipe: ", "Name of author: ", "Total time needed: ",
                "Add an ingredient: ", "Add step: "};
        int numPairs = labels.length;
        ArrayList<Object> inputFields = new ArrayList<>();

        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            main.add(l);

            if (labels[i].equals("Add step: ")) { // step (done?)
                boolean b = false;
                for (String s : r.getSteps()) {
                    JTextField input = new JTextField(s);
                    if (!b) {
                        l.setLabelFor(input);
                        b = true;
                    }
                    main.add(input);
                }
                JButton addStep = new JButton("Add a new step... +");
                addStep.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        JTextField newStep = new JTextField();
                        if (!String.valueOf(newStep).isEmpty()) {
                            r.getSteps().add(String.valueOf(newStep));
                        }
                        main.add(newStep);
                    }
                });
                main.add(addStep);
                inputFields.add(addStep);

            } else if (labels[i].equals("Add an ingredient: ")) { // ingredient (done?)
                boolean b = false;
                for (Ingredient ing : r.getIngredients()) {
                    JTextField input = new JTextField(ing.getName());
                    JComboBox dietChoice = new JComboBox<>(IngredientCategories.values());
                    dietChoice.setSelectedItem(ing.getCategory());
                    if (!b) {
                        l.setLabelFor(input);
                        b = true;
                    }
                    main.add(input);
                    main.add(dietChoice);
                    inputFields.add(input);
                }

                JButton addIng = new JButton("Add a new ingredient... +");
                addIng.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        JTextField newIngInput = new JTextField();
                        JComboBox newDietChoice = new JComboBox<>(IngredientCategories.values());
                        if (!String.valueOf(newIngInput).isEmpty()) {
                            Ingredient i = new Ingredient(String.valueOf(newIngInput),
                                    IngredientCategories.valueOf(String.valueOf(newDietChoice)));
                            r.getIngredients().add(i);
                        }
                        main.add(newIngInput);
                        main.add(newDietChoice);
                    }
                });
                main.add(addIng);
                inputFields.add(addIng);

            } else if (labels[i].equals("Total time needed: ")) { // time (done)
                JSlider input = new JSlider(JSlider.HORIZONTAL, 0, 1000, r.getTime());
                input.setMajorTickSpacing(100);
                input.setMinorTickSpacing(10);
                input.setPaintTicks(true);
                input.setPaintLabels(true);
                l.setLabelFor(input);
                main.add(input);
                inputFields.add(input);

            } else if (labels[i].equals("Name of recipe: ")) { // name (done)
                JTextField input = new JTextField(r.getName());
                l.setLabelFor(input);
                main.add(input);
                inputFields.add(input);
            } else if (labels[i].equals("Name of author: ")) { // author (done)
                JTextField input = new JTextField(r.getAuthor());
                l.setLabelFor(input);
                main.add(input);
                inputFields.add(input);
            }
        }

        SpringUtilities.makeCompactGrid(main, numPairs, 2, 6, 6, 6, 6);
        main.setOpaque(true);
        main.revalidate();
        main.repaint();
    }

    public void configureMainEmpty() {
        main.removeAll();
        main.setBackground(new Color(241, 235, 225));
        main.setLayout(new SpringLayout());

        String[] labels = {"Name of recipe: ", "Name of author: ", "Total time needed: ",
                "Add an ingredient: ", "Add step: "};
        int numPairs = labels.length;
        ArrayList<Object> inputFields = new ArrayList<>();

        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            main.add(l);

            if (labels[i].equals("Add step: ")) { // step (done?)
                JButton addStep = new JButton("Add a new step... +");
                l.setLabelFor(addStep);
                boolean b = false;
                addStep.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        JTextField newStep = new JTextField();
//                        if (!b) {
//                            l.setLabelFor(newStep);
//                            b = true;
//                        }
                        ArrayList<String> stepList = new ArrayList<>();
                        if (!String.valueOf(newStep).isEmpty()) {
                            stepList.add(newStep.getText());
                        }
                        main.add(newStep);
                    }
                });
                l.setLabelFor(addStep);
                main.add(addStep);
                inputFields.add(addStep);

            } else if (labels[i].equals("Add an ingredient: ")) { // ingredient (done?)
                boolean b = false;
                ArrayList<Ingredient> ingList = new ArrayList<>();

                JButton addIng = new JButton("Add a new ingredient... +");
                addIng.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        JTextField newIngInput = new JTextField();
                        JComboBox newDietChoice = new JComboBox<>(IngredientCategories.values());
                        if (!String.valueOf(newIngInput).isEmpty()) {
                            Ingredient i = new Ingredient(String.valueOf(newIngInput),
                                    IngredientCategories.valueOf(String.valueOf(newDietChoice)));
                            ingList.add(i);
                        }
                        main.add(newIngInput);
                        main.add(newDietChoice);
                    }
                });
                l.setLabelFor(addIng);
                main.add(addIng);
                inputFields.add(addIng);

            } else if (labels[i].equals("Total time needed: ")) { // time (done)
                JSlider input = new JSlider(JSlider.HORIZONTAL, 0, 1000, 1000);
                input.setMajorTickSpacing(100);
                input.setMinorTickSpacing(10);
                input.setPaintTicks(true);
                input.setPaintLabels(true);
                l.setLabelFor(input);
                main.add(input);
                inputFields.add(input);

            } else { // name, author (done)
                JTextField input = new JTextField();
                l.setLabelFor(input);
                main.add(input);
                inputFields.add(input);
            }
        }

        SpringUtilities.makeCompactGrid(main, numPairs, 2, 6, 6, 6, 6);
        main.setOpaque(true);
        main.revalidate();
        main.repaint();
    }
}

//    public void configureMain() {
//        main.setBackground(new Color(241, 235, 225));
//        main.setLayout(new SpringLayout());
//
//        String[] labels = {"Name of recipe: ", "Name of author: ", "Total time needed: ",
//                "Add an ingredient: ", "Add step: "};
//        int numPairs = labels.length;
//        ArrayList<Object> inputFields = new ArrayList<>();
//
//        for (int i = 0; i < numPairs; i++) {
//            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
//            main.add(l);
//
//            if (labels[i].equals(" ")) {
//                String[] diets = {"None", "Lactose-free", "Gluten-free", "Vegetarian"};
//                JComboBox<String> input = new JComboBox<>(diets);
//                l.setLabelFor(input);
//                main.add(input);
//                inputFields.add(input);
//            } else if (labels[i].equals("Total time needed: ")) {
//                JSlider input = new JSlider(JSlider.HORIZONTAL, 0, 1000, 1000);
//                input.setMajorTickSpacing(100);
//                input.setMinorTickSpacing(10);
//                input.setPaintTicks(true);
//                input.setPaintLabels(true);
//                l.setLabelFor(input);
//                main.add(input);
//                inputFields.add(input);
//            } else {
//                JTextField input = new JTextField();
//                l.setLabelFor(input);
//                main.add(input);
//                inputFields.add(input);
//            }
//        }
//
//        SpringUtilities.makeCompactGrid(main, numPairs, 2, 6, 6, 6, 6);
//        main.setOpaque(true);
//
////        name = new JLabel("Name of recipe: ");
////        nameInput = new JTextField("");
////        author = new JLabel("Name of author: ");
////        authorInput = new JTextField("");
////        time = new JLabel("Total time needed: ");
////        timeInput = new JTextField("");
////        ing = new JLabel("Add an ingredient: ");
////        addIng = new JButton("Add a new ingredient... +");
////        addIng.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent ae) {
////                JTextField newIngInput = new JTextField();
////                JComboBox newDietChoice = new JComboBox<>(IngredientCategories.values());
////                if (!String.valueOf(newIngInput).isEmpty()) {
////                    Ingredient i = new Ingredient(String.valueOf(newIngInput),
////                            IngredientCategories.valueOf(String.valueOf(newDietChoice)));
////                    ingredientList.add(i);
////                }
////                main.add(newIngInput);
////                main.add(newDietChoice);
////                // layout.putConstraint...
////            }
////        });
////        stepList = new ArrayList<String>();
////        step = new JLabel("Add a new step: ");
////        addStep = new JButton("Add a new step... +");
////        addStep.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent ae) {
////                JTextField newStepInput = new JTextField("");
////                if (!String.valueOf(newStepInput).isEmpty()) {
////                    stepList.add(String.valueOf(newStepInput));
////                }
////                main.add(newStepInput);
////                // layout.putConstraint...
////            }
////        });
//
////        main.add(name);
////        main.add(nameInput);
////        main.add(author);
////        main.add(authorInput);
////        main.add(time);
////        main.add(timeInput);
////        main.add(ing);
////        main.add(addIng);
////        main.add(step);
////        main.add(addStep);
//
////        layout.putConstraint(SpringLayout.WEST, name, 10, SpringLayout.WEST, main);
////        layout.putConstraint(SpringLayout.NORTH, name, 10, SpringLayout.NORTH, main);
////        layout.putConstraint(SpringLayout.WEST, nameInput, 5, SpringLayout.EAST, name);
////        layout.putConstraint(SpringLayout.NORTH, nameInput, 10, SpringLayout.NORTH, main);
////
////        layout.putConstraint(SpringLayout.WEST, author, 10, SpringLayout.WEST, main);
////        layout.putConstraint(SpringLayout.NORTH, author, 10, SpringLayout.SOUTH, name);
////        layout.putConstraint(SpringLayout.WEST, authorInput, 5, SpringLayout.EAST, author);
////        layout.putConstraint(SpringLayout.NORTH, authorInput, 10, SpringLayout.SOUTH, nameInput);
////
////        layout.putConstraint(SpringLayout.WEST, time, 10, SpringLayout.WEST, main);
////        layout.putConstraint(SpringLayout.NORTH, time, 10, SpringLayout.SOUTH, author);
////        layout.putConstraint(SpringLayout.WEST, timeInput, 5, SpringLayout.EAST, time);
////        layout.putConstraint(SpringLayout.NORTH, timeInput, 10, SpringLayout.SOUTH, authorInput);
////
////        layout.putConstraint(SpringLayout.WEST, ing, 10, SpringLayout.WEST, main);
////        layout.putConstraint(SpringLayout.NORTH, ing, 10, SpringLayout.SOUTH, time);
////        layout.putConstraint(SpringLayout.WEST, ingInput, 5, SpringLayout.EAST, ing);
////        layout.putConstraint(SpringLayout.NORTH, ingInput, 10, SpringLayout.SOUTH, timeInput);
////        layout.putConstraint(SpringLayout.NORTH, dietChoice, 10, SpringLayout.SOUTH, timeInput);
////        layout.putConstraint(SpringLayout.WEST, dietChoice, 5, SpringLayout.EAST, ingInput);
////        layout.putConstraint(SpringLayout.SOUTH, ingInput, 10, SpringLayout.NORTH, addIng);
////
////        layout.putConstraint(SpringLayout.WEST, step, 10, SpringLayout.WEST, main);
////        layout.putConstraint(SpringLayout.WEST, stepInput, 5, SpringLayout.EAST, step);
////        layout.putConstraint(SpringLayout.NORTH, stepInput, 10, SpringLayout.SOUTH, addIng);
////        layout.putConstraint(SpringLayout.SOUTH, stepInput, 10, SpringLayout.NORTH, addStep);
////        layout.putConstraint(SpringLayout.SOUTH, addStep, 10, SpringLayout.SOUTH, main);
//    }
//
//    public void saveRecipe(Recipe r) {
//        if (library.getDrafts().contains(r)) {
//            r.setName(String.valueOf(nameInput));
//            r.setAuthor(String.valueOf(authorInput));
//            r.setTime(Integer.valueOf(String.valueOf(timeInput)));
//
//            r.getIngredients().removeAll(r.getIngredients());
//            for (Ingredient i : ingredientList) {
//                r.addIngredients(i);
//            }
//
//            r.getSteps().removeAll(r.getSteps());
//            for (String s : stepList) {
//                r.getSteps().add(s);
//            }
//        } else {
//            library.addRecipeToLibrary(r);
//        }
//    }
//}
