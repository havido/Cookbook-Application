package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

public class Recipe implements Writable {
    private String name;
    private static int nextRecipeId = 1;
    private int id;
    private String author;
    private Set<Ingredient> ingredients = new HashSet<Ingredient>();
    private Set<String> dietaryRequirements = new HashSet<String>();
    private int time; // in minutes
    private List<String> steps;

    /*
     * REQUIRES: name has a non-zero length
     * EFFECTS: recipe's name is set to name; recipe id is a positive integer not assigned
     *          to any other recipe; if author is empty or contains only blankspaces, then
     *          recipe's author is "Anonymous", otherwise recipe's author is set to author;
     *          recipe's ingredients is set to ingredients, recipe's dietary requirements
     *          is calculated based on the category assigned with each ingredient; and an
     *          arraylist to store instructions is initialised.
     */
    @SuppressWarnings("methodlength")
    public Recipe(String name, String author, Set<Ingredient> ingredients, int time) {
        this.name = name;
        if (author.isBlank()) {
            this.author = "Anonymous";
        } else {
            this.author = author;
        }
        id = nextRecipeId++;
        this.ingredients = ingredients;
        this.time = time;
        dietaryRequirements.add("lactose-free");
        dietaryRequirements.add("gluten-free");
        dietaryRequirements.add("vegetarian");

        for (Ingredient ingredient : ingredients) {
            extracted(ingredient);
        }
        steps = new ArrayList<String>();
    }

    private void extracted(Ingredient ingredient) {
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

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public Set<String> getDietaryRequirements() {
        return dietaryRequirements;
    }

    public int getTime() {
        return time;
    }

    public List<String> getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        String printSteps = "";
        for (int i = 1; i <= steps.size(); i++) {
            printSteps += "\n" + "Step " + i + ": " + steps.get(i - 1);
        }

        // Putting the Sets to sorted list so that the toString() method always print out similar statements
        List<String> sortedIngredients = new ArrayList<>();
        for (Ingredient i : ingredients) {
            sortedIngredients.add(i.getName());
        }
        Collections.sort(sortedIngredients);

        List<String> sortedDiets = new ArrayList<>();
        sortedDiets.addAll(dietaryRequirements);
        Collections.sort(sortedDiets);

        return "ID: " + getId() + "\nRecipe: " + getName() + "\nAuthor: " + getAuthor()
                + "\nTotal time: " + time + "\n\nDietary notes: " + sortedDiets
                + "\nIngredients: " + sortedIngredients + "\n\nInstructions: "
                + printSteps;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("id", id);
        json.put("author", author);
        json.put("ingredients", ingredients);
        json.put("dietary restrictions", dietaryRequirements);
        json.put("time", time);
        json.put("steps", steps);
        return json;
    }
}
