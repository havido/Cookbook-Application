package ui;

import model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class RecipeAppGUI extends JFrame {

    private RecipeApp app;
    private LibraryPanel libMenu;
    private SearchAddPanel saMenu;
    private MainPanel bigPanel;

    // Constructs main window
    // EFFECTS: sets up window in which the Recipe App will be run
    public RecipeAppGUI() throws FileNotFoundException {
        super("Virtual Cookbook");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setSize(300, 200);
        app = new RecipeApp();
        setLocationRelativeTo(null);
        setBackground(Color.pink);
        setLayout(BoxLayout);

        libMenu = new LibraryPanel(app);
        saMenu = new SearchAddPanel(app, bigPanel);
        bigPanel = new MainPanel(app);

        add(libMenu, BoxLayout.LINE_AXIS);
        add(saMenu, BoxLayout.LINE_AXIS);
        add(bigPanel, BoxLayout.LINE_AXIS);

        setVisible(true);
    }

}
