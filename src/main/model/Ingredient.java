package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an ingredient having a name and a category
public class Ingredient implements Writable {
    private String name; // lowercase
    private IngredientCategories category;

    // EFFECTS: construct an ingredient with a name and category
    public Ingredient(String name, IngredientCategories category) {
        this.name = name.toLowerCase();
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public IngredientCategories getCategory() {
        return category;
    }

    // EFFECTS: returns string representation of this ingredient
    public String toString() {
        return name + ", " + category;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("category", category);
        return json;
    }
}
