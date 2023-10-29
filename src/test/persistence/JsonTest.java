package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkRecipe(Recipe recipe, RecipeTag tag, String name, String author, int id, int time) {
        assertEquals(tag, recipe.getTag());
        assertEquals(name, recipe.getName());
        assertEquals(author, recipe.getAuthor());
        assertEquals(id, recipe.getId());
        assertEquals(time, recipe.getTime());
    }

    protected void checkIngredient(Ingredient ingredient, IngredientCategories type, String name) {
        assertEquals(name, ingredient.getName());
        assertEquals(type, ingredient.getCategory());
    }
}
