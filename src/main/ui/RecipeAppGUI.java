package ui;

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
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        libMenu = new LibraryPanel(app);
        bigPanel = new MainPanel(app, saMenu);  // Move the creation of bigPanel before saMenu
        saMenu = new SearchAddPanel(app, bigPanel);

        add(libMenu, BoxLayout.LINE_AXIS);
        add(saMenu, BoxLayout.LINE_AXIS);
        add(bigPanel, BoxLayout.LINE_AXIS);

        setVisible(true);
    }
}
