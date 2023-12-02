package ui;

import java.io.FileNotFoundException;

// Run the program on the console
public class MainConsole {
    public static void main(String[] args) {
        try {
            new RecipeApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}