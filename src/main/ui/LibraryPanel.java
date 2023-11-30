package ui;

import model.RecipeLibrary;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class LibraryPanel extends JPanel {
    private RecipeAppContext context;
    private SearchAddPanel saPanel;
    private JLabel prompt;
    private JLabel loadStatus;
    private JLabel saveStatus;
    private JButton libraryButton;
    private JButton libUserButton;
    private JButton saveButton;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private RecipeLibrary library;

    public LibraryPanel(RecipeAppContext context, SearchAddPanel sa) {
        this.context = context;
        this.saPanel = sa;
        library = context.getLibrary();
        setBackground(new Color(241, 235, 225));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 700));
        setVisible(true);

        prompt = new JLabel("Choose a library to load from: ");
        add(prompt);

        loadStatus = new JLabel();
        saveStatus = new JLabel();
        libraryButton = new JButton("Default");
        libUserButton = new JButton("User's library");
        saveButton = new JButton("Save to current library");

        library.toString();
        loadFromDefault();
        loadFromUserLib();

        // this save button should only be put at the mainPanel > addNewRecipe or editRecipe
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    jsonWriter = new JsonWriter(jsonReader.getSource());
                    jsonWriter.open();
                    jsonWriter.write(library);
                    jsonWriter.close();
                    saveStatus.setText("All changes saved to " + jsonReader.getSource());
                } catch (FileNotFoundException e) {
                    saveStatus.setText("Error saving library to " + jsonReader.getSource());
                    e.printStackTrace();
                }
            }
        });

        add(libraryButton);
        add(libUserButton);
        add(loadStatus);
        add(Box.createVerticalGlue());
        add(saveStatus);
        add(saveButton);
    }

    private void loadFromUserLib() {
        libUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    jsonReader = new JsonReader("./data/libUser.json");
                    RecipeLibrary temp = jsonReader.read();
                    library.updateLibrary(temp);
                    loadStatus.setText("Currently using the user's library");
                    saPanel.appear();
                } catch (Exception e) {
                    loadStatus.setText("Error loading library from the user's library");
                    e.printStackTrace();
                }
            }
        });
        library.toString();
    }

    private void loadFromDefault() {
        libraryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    jsonReader = new JsonReader("./data/library.json");
                    RecipeLibrary temp = jsonReader.read();
                    library.updateLibrary(temp);
                    loadStatus.setText("Currently using the default library");
                    saPanel.appear();
                } catch (Exception e) {
                    loadStatus.setText("Error loading library from the default library");
                    e.printStackTrace();
                }
            }
        });
        library.toString();
    }
}
