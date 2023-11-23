package ui;

import model.Recipe;

import javax.swing.*;
import java.awt.*;

public class LibraryPanel extends JPanel {
    private RecipeApp app;
    private JLabel prompt;

    public LibraryPanel(RecipeApp app) {
        this.app = app;
        setBackground(new Color(241, 235, 225));
        prompt = new JLabel("Choose a library to load from");
        prompt.setPreferredSize(new Dimension(100, 60));
        add(prompt);
        addButton();
    }

    public void addButton() {
        app.loadLibrary();
    }
}
