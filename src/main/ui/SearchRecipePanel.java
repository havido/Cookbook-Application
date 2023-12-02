package ui;

import layout.SpringUtilities;
import model.Ingredient;
import model.Recipe;
import model.RecipeLibrary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SearchRecipePanel extends JPanel {
    private final String ingPrompt = "Enter ingredients, each separated by a comma";
    private RecipeLibrary library;
    private JPanel searchPanel;
    private JPanel resultPanel;
    private JButton enter;

    public SearchRecipePanel(RecipeAppContext context) {
        library = context.getLibrary();
        setBackground(new Color(241, 235, 225));
        setVisible(true);
        setLayout(new BorderLayout());

        searchPanel = new JPanel(new SpringLayout());
        resultPanel = new JPanel();
        resultPanel.setBackground(new Color(241,235,225));
        add(BorderLayout.NORTH, searchPanel);
        add(BorderLayout.CENTER, new JScrollPane(resultPanel));
        configureSearch();
    }

    public void configureSearch() {
        searchPanel.setBackground(new Color(241, 235, 225));

        String[] labels = {"Filter by name: ", "Filter by ingredients: ",
                "Filter by dietary requirements: ", "Filter by time (minutes): "};
        int numPairs = labels.length;
        ArrayList<Object> inputFields = new ArrayList<>();

        createFilterMenu(labels, numPairs, inputFields);

        SpringUtilities.makeCompactGrid(searchPanel, numPairs, 2, 6, 6, 6, 6);
        searchPanel.setOpaque(true);

        createEnterButton(inputFields);
        searchPanel.add(enter);
    }

    private void createFilterMenu(String[] labels, int numPairs, ArrayList<Object> inputFields) {
        for (int i = 0; i < numPairs; i++) {
            JLabel b = new JLabel(labels[i], JLabel.TRAILING);
            searchPanel.add(b);

            if (labels[i].equals("Filter by dietary requirements: ")) {
                String[] diets = {"None", "Lactose-free", "Gluten-free", "Vegetarian"};
                JComboBox<String> input = new JComboBox<>(diets);
                b.setLabelFor(input);
                searchPanel.add(input);
                inputFields.add(input);
            } else if (labels[i].equals("Filter by time (minutes): ")) {
                JSlider input = new JSlider(JSlider.HORIZONTAL, 0, 1000, 1000);
                input.setMajorTickSpacing(100);
                input.setMinorTickSpacing(10);
                input.setPaintTicks(true);
                input.setPaintLabels(true);
                b.setLabelFor(input);
                searchPanel.add(input);
                inputFields.add(input);
            } else {
                JTextField input = new JTextField();
                b.setLabelFor(input);
                searchPanel.add(input);
                inputFields.add(input);
            }
        }
    }

    private void createEnterButton(ArrayList<Object> inputFields) {
        enter = new JButton("Search!");
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Recipe> filteredRecipes = new ArrayList<>(library.getLibrary());
                List<Recipe> temp;

                temp = library.filterByName(((JTextField) inputFields.get(0)).getText());
                filteredRecipes.retainAll(temp);

                temp = library.filterByMultipleIng(((JTextField) inputFields.get(1)).getText());
                filteredRecipes.retainAll(temp);

                temp = library.filterByDiet(((JComboBox<String>) inputFields.get(2)).getSelectedItem().toString());
                filteredRecipes.retainAll(temp);

                temp = library.filterByTime(((JSlider) inputFields.get(3)).getValue());
                filteredRecipes.retainAll(temp);

                configureResult(filteredRecipes);
            }
        });
    }

    public void configureResult(List<Recipe> lib) {
        resultPanel.removeAll();

        if (lib.isEmpty()) {
            JLabel empty = new JLabel("No results found.");
            resultPanel.add(empty);
        } else {
            resultPanel.setLayout(new GridLayout(0,2,10,10));
            for (Recipe r : lib) {
                JButton card = new JButton(r.getName());
                card.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showButtonInfo(r);
                    }
                });
                resultPanel.add(card);
            }
        }

        resultPanel.revalidate();
        resultPanel.repaint();
        System.out.println("hehe");
    }

    public void showButtonInfo(Recipe r) {
        JFrame infoFrame = new JFrame("Recipe");
        JPanel infoPanel = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel();
        ImageIcon image = new ImageIcon("./images/" + r.getId() + ".jpg");
        if (image.getImage() == null) {
            imageLabel.setText("No image yet");
        } else {
            imageLabel.setIcon(image);
        }
        JTextArea info = new JTextArea(r.toString());
        info.setEditable(false);

        infoPanel.add(info, BorderLayout.CENTER);
        infoPanel.add(imageLabel, BorderLayout.WEST);

        infoFrame.getContentPane().add(infoPanel);
        infoFrame.setPreferredSize(new Dimension(500,500));
        infoFrame.setLocationRelativeTo(null);

        infoFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                infoFrame.dispose();
            }
        });

        infoFrame.setVisible(true);
    }
}
