package ui;

import model.RecipeLibrary;

public class RecipeAppContext {
    private RecipeLibrary library;
    private String source;

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
