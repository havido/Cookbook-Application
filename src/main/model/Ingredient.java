package model;

import org.json.JSONObject;
import persistence.Writable;

public class Ingredient implements Writable {
    private String name; // lowercase
    private IngredientCategories category;

    /*
     * EFFECTS: Set this.name to name, and this.category to category
     */
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
        json.put("Ingredient", name);
        json.put("Type", category);
        return json;
    }
}
