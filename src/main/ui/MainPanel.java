package ui;

import javax.swing.*;
import java.awt.*;

// Represents the last panel after user clicks a button in the previous panel. This panel acts as a carry
// panel for search panel or add panel, depending on the user's choice
public class MainPanel extends JPanel {
    private RecipeAppContext context;
    private AddRecipePanel addPanel;
    private SearchRecipePanel searchPanel;

    // EFFECTS: sets the background colour and draws the initial labels and buttons
    public MainPanel(RecipeAppContext context) {
        this.context = context;
        setBackground(new Color(241,235,225));
        setVisible(true);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    // EFFECTS: clear the entire panel and call add recipe panel
    public void addRecipePanel() {
        removeAll();
        if (addPanel == null) {
            addPanel = new AddRecipePanel(context);
        }
        addPanel.setVisible(true);
        add(addPanel);
        revalidate();
        repaint();
    }

    // EFFECTS: clear the entire panel and call search recipe panel
    public void searchRecipePanel() {
        removeAll();
        if (searchPanel == null) {
            searchPanel = new SearchRecipePanel(context);
        }
        searchPanel.setVisible(true);
        add(searchPanel);
        revalidate();
        repaint();
    }
}
