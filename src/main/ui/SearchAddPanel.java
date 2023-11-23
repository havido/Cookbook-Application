package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SearchAddPanel extends JPanel implements ActionListener {
    private RecipeApp app;
    private MainPanel otherPanel;
    private JLabel prompt;
    private JButton searchButton;
    private JButton addButton;

    public SearchAddPanel(RecipeApp app, MainPanel otherPanel) {
        this.app = app;
        this.otherPanel = otherPanel;
        setBackground(new Color(241, 235, 225));
        prompt = new JLabel("What do you want to do today?");
        prompt.setPreferredSize(new Dimension(100, 60));

        searchButton = new JButton("Search recipes");
        searchButton.setBackground(new Color(192,207,178));
        searchButton.setSize(new Dimension(100, 60));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActiveEvent e) {

            }
        });
        addButton = new JButton("Add a new recipe");
        addButton.setBackground(new Color(192,207,178));
        addButton.setSize(new Dimension(100, 60));

        add(prompt);
        add(Box.createVerticalStrut(10));
        add(searchButton);
        add(Box.createVerticalStrut(10));
        add(addButton);
    }
}

