package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the second panel after user have chosen data source, to let them choose what action to do next
public class SearchAddPanel extends JPanel {
    private MainPanel otherPanel;
    private JLabel prompt;
    private JButton searchButton;
    private JButton addButton;

    // EFFECTS: sets the background colour and draws the initial labels and buttons
    public SearchAddPanel(MainPanel otherPanel) {
        this.otherPanel = otherPanel;
        setBackground(new Color(241, 235, 225));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 700));
        setVisible(true);

        prompt = new JLabel("What do you want to do today?");
        add(prompt);

        searchButton = new JButton("Search recipes");
        addButton = new JButton("Add a new recipe");
        configureButton(searchButton);
        configureButton(addButton);

        addActionsToButton(otherPanel);

        add(searchButton);
        add(addButton);
        add(Box.createVerticalGlue());
    }

    // MODIFIES: this
    // EFFECTS: add actions to the search/load buttons
    private void addActionsToButton(MainPanel otherPanel) {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                otherPanel.searchRecipePanel();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                otherPanel.addRecipePanel();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: set visual aspects for the buttons
    public void configureButton(JButton b) {
        b.setBackground((new Color(192,207,178)));
    }

    // MODIFIES: this
    // EFFECTS: make the searchAddPanel disappear
    public void disappear() {
        setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: make the searchAddPanel appear
    public void appear() {
        setVisible(true);
    }
}

