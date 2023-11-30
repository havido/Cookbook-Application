package ui;

import model.RecipeLibrary;

public class RecipeAppContext {
    private RecipeLibrary library;

    public RecipeAppContext() {
        this.library = new RecipeLibrary();
    }

    public RecipeLibrary getLibrary() {
        return library;
    }
}
