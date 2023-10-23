package model;

import org.json.JSONObject;

public class Ingredient {
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ingredient", name);
        json.put("type", category);
        return json;
    }
}
