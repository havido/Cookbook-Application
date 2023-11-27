package ui;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class RecipeAppGUI extends JFrame {

//    private RecipeApp app;
    private LibraryPanel libMenu;
    private SearchAddPanel saMenu;  // Changed from SearchAddPanel
    private MainPanel bigPanel;

    // Constructs main window
    // EFFECTS: sets up window in which the Recipe App will be run
    public RecipeAppGUI() throws FileNotFoundException {
        super("Virtual Cookbook");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        app = new RecipeApp();
        setLocationRelativeTo(null);
        setBackground(Color.pink);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        libMenu = new LibraryPanel();
        saMenu = new SearchAddPanel(bigPanel);
        bigPanel = new MainPanel(saMenu);

        add(libMenu);
        add(saMenu);
        add(bigPanel);

        pack();
        setVisible(true);
    }
}
