package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Represents a recipe having a tag, a name, a unique ID, an author's name, a list of ingredients,
// a list of dietary restrictions, time consumption, and a list of steps
public class Recipe implements Writable {
    private RecipeTag tag;
    private String name;
    private int id;
    private String author;
    private List<Ingredient> ingredients;
    private List<String> dietaryRequirements;
    private int time; // in minutes
    private List<String> steps;

    /*
     * REQUIRES: name has a non-zero length
     * EFFECTS: recipe's name is set to name; recipe id is -1 to indicate that it's not saved;
     *          if author is empty or contains only blankspaces, then
     *          recipe's author is "Anonymous", otherwise recipe's author is set to author;
     *          recipe's ingredients is set to ingredients, recipe's dietary requirements
     *          is calculated based on the category assigned with each ingredient; and an
     *          arraylist to store instructions is initialised.
     */
    public Recipe(String name, String author, RecipeTag tag) {
        this.name = name;
        if (author.isBlank()) {
            this.author = "Anonymous";
        } else {
            this.author = author;
        }
        this.tag = tag;
        id = -1;
        // ID -1 means that the recipe is not saved yet

        time = 0;
        ingredients = new ArrayList<Ingredient>();
        dietaryRequirements = new ArrayList<String>();
        dietaryRequirements.add("lactose-free");
        dietaryRequirements.add("gluten-free");
        dietaryRequirements.add("vegetarian");

        steps = new ArrayList<String>();
    }

    // MODIFIES: this
    // EFFECTS: check ingredient to see if the recipe meets certain dietary restrictions
    private void calculateDietaryRequirements(Ingredient ingredient) {
        if (ingredient.getCategory() == IngredientCategories.MEAT) {
            dietaryRequirements.remove("vegetarian");
        }
        if (ingredient.getCategory() == IngredientCategories.GLUTEN) {
            dietaryRequirements.remove("gluten-free");
        }
        if (ingredient.getCategory() == IngredientCategories.LACTOSE) {
            dietaryRequirements.remove("lactose-free");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public RecipeTag getTag() {
        return tag;
    }

    public void setTag(RecipeTag tag) {
        this.tag = tag;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    // MODIFIES: this
    // EFFECTS: add an ingredient to list and check dietary restrictions
    public void addIngredients(Ingredient ingredient) {
        ingredients.add(ingredient);
        calculateDietaryRequirements(ingredients.get(ingredients.size() - 1));
    }

    public List<String> getDietaryRequirements() {
        return dietaryRequirements;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<String> getSteps() {
        return steps;
    }

    // EFFECTS: check if the recipe has any blank fields
    public boolean checkNotNull() {
        if (!name.isBlank() && !author.isBlank() && time != 0 && ingredients.size() != 0 && steps.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: returns string representation of this recipe
    @Override
    public String toString() {
        String printSteps = "";
        for (int i = 1; i <= steps.size(); i++) {
            printSteps += "\n" + "Step " + i + ": " + steps.get(i - 1);
        }

        List<String> ingName = new ArrayList<>();
        for (Ingredient i : ingredients) {
            ingName.add(i.getName());
        }

        return "ID: " + getId() + "\nRecipe: " + getName() + "\nAuthor: " + getAuthor()
                + "\nTotal time: " + time + "\n\nDietary notes: " + dietaryRequirements
                + "\nIngredients: " + ingName + "\n\nInstructions: "
                + printSteps;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Tag", tag);
        json.put("Name", name);
        json.put("ID", id);
        json.put("Author", author);
        json.put("Ingredients", ingredients);
        json.put("Dietary restrictions", dietaryRequirements);
        json.put("Time", time);
        json.put("Steps", steps);
        return json;
    }
}
