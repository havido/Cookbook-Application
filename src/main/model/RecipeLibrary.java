package model;

import java.util.ArrayList;
import java.util.List;

public class RecipeLibrary {
    private List<Recipe> library;

    public RecipeLibrary() {
        library = new ArrayList<Recipe>();
    }

    public List<Recipe> getLibrary() {
        return library;
    }

    /*
     * EFFECTS: filter the library by the name of each recipe, and add the filtered recipes to a new ArrayList
     */
    public List<Recipe> filterByName(String keyword) {
        List<Recipe> filteredName = new ArrayList<Recipe>();
        for (Recipe recipe : library) {
            if (recipe.getName().toLowerCase().contains(keyword.toLowerCase())) {
                filteredName.add(recipe);
            }
        }
        return filteredName;
    }

    /*
     * EFFECTS: filter the library by the ingredients of each recipe, and add the filtered recipes to a new ArrayList
     */
    public List<Recipe> filterByIngredients(String keyword) {
        List<Recipe> filteredIngredients = new ArrayList<Recipe>();
        for (Recipe recipe : library) {

            for (Ingredient ingredient : recipe.getIngredients()) {
                if (ingredient.getName().toLowerCase().equals(keyword.toLowerCase())) {
                    filteredIngredients.add(recipe);
                    break;
                }
            }
        }
        return filteredIngredients;
    }

    /*
     * EFFECTS: filter the library by the dietary requirements of each recipe,
     *          and add the filtered recipes to a new ArrayList
     */
    public List<Recipe> filterByDiet(String keyword) {
        List<Recipe> filteredDiet = new ArrayList<Recipe>();
        for (Recipe recipe : library) {
            for (String requirement : recipe.getDietaryRequirements()) {
                if (requirement.toLowerCase().equals(keyword.toLowerCase())) {
                    filteredDiet.add(recipe);
                    break;
                }
            }
        }
        return filteredDiet;
    }

    /*
     * EFFECTS: filter the library by the time taken to make of each recipe,
     *          and add the filtered recipes to a new ArrayList
     */
    public List<Recipe> filterByTime(int maxTime) {
        List<Recipe> filteredTime = new ArrayList<Recipe>();
        for (Recipe recipe : library) {
            if (recipe.getTime() <= maxTime) {
                filteredTime.add(recipe);
            }
        }
        return filteredTime;
    }
}
