package ui;

import model.RecipeLibrary;

// A class to store the library loaded from a specific data source to be used throughout the application
public class RecipeAppContext {
    private RecipeLibrary library;
    private String source;

    // EFFECTS: construct an object with a library
    public RecipeAppContext() {
        this.library = new RecipeLibrary();
    }

    public RecipeLibrary getLibrary() {
        return library;
    }

    public void setSource(String src) {
        source = src;
    }

    public String getSource() {
        return source;
    }
}
