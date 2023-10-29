package persistence;

import model.IngredientCategories;
import model.RecipeLibrary;
import model.RecipeTag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            RecipeLibrary library = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass test
        }
    }

    @Test
    void testReaderEmptyLibrary() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLibrary.json");
        try {
            RecipeLibrary library = reader.read();
            assertEquals(0, library.numTotal());
            assertEquals(0, library.numRecipesInLibrary());
            assertEquals(0, library.numDrafts());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralLibrary() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralLibrary.json");
        try {
            RecipeLibrary library = reader.read();
            // size of lists
            assertEquals(2, library.getAllRecipes().size());
            assertEquals(1, library.getLibrary().size());
            assertEquals(1, library.getDrafts().size());

            // check 5 fields
            // Check ingredient list's size and whether it contains the correct ingredients
            assertEquals(2, library.getLibrary().get(0).getIngredients().size());
            assertEquals(2, library.getDrafts().get(0).getIngredients().size());
            checkIngredient(library.getAllRecipes().get(0).getIngredients().get(0), IngredientCategories.NONE, "ing1");
            checkIngredient(library.getAllRecipes().get(1).getIngredients().get(1), IngredientCategories.GLUTEN, "ingb");

            // Check 5 fields of recipe
            checkRecipe(library.getLibrary().get(0), RecipeTag.DEFAULT, "Name1", "Author1", 1, 10);
            checkRecipe(library.getDrafts().get(0), RecipeTag.DRAFT, "Name2", "Author2", 2, 20);

            // Check dietary requirements
            assertTrue(library.getLibrary().get(0).getDietaryRequirements().contains("gluten-free"));
            assertFalse(library.getLibrary().get(0).getDietaryRequirements().contains("none"));
            assertTrue(library.getDrafts().get(0).getDietaryRequirements().contains("vegetarian"));
            assertFalse(library.getDrafts().get(0).getDietaryRequirements().contains("lactose-free"));

            // Check steps
            assertTrue(library.getLibrary().get(0).getSteps().contains("test1"));
            assertFalse(library.getLibrary().get(0).getSteps().contains("test a"));
            assertTrue(library.getDrafts().get(0).getSteps().contains("test b"));
            assertFalse(library.getDrafts().get(0).getSteps().contains("test2"));


        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
