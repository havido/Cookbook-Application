package ui;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private RecipeAppContext context;
    private AddRecipePanel addPanel;
    private SearchRecipePanel searchPanel;

    public MainPanel(RecipeAppContext context) {
        this.context = context;
        setBackground(new Color(241,235,225));
        setVisible(true);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

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
