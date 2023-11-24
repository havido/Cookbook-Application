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
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setSize(100,400);
        setVisible(true);

        prompt = new JLabel("What do you want to do today?");
        prompt.setPreferredSize(new Dimension(100, 60));
        add(prompt);

        searchButton = new JButton("Search recipes");
        addButton = new JButton("Add a new recipe");
//        searchButton.setBackground(new Color(192,207,178));
//        searchButton.setSize(new Dimension(100, 60));
//        addButton.setBackground(new Color(192,207,178));
//        addButton.setSize(new Dimension(100, 60));

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActiveEvent ae) {
                System.out.println("hehe");
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActiveEvent ae) {
                System.out.println("hehe");
            }
        });

        add(searchButton);
        add(addButton);
    }
}

