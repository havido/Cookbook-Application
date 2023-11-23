package ui;

import javax.swing.*;
import java.awt.*;

import static javax.swing.text.StyleConstants.setBackground;

public class MainPanel {
    private RecipeApp app;
    private SearchAddPanel saPanel;
    private JTextField searchBar;
    private JButton search;

    public MainPanel(RecipeApp app, SearchAddPanel saPanel, LibraryPanel libPanel) {
        this.app = app;
        this.saPanel = saPanel;
        setBackground(a,new Color(241,235,225));
        searchBar = new JTextField();
    }

    private void addRecipePanel() {
        JLabel name = new JLabel("Name of recipe: ");
        JTextField nameInput = new JTextField();
        JLabel author = new JLabel("Name of author: ");
        JTextField authorInput = new JTextField();
        JLabel time = new JLabel("Total time needed: ");
        JTextField timeInput = new JTextField();
        JLabel ing = new JLabel("Add an ingredient: ");
        JTextField ingInput = new JTextField();
        JComboBox dietChoice = new JComboBox();
        JButton addIng = new JButton("Add a new ingredient... +");

        JLabel step = new JLabel("Add a new step: ");
        JTextArea stepInput = new JTextArea();
        JButton addStep = new JButton("Add a new step... +");
    }

    private void searchRecipePanel() {
        JLabel filterPrompt = new JLabel("Filter by: ");
        JButton filterName = new JButton("Name");
        JButton filterIng = new JButton("Ingredients");
        JButton filterDiet = new JButton("Dietary requirements");
        JButton filterTime = new JButton("Time needed");
    }

    private void filterNamePanel() {
        JLabel searchBox = new JLabel("Search here: ");
        JTextField searchBar = new JTextField();
        JButton search = new JButton("Search");
    }

    private void filterIngPanel() {
        JLabel searchBox = new JLabel("Search here: ");
        JTextField searchBar = new JTextField();
        JButton search = new JButton("Search");
    }

    private void filterDietPanel() {
        JLabel searchBox = new JLabel("Search here: ");
        JTextField searchBar = new JTextField();
        JButton search = new JButton("Search");
    }

    private void filterTimePanel() {
        JLabel searchBox = new JLabel("Search here: ");
        JTextField searchBar = new JTextField();
        JButton search = new JButton("Search");
    }
}
