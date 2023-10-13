package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecipeLibrary {
    private List<Recipe> library;

    public RecipeLibrary() {
        library = new ArrayList<Recipe>();
    }

    public List<Recipe> getLibrary() {
        return library;
    }

    public List<Recipe> filterByName(String keyword) {
        List<Recipe> filteredName = new ArrayList<Recipe>();
        for (Recipe recipe : library) {
            if (recipe.getName().toLowerCase().contains(keyword.toLowerCase())) {
                filteredName.add(recipe);
            }
        }
        return filteredName;
    }

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
