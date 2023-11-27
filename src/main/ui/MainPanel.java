package ui;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
//    private RecipeApp app;
    private SearchAddPanel saPanel;
    private AddRecipePanel addPanel;
    private SearchRecipePanel searchPanel;

    public MainPanel(SearchAddPanel saPanel) {
//        this.app = app;
        this.saPanel = saPanel;
        setBackground(new Color(241,235,225));
        setVisible(true);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void addRecipePanel() {
        removeAll();
        addPanel.setVisible(true);
    }

    public void searchRecipePanel() {
        removeAll();
        searchPanel.setVisible(true);
    }
}
