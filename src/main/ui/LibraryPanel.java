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
    private RecipeApp app;
    private MainPanel otherPanel;
    private JLabel prompt;
    private JLabel loadStatus;
    private JLabel saveStatus;
    private JButton libraryButton;
    private JButton libUserButton;
    private JButton saveButton;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private static RecipeLibrary library;

    public LibraryPanel(RecipeApp app) {
        this.app = app;
        this.otherPanel = otherPanel;
        setBackground(new Color(241, 235, 225));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setSize(100,400);
        setVisible(true);

        prompt = new JLabel("Choose a library to load from: ");
        prompt.setPreferredSize(new Dimension(100, 60));
        add(prompt);

        loadStatus = new JLabel();
        saveStatus = new JLabel();
        libraryButton = new JButton("Default");
        libUserButton = new JButton("User's library");
        saveButton = new JButton("Save to current library");

        libraryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    library = new RecipeLibrary();
                    jsonReader = new JsonReader("./data/library.json");
                    library = jsonReader.read();
                    loadStatus.setText("Currently using the default library");
                } catch (Exception e) {
                    loadStatus.setText("Error loading library from the default library");
                }
            }
        });

        libUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    library = new RecipeLibrary();
                    jsonReader = new JsonReader("./data/libUser.json");
                    library = jsonReader.read();
                    loadStatus.setText("Currently using the user's library");
                } catch (Exception e) {
                    loadStatus.setText("Error loading library from the user's library");
                }
            }
        });

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
                }
            }
        });

        add(libraryButton);
        add(libUserButton);
        add(loadStatus);
        add(Box.createVerticalGlue());
        add(saveStatus);
        add(saveButton);

        otherPanel.setLibrary(library);
    }

//    public void configureButton(JButton button) {
//        button.setBackground(new Color());
//        button.set
//    }

    public static RecipeLibrary getLibrary() {
        return library;
    }
}
