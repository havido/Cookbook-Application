package persistence;

import model.Ingredient;
import model.Recipe;
import model.RecipeTag;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkLibrary(Recipe recipe, RecipeTag tag, String name, String author, int id, ArrayList ingredients, int time, ArrayList steps) {
        assertEquals(tag, recipe.getTag());
        assertEquals(name, recipe.getName());
        assertEquals(author, recipe.getAuthor());
        assertEquals(id, recipe.getId());
        assertEquals(ingredient.getName(), recipe.getIngredients().getName());
    }

    protected void checkIngredient(Recipe recipe)
}
