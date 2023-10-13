package model;

import static org.junit.jupiter.api.Assertions.*;

import model.RecipeLibrary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

class RecipeLibraryTest {
    private RecipeLibrary recipeLibrary;

    @BeforeEach
    void runBefore() {
        recipeLibrary = new RecipeLibrary();

        // Create sample recipes and add them to the library
        Set<Ingredient> ingredients1 = new HashSet<>();
        ingredients1.add(new Ingredient("Chicken", Categories.MEAT));
        recipeLibrary.getLibrary().add(new Recipe("Chicken Curry", "John", ingredients1, 60));

        Set<Ingredient> ingredients2 = new HashSet<>();
        ingredients2.add(new Ingredient("Pasta", Categories.GLUTEN));
        recipeLibrary.getLibrary().add(new Recipe("Pasta Carbonara", "Alice", ingredients2, 30));

        Set<Ingredient> ingredients3 = new HashSet<>();
        ingredients3.add(new Ingredient("Tofu", Categories.NONE));
        ingredients3.add(new Ingredient("Rice", Categories.NONE));
        recipeLibrary.getLibrary().add(new Recipe("Tofu Stir-Fry", "Bob", ingredients3, 45));
    }

    @Test
    public void testFilterByName() {
        List<Recipe> filteredRecipes = recipeLibrary.filterByName("pasta");
        assertEquals(1, filteredRecipes.size());
        assertEquals("Pasta Carbonara", filteredRecipes.get(0).getName());
    }

    @Test
    public void testFilterByIngredients() {
        List<Recipe> filteredRecipes = recipeLibrary.filterByIngredients("chicken");
        assertEquals(1, filteredRecipes.size());
        assertEquals("Chicken Curry", filteredRecipes.get(0).getName());
    }

    @Test
    public void testFilterByDiet() {
        List<Recipe> filteredRecipes = recipeLibrary.filterByDiet("vegetarian");
        assertEquals(2, filteredRecipes.size());
        assertEquals("Pasta Carbonara", filteredRecipes.get(0).getName());
    }

    @Test
    public void testFilterByTime() {
        List<Recipe> filteredRecipes = recipeLibrary.filterByTime(40);
        assertEquals(1, filteredRecipes.size());
        assertEquals("Pasta Carbonara", filteredRecipes.get(0).getName());
    }
}