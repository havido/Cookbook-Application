package ui;

import model.RecipeLibrary;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the first panel when user opens the app, to let user choose which data source they want the
// application to be run upon
public class LibraryPanel extends JPanel {
    private final RecipeAppContext context;
    private final SearchAddPanel saPanel;
    private JLabel loadStatus;
    private JButton libraryButton;
    private JButton libUserButton;
    private JsonReader jsonReader;
    private RecipeLibrary library;

    // EFFECTS: sets the background colour and draws the initial labels and buttons
    public LibraryPanel(RecipeAppContext context, SearchAddPanel sa) {
        this.context = context;
        this.saPanel = sa;
        library = context.getLibrary();
        setBackground(new Color(241, 235, 225));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 700));
        setVisible(true);

        JLabel prompt = new JLabel("Choose a library to load from: ");
        add(prompt);

        loadStatus = new JLabel();
        libraryButton = new JButton("Default");
        libUserButton = new JButton("User's library");

        loadFromDefault();
        loadFromUserLib();

        add(libraryButton);
        add(libUserButton);
        add(loadStatus);
        add(Box.createVerticalGlue());
    }

    // MODIFIES: this
    // EFFECTS: load from libUser.json
    private void loadFromUserLib() {
        libUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    jsonReader = new JsonReader("./data/libUser.json");
                    RecipeLibrary temp = jsonReader.read();
                    library.updateLibrary(temp);
                    loadStatus.setText("Currently using the user's library");
                    context.setSource("./data/libUser.json");
                    saPanel.appear();
                } catch (Exception e) {
                    loadStatus.setText("Error loading library from the user's library");
                    e.printStackTrace();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: load from libUser.json
    private void loadFromDefault() {
        libraryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    jsonReader = new JsonReader("./data/library.json");
                    RecipeLibrary temp = jsonReader.read();
                    library.updateLibrary(temp);
                    loadStatus.setText("Currently using the default library");
                    context.setSource("./data/library.json");
                    saPanel.appear();
                } catch (Exception e) {
                    loadStatus.setText("Error loading library from the default library");
                    e.printStackTrace();
                }
            }
        });
    }
}
