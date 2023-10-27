package persistence;

import model.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkRecipe(Recipe recipe, RecipeTag tag, String name, String author, int id, ArrayList ingredients, ArrayList dietary, int time, ArrayList steps) {
        assertEquals(tag, recipe.getTag());
        assertEquals(name, recipe.getName());
        assertEquals(author, recipe.getAuthor());
        assertEquals(id, recipe.getId());
        assertEquals(ingredients, recipe.getIngredients());
        assertEquals(time, recipe.getTime());
        assertEquals(dietary, recipe.getDietaryRequirements());
        assertEquals(steps, recipe.getSteps());
    }

    protected void checkIngredient(Ingredient ingredient, IngredientCategories type, String name) {
        assertEquals(name, ingredient.getName());
        assertEquals(type, ingredient.getCategory());
    }
}
