package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchAddPanel extends JPanel {
    private MainPanel otherPanel;
    private JLabel prompt;
    private JButton searchButton;
    private JButton addButton;

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

        add(searchButton);
        add(addButton);
        add(Box.createVerticalGlue());
    }

    public void configureButton(JButton b) {
        b.setBackground((new Color(192,207,178)));
        b.setSize(new Dimension(100, 60));
    }

    public void disappear() {
        setVisible(false);
    }

    public void appear() {
        setVisible(true);
    }
}

