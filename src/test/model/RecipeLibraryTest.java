package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecipeLibraryTest {
    private RecipeLibrary recipeLibrary;
    private Recipe recipe1;
    private Recipe recipe2;
    private Recipe recipe3;

    @BeforeEach
    void runBefore() {
        recipeLibrary = new RecipeLibrary();
        recipe1 = new Recipe("recipe 1", "author 1", RecipeTag.DEFAULT);
        recipe2 = new Recipe("recipe 2", "author 2", RecipeTag.DEFAULT);
        recipe3 = new Recipe("recipe 3", "author 3", RecipeTag.DRAFT);
    }

    @Test
    public void testAddRecipeToLibrary() {
        recipeLibrary.addRecipeToLibrary(recipe1);
        recipeLibrary.addRecipeToLibrary(recipe2);
        recipeLibrary.addRecipeToLibrary(recipe3);

        assertEquals(3, recipeLibrary.numTotal());
        assertEquals(2, recipeLibrary.numRecipesInLibrary());
        assertEquals(1, recipeLibrary.numDrafts());

        // Make sure the recipes are in the correct lists
        assertTrue(recipeLibrary.getLibrary().contains(recipe1));
        assertTrue(recipeLibrary.getLibrary().contains(recipe2));
        assertFalse(recipeLibrary.getLibrary().contains(recipe3));
        assertTrue(recipeLibrary.getDrafts().contains(recipe3));
        assertFalse(recipeLibrary.getDrafts().contains(recipe1));

        // See if ID are assigned properly
        assertEquals(1, recipe1.getId());
        assertEquals(2, recipe2.getId());
        assertEquals(3, recipe3.getId());
    }

    @Test
    public void testFilterByName() {
        recipeLibrary.addRecipeToLibrary(recipe1);
        recipeLibrary.addRecipeToLibrary(recipe2);

        // Test filtering by name
        assertEquals(2, recipeLibrary.filterByName("").size());
        assertEquals(2, recipeLibrary.filterByName("recipe").size());
        assertEquals(1, recipeLibrary.filterByName("1").size());
        assertTrue(recipeLibrary.filterByName("1").contains(recipe1));
        assertEquals(0, recipeLibrary.filterByName("random").size());
    }

    @Test
    public void testFilterByIngredientsAndByDiet() {
        recipe1.addIngredients(new Ingredient("i1", IngredientCategories.NONE));
        recipe1.addIngredients(new Ingredient("i2", IngredientCategories.LACTOSE));
        recipe2.addIngredients(new Ingredient("i1", IngredientCategories.NONE));
        recipe2.addIngredients(new Ingredient("i3", IngredientCategories.MEAT));

        recipeLibrary.addRecipeToLibrary(recipe1);
        recipeLibrary.addRecipeToLibrary(recipe2);

        // Test filtering by ingredients
        assertEquals(2, recipeLibrary.filterByIngredients("").size());
        assertEquals(2, recipeLibrary.filterByIngredients("i1").size());
        assertEquals(1, recipeLibrary.filterByIngredients("i2").size());
        assertTrue(recipeLibrary.filterByIngredients("i2").contains(recipe1));
        assertTrue(recipeLibrary.filterByMultipleIng("i1,i2").contains(recipe1));
        assertEquals(1, recipeLibrary.filterByIngredients("i3").size());
        assertTrue(recipeLibrary.filterByIngredients("i3").contains(recipe2));
        assertTrue(recipeLibrary.filterByMultipleIng("i1,i3").contains(recipe2));
        assertEquals(0, recipeLibrary.filterByIngredients("carrot").size());

        // Test filtering by dietary requirements
        assertEquals(2, recipeLibrary.filterByDiet("gluten-free").size());
        assertEquals(1, recipeLibrary.filterByDiet("vegetarian").size());
        assertEquals(0, recipeLibrary.filterByDiet("random").size());
    }

    @Test
    public void testFilterByTime() {
        recipe1.setTime(30);
        recipe2.setTime(45);

        recipeLibrary.addRecipeToLibrary(recipe1);
        recipeLibrary.addRecipeToLibrary(recipe2);

        // Test filtering by time
        assertEquals(1, recipeLibrary.filterByTime(40).size());
        assertEquals(2, recipeLibrary.filterByTime(100).size());
        assertEquals(0, recipeLibrary.filterByTime(20).size());
    }

    @Test
    public void testUpdateLibrary() {
        recipeLibrary.addRecipeToLibrary(recipe1);
        recipeLibrary.addRecipeToLibrary(recipe2);
        RecipeLibrary temp = new RecipeLibrary();
        temp.addRecipeToLibrary(recipe3);
        recipeLibrary.updateLibrary(temp);
        assertEquals(temp.getAllRecipes(), recipeLibrary.getAllRecipes());
    }
}