package model;

import java.util.*;

public class Recipe {
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
     *          recipe's author is "Anonymous", otherwise recipe's author is set to author.
     */
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

//        Iterator<Ingredient> iterator = ingredients.iterator();
//        while (iterator.hasNext()) {
//            Ingredient ingredient = iterator.next();
//            if (iterator.next().getCategory() == Categories.MEAT) {
//                dietaryRequirements.remove("vegetarian");
//            }
//            if (iterator.next().getCategory() == Categories.GLUTEN) {
//                dietaryRequirements.remove("gluten-free");
//            }
//            if (iterator.next().getCategory() == Categories.LACTOSE) {
//                dietaryRequirements.remove("lactose-free");
//            }
//        }

        for (Ingredient ingredient : ingredients) {
            if (ingredient.getCategory() == Categories.MEAT) {
                dietaryRequirements.remove("vegetarian");
            }
            if (ingredient.getCategory() == Categories.GLUTEN) {
                dietaryRequirements.remove("gluten-free");
            }
            if (ingredient.getCategory() == Categories.LACTOSE) {
                dietaryRequirements.remove("lactose-free");
            }
        }
        steps = new ArrayList<String>();
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

        return "ID: " + getId() + "\nRecipe: " + getName() + "\nAuthor: " + getAuthor()
                + "\nTotal time: " + time + "\n\nDietary notes: " + getDietaryRequirements()
                + "\nIngredient: " + getIngredients() + "\n\nInstructions: "
                + printSteps;
    }
}
