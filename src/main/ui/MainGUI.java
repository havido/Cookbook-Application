package ui;

import javax.swing.*;
import java.io.FileNotFoundException;

public class MainGUI {
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                        new RecipeAppGUI();
                    } catch (FileNotFoundException e) {
                        System.out.println("Unable to run application: file not found");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
