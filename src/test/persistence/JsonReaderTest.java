package persistence;

import model.Recipe;
import model.RecipeLibrary;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            RecipeLibrary library = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
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
            assertEquals("Name1", library.getAllRecipes().get(0).getName());
            assertEquals("Name2", library.getAllRecipes().get(1).getName());
            assertEquals("Name2", library.getDrafts().get(0).getName());
            // name of items in each list
            assertEquals(2, library.getAllRecipes().size());
            assertEquals(1, library.getLibrary().size());
            assertEquals(1, library.getDrafts().size());
            // check id
//            checkThingy("needle", Category.STITCHING, thingies.get(0));
//            checkThingy("saw", Category.WOODWORK, thingies.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
