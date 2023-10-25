package model;

import java.util.ArrayList;
import java.util.List;

public class DraftsLibrary {
    private List<Recipe> library;

    public DraftsLibrary() {
        library = new ArrayList<Recipe>();
    }

    public List<Recipe> getLibrary() {
        return library;
    }
}
