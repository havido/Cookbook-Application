package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class WorkRoom implements Writable {
    private String name;
    private String author;
    private List<Ingredient> ingredients;
    private int time;
    private List<String> steps;

    // EFFECTS: constructs workroom with a name and empty list of stuffs in the recipe
    public WorkRoom(String name) {
        this.name = name;
        this.author = author;
        ingredients = new ArrayList<>();
        this.time = time;
        steps = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getTime() {
        return time;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public int numIngredients() {
        return ingredients.size();
    }

    public void addSteps(String step) {
        steps.add(step);
    }

    public List<String> getSteps() {
        return steps;
    }

    public int numSteps() {
        return steps.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Name", name);
        json.put("Author", author);
        json.put("Ingredients", ingredientsToJson());
    }

    private JSONArray ingredientsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Ingredient i : ingredients) {
            jsonArray.put(i.toJson());
        }
        return jsonArray;
    }
}
