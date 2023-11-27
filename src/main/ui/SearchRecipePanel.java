package ui;

import model.IngredientCategories;
import model.Recipe;
import model.RecipeLibrary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchRecipePanel extends JPanel {
    private final String ingPrompt = "Enter ingredients, each separated by a comma";
//    private RecipeApp app;
    private MainPanel mainPanel;
    private RecipeLibrary library;
    private JPanel searchPanel;
    private JPanel resultPanel;
    private JButton filName;
    private JButton filIng;
    private JButton filDiet;
    private JButton filTime;
    private JTextField filNameInput;
    private JTextField filIngInput;
    private JComboBox filDietInput;
    private JSlider filTimeInput;
    private JButton enter;

    public SearchRecipePanel(MainPanel mainPanel) {
//        this.app = app;
        this.mainPanel = mainPanel;
        library = LibraryPanel.getLibrary();
        setBackground(new Color(241, 235, 225));
        setVisible(true);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        configureSearch();

        add(searchPanel);
        add(resultPanel);
    }

    public void configureSearch() {
        searchPanel = new JPanel();
        searchPanel.setBackground(new Color(241, 235, 225));
        SpringLayout layout = new SpringLayout();
        searchPanel.setLayout(layout);
        List<Recipe> tempLib;

        filName = new JButton("Filter by name");
        filNameInput = new JTextField();
        filIng = new JButton("Filter by ingredients");
        filIng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filIngInput.setVisible(true);
            }
        });
        filIngInput = new JTextField(ingPrompt);
        filDiet = new JButton("Filter by dietary requirements");
        filDiet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filDietInput.setVisible(true);
            }
        });
        filDietInput = new JComboBox<>(IngredientCategories.values());
        filTime = new JButton("Filter by time");
        filTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filTimeInput.setVisible(true);
            }
        });
        filTimeInput = new JSlider(0, 1000);
        enter = new JButton("Search!");
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean filterByName = filNameInput.isVisible() && !filNameInput.getText().isEmpty();
                boolean filterByIngredients = filIngInput.isVisible() && !filIngInput.getText().isEmpty();
                boolean filterByDiet = filDietInput.isVisible() && filDietInput.getSelectedIndex() != -1;
                boolean filterByTime = filTimeInput.isVisible();

                // If none of the fields are filled, do nothing
                if (!filterByName && !filterByIngredients && !filterByDiet && !filterByTime) {
                    return;
                }

                // Filter the library based on the user input
                List<Recipe> filteredRecipes = library.getAllRecipes();

                if (filterByName) {
                    filteredRecipes = library.filterByName(filNameInput.getText());
                }
                if (filterByIngredients) {
                    filteredRecipes = library.filterByMultipleIng(filIngInput.getText());
                }
                if (filterByDiet) {
                    filteredRecipes = library.filterByDiet(filDietInput.getSelectedItem().toString());
                }
                if (filterByTime) {
                    filteredRecipes = library.filterByTime(filTimeInput.getValue());
                }

                configureResult(filteredRecipes);
            }
        });

        searchPanel.add(filName);
        searchPanel.add(filNameInput);
        searchPanel.add(filIng);
        searchPanel.add(filIngInput);
        searchPanel.add(filDiet);
        searchPanel.add(filDietInput);
        searchPanel.add(filTime);
        searchPanel.add(filTimeInput);
        searchPanel.add(enter);

        layout.putConstraint(SpringLayout.WEST, filName, 10, SpringLayout.WEST, searchPanel);
        layout.putConstraint(SpringLayout.NORTH, filName, 10, SpringLayout.NORTH, searchPanel);
        layout.putConstraint(SpringLayout.WEST, filNameInput, 5, SpringLayout.EAST, filName);
        layout.putConstraint(SpringLayout.NORTH, filNameInput, 10, SpringLayout.NORTH, searchPanel);

        layout.putConstraint(SpringLayout.WEST, filIng, 10, SpringLayout.WEST, searchPanel);
        layout.putConstraint(SpringLayout.NORTH, filIng, 10, SpringLayout.SOUTH, filName);
        layout.putConstraint(SpringLayout.WEST, filIngInput, 5, SpringLayout.EAST, filIng);
        layout.putConstraint(SpringLayout.NORTH, filIngInput, 10, SpringLayout.SOUTH, filNameInput);

        layout.putConstraint(SpringLayout.WEST, filDiet, 10, SpringLayout.WEST, searchPanel);
        layout.putConstraint(SpringLayout.NORTH, filDiet, 10, SpringLayout.SOUTH, filIng);
        layout.putConstraint(SpringLayout.WEST, filDietInput, 5, SpringLayout.EAST, filDiet);
        layout.putConstraint(SpringLayout.NORTH, filDietInput, 10, SpringLayout.SOUTH, filIngInput);

        layout.putConstraint(SpringLayout.WEST, filTime, 10, SpringLayout.WEST, searchPanel);
        layout.putConstraint(SpringLayout.NORTH, filTime, 10, SpringLayout.SOUTH, filDiet);
        layout.putConstraint(SpringLayout.WEST, filTimeInput, 5, SpringLayout.EAST, filTime);
        layout.putConstraint(SpringLayout.NORTH, filTimeInput, 10, SpringLayout.SOUTH, filDietInput);

        layout.putConstraint(SpringLayout.WEST, enter, 10, SpringLayout.WEST, searchPanel);
        layout.putConstraint(SpringLayout.SOUTH, enter, -10, SpringLayout.SOUTH, searchPanel);
        layout.putConstraint(SpringLayout.EAST, enter, -10, SpringLayout.EAST, searchPanel);
    }

    public void configureResult(List<Recipe> lib) {
        resultPanel.removeAll();
        resultPanel = new JPanel();
        resultPanel.setSize(100, 400);
        resultPanel.setBackground(new Color(241, 235, 225));
        resultPanel.setLayout(new GridLayout(2, 2, 10, 10));
        resultPanel.setVisible(true);

        for (Recipe r : lib) {
            JButton card = new JButton(r.getName());
            resultPanel.add(card);
        }
    }
}
