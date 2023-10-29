package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            RecipeLibrary library = new RecipeLibrary();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyLibrary() {
        try {
            RecipeLibrary recipeLibrary = new RecipeLibrary();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLibrary.json");
            writer.open();
            writer.write(recipeLibrary);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLibrary.json");
            recipeLibrary = reader.read();
            assertEquals(0, recipeLibrary.numTotal());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLibrary() {
        try {
            RecipeLibrary recipeLibrary = new RecipeLibrary();
            Recipe recipe1 = new Recipe("Name1", "Author1", RecipeTag.DEFAULT);
            recipe1.setTime(10);
            recipe1.addIngredients(new Ingredient("ing1", IngredientCategories.NONE));
            recipe1.addIngredients(new Ingredient("ing2", IngredientCategories.MEAT));
            recipe1.getSteps().add("test1");
            recipe1.getSteps().add("test2");

            Recipe recipe2 = new Recipe("Name2", "Author2", RecipeTag.DRAFT);
            recipe2.setTime(20);
            recipe2.addIngredients(new Ingredient("inga", IngredientCategories.LACTOSE));
            recipe2.addIngredients(new Ingredient("ingb", IngredientCategories.GLUTEN));
            recipe2.getSteps().add("test a");
            recipe2.getSteps().add("test b");

            recipeLibrary.addRecipeToLibrary(recipe1);
            recipeLibrary.addRecipeToLibrary(recipe2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLibrary.json");
            writer.open();
            writer.write(recipeLibrary);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralLibrary.json");
            recipeLibrary = reader.read();

            // Check library's size
            assertEquals(2, recipeLibrary.getAllRecipes().size());
            assertEquals(1, recipeLibrary.getDrafts().size());
            assertEquals(1, recipeLibrary.getLibrary().size());

            // Check ingredient list's size and whether it contains the correct ingredients
            assertEquals(2, recipeLibrary.getLibrary().get(0).getIngredients().size());
            assertEquals(2, recipeLibrary.getDrafts().get(0).getIngredients().size());
            checkIngredient(recipeLibrary.getAllRecipes().get(0).getIngredients().get(0), IngredientCategories.NONE, "ing1");
            checkIngredient(recipeLibrary.getAllRecipes().get(1).getIngredients().get(1), IngredientCategories.GLUTEN, "ingb");

            // Check 5 fields of recipe
            checkRecipe(recipeLibrary.getLibrary().get(0), RecipeTag.DEFAULT, "Name1", "Author1", 1, 10);
            checkRecipe(recipeLibrary.getDrafts().get(0), RecipeTag.DRAFT, "Name2", "Author2", 2, 20);

            // Check dietary requirements
            assertTrue(recipeLibrary.getLibrary().get(0).getDietaryRequirements().contains("gluten-free"));
            assertFalse(recipeLibrary.getLibrary().get(0).getDietaryRequirements().contains("vegetarian"));
            assertTrue(recipeLibrary.getDrafts().get(0).getDietaryRequirements().contains("vegetarian"));
            assertFalse(recipeLibrary.getDrafts().get(0).getDietaryRequirements().contains("lactose-free"));

            // Check steps
            assertTrue(recipeLibrary.getLibrary().get(0).getSteps().contains("test1"));
            assertFalse(recipeLibrary.getLibrary().get(0).getSteps().contains("test a"));
            assertTrue(recipeLibrary.getDrafts().get(0).getSteps().contains("test b"));
            assertFalse(recipeLibrary.getDrafts().get(0).getSteps().contains("test2"));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
