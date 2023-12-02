package ui;

import model.Event;
import model.EventLog;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

// Represents the main window in which the cookbook application is run
public class RecipeAppGUI extends JFrame {

    private RecipeAppContext context;
    private LibraryPanel libMenu;
    private SearchAddPanel saMenu;
    private MainPanel bigPanel;

    // Constructs main window
    // EFFECTS: sets up window in which the Recipe App will be run
    public RecipeAppGUI() throws FileNotFoundException {
        super("Virtual Cookbook");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        writeEventLog();
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

    private void writeEventLog() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    EventLog eventLog = EventLog.getInstance();
                    for (Event next : eventLog) {
                        System.out.print(next.toString());
                        System.out.println("\n\n");
                    }
                } catch (Exception le) {
                    System.out.println("Error while trying to print event logs to console");
                }
                System.exit(0);
            }
        });
    }
}
