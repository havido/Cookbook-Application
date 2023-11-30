package ui;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class RecipeAppGUI extends JFrame {

    private RecipeAppContext context;
    private LibraryPanel libMenu;
    private SearchAddPanel saMenu;
    private MainPanel bigPanel;

    // Constructs main window
    // EFFECTS: sets up window in which the Recipe App will be run
    public RecipeAppGUI() throws FileNotFoundException {
        super("Virtual Cookbook");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
        setPreferredSize(new Dimension(1200,700));

        context = new RecipeAppContext();
        bigPanel = new MainPanel(context);
        saMenu = new SearchAddPanel(bigPanel);
        libMenu = new LibraryPanel(context, saMenu);

        add(libMenu);
        add(saMenu);
        saMenu.disappear();
        add(bigPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
