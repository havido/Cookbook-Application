package model;

import java.util.ArrayList;
import java.util.List;

// Represents a library having 3 lists of recipe: an overall list, a list of officially added recipes,
// and a list of drafts
// NOTE: All differences from Phase 2 are noted
public class RecipeLibrary {
    private List<Recipe> allRecipes;
    private List<Recipe> library;
    private List<Recipe> drafts;

    // EFFECTS: constructs a library with 3 lists of recipes
    public RecipeLibrary() {
        library = new ArrayList<Recipe>();
        drafts = new ArrayList<Recipe>();
        allRecipes = new ArrayList<Recipe>();
    }

    public List<Recipe> getLibrary() {
        return library;
    }

    public List<Recipe> getDrafts() {
        return drafts;
    }

    public List<Recipe> getAllRecipes() {
        return allRecipes;
    }

    // MODIFIES: this
    // EFFECTS: add a new recipe to a suitable list based on its tag & assign an ID number to the recipe
    public void addRecipeToLibrary(Recipe recipe) {
        allRecipes.add(recipe);
        recipe.setId(allRecipes.indexOf(recipe) + 1);
        if (recipe.getTag() == RecipeTag.DEFAULT) {
            library.add(recipe);
        } else {
            drafts.add(recipe);
        }
    }

    // EFFECTS: filter the library by the name of each recipe, and add the filtered recipes to a new ArrayList
    public List<Recipe> filterByName(String keyword) {
        if (keyword.isBlank()) {
            return library;
        } // Diff phase 2
        List<Recipe> filteredName = new ArrayList<Recipe>();
        for (Recipe recipe : library) {
            if (recipe.getName().toLowerCase().contains(keyword.toLowerCase())) {
                filteredName.add(recipe);
            }
        }
        return filteredName;
    }

    // EFFECTS: filter the library by the ingredients of each recipe, and add the filtered recipes to a new ArrayList
    public List<Recipe> filterByIngredients(String keyword) {
        if (keyword.isBlank()) {
            return library;
        } // Diff phase 2
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

    // Diff phase 2
    public List<Recipe> filterByMultipleIng(String s) {
        if (s.isBlank()) {
            return library;
        }
        List<Recipe> tempLib = new ArrayList<>(library);
        String[] ing = s.split(",");
        for (String ingredient : ing) {
            List<Recipe> filteredList = filterByIngredients(ingredient.trim());
            tempLib.retainAll(filteredList);
        }
        return tempLib;
    }

    // EFFECTS: filter the library by the dietary requirements of each recipe,
    // and add the filtered recipes to a new ArrayList
    public List<Recipe> filterByDiet(String keyword) {
        if (keyword.isBlank() || keyword.toLowerCase().equals("none")) {
            return library;
        } // Diff phase 2
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

    // EFFECTS: filter the library by the time taken to make of each recipe,
    // and add the filtered recipes to a new ArrayList
    public List<Recipe> filterByTime(int maxTime) {
        List<Recipe> filteredTime = new ArrayList<Recipe>();
        for (Recipe recipe : library) {
            if (recipe.getTime() <= maxTime) {
                filteredTime.add(recipe);
            }
        }
        return filteredTime;
    }

    // EFFECTS: return the size of the overall list
    public int numRecipesInLibrary() {
        return library.size();
    }

    // EFFECTS: return the size of the draft list
    public int numDrafts() {
        return drafts.size();
    }

    // EFFECTS: return the size of the official list
    public int numTotal() {
        return allRecipes.size();
    }

    // Diff phase 2
    public void updateLibrary(RecipeLibrary lib) {
        allRecipes.clear();
        drafts.clear();
        library.clear();

        for (Recipe r : lib.getAllRecipes()) {
            addRecipeToLibrary(r);
        }
    }
}
