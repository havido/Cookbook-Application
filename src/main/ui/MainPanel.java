package ui;

import model.Ingredient;
import model.IngredientCategories;
import model.Recipe;
import model.RecipeLibrary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {
    private RecipeApp app;
    private SearchAddPanel saPanel;
    private JTextField searchBar;
    private JButton search;
    private RecipeLibrary library;

    public MainPanel(RecipeApp app, SearchAddPanel saPanel, LibraryPanel libPanel) {
        this.app = app;
        this.saPanel = saPanel;
        library = LibraryPanel.getLibrary();
        setBackground(new Color(241,235,225));
        setVisible(true);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        searchBar = new JTextField();
    }

    public void addRecipePanel() {
        removeAll();
        JPanel menu = new JPanel();
        menu.setSize(100,400);
        menu.setBackground(new Color(241, 235, 225));
        menu.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        menu.setVisible(true);

        JLabel drafts = new JLabel("Your current drafts:");
        if (library.getDrafts().isEmpty()) {
            drafts.setText("You don't have any drafts");
        }
        menu.add(drafts);

        JPanel main = new JPanel();
        main.setBackground(new Color(241, 235, 225));
        SpringLayout layout = new SpringLayout();
        main.setLayout(layout);

        JLabel name = new JLabel("Name of recipe: ");
        JTextField nameInput = new JTextField("");
        JLabel author = new JLabel("Name of author: ");
        JTextField authorInput = new JTextField("");
        JLabel time = new JLabel("Total time needed: ");
        JTextField timeInput = new JTextField("");
        JLabel ing = new JLabel("Add an ingredient: ");
        JTextField ingInput = new JTextField("");
        JComboBox dietChoice = new JComboBox<>(IngredientCategories.values());
        JButton addIng = new JButton("Add a new ingredient... +");
        JLabel step = new JLabel("Add a new step: ");
        JTextArea stepInput = new JTextArea();
        JButton addStep = new JButton("Add a new step... +");

        main.add(name);
        main.add(nameInput);
        main.add(author);
        main.add(authorInput);
        main.add(time);
        main.add(timeInput);
        main.add(ing);
        main.add(ingInput);
        main.add(dietChoice);
        main.add(addIng);
        main.add(step);
        main.add(stepInput);
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

        for (Recipe draft : library.getDrafts()) {
            JButton button = new JButton(draft.getName());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    nameInput.setText(draft.getName());
                    authorInput.setText(draft.getAuthor());
                    timeInput.setText(Integer.toString(draft.getTime()));

                    for (Ingredient i : draft.getIngredients()) {
                        ingInput.setText(i.getName());
                        dietChoice.setSelectedItem(i.getCategory());
                    }

                    main.setVisible(true);
                }
            });
            menu.add(button);
        }
    }

    public void searchRecipePanel() {
        removeAll();
        setLayout(new CardLayout());
        JLabel filterPrompt = new JLabel("Filter by: ");
        JButton filterName = new JButton("Name");
        JButton filterIng = new JButton("Ingredients");
        JButton filterDiet = new JButton("Dietary requirements");
        JButton filterTime = new JButton("Time needed");
    }

    public void filterNamePanel() {
        JLabel searchBox = new JLabel("Search here: ");
        JTextField searchBar = new JTextField();
        JButton search = new JButton("Search");
    }

    public void filterIngPanel() {
        JLabel searchBox = new JLabel("Search here: ");
        JTextField searchBar = new JTextField();
        JButton search = new JButton("Search");
    }

    public void filterDietPanel() {
        JLabel searchBox = new JLabel("Search here: ");
        JTextField searchBar = new JTextField();
        JButton search = new JButton("Search");
    }

    public void filterTimePanel() {
        JLabel searchBox = new JLabel("Search here: ");
        JTextField searchBar = new JTextField();
        JButton search = new JButton("Search");
    }
}
