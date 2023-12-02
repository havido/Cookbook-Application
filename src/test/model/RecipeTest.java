package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {
    private Recipe recipe;
    private Ingredient i1;
    private Ingredient i2;
    private Ingredient i3;
    private Ingredient i4;

    @BeforeEach
    void runBefore() {
        recipe = new Recipe("name", "author", RecipeTag.DRAFT);
        i1 = new Ingredient("i1", IngredientCategories.LACTOSE);
        i2 = new Ingredient("i2", IngredientCategories.GLUTEN);
        i3 = new Ingredient("i3", IngredientCategories.MEAT);
        i4 = new Ingredient("i4", IngredientCategories.NONE);
    }

    @Test
    void testConstructor() {
        assertEquals("name", recipe.getName());
        assertEquals("author", recipe.getAuthor());
        assertEquals(RecipeTag.DRAFT, recipe.getTag());
        assertEquals(-1, recipe.getId());
        assertEquals(0, recipe.getTime());
        assertNotNull(recipe.getIngredients());
        assertNotNull(recipe.getDietaryRequirements());
        assertNotNull(recipe.getSteps());
        assertEquals(3, recipe.getDietaryRequirements().size());
    }

    @Test
    public void testSetName() {
        recipe.setName("test");
        assertEquals("test", recipe.getName());
    }

    @Test
    public void testSetAuthor() {
        recipe.setAuthor("author 1");
        assertEquals("author 1", recipe.getAuthor());
    }

    @Test
    public void testSetTag() {
        recipe.setTag(RecipeTag.DEFAULT);
        assertEquals(RecipeTag.DEFAULT, recipe.getTag());
    }

    @Test
    public void testSetTime() {
        recipe.setTime(60);
        assertEquals(60, recipe.getTime());
    }

    @Test
    public void testAddIngredientsAndDiet() {
        recipe.addIngredients(i1);
        assertEquals(1, recipe.getIngredients().size());
        assertTrue(recipe.getDietaryRequirements().contains("gluten-free"));
        assertTrue(recipe.getDietaryRequirements().contains("vegetarian"));

        recipe.addIngredients(i2);
        assertEquals(2, recipe.getIngredients().size());
        assertTrue(recipe.getDietaryRequirements().contains("vegetarian"));
        assertEquals(1, recipe.getDietaryRequirements().size());

        recipe.addIngredients(i3);
        assertEquals(3, recipe.getIngredients().size());
    }

    @Test
    public void testCheckNotNull() {
        assertFalse(recipe.checkNotNull()); // Fields are empty

        recipe.setTime(30);
        recipe.addIngredients(i1);
        recipe.getSteps().add("test 1");

        assertTrue(recipe.checkNotNull()); // All fields are filled
    }
}