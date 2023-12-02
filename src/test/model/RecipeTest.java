package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {
    private Recipe recipe;
    private Recipe recipe2;
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
        recipe2 = new Recipe("", "", RecipeTag.DRAFT);
    }

    @Test
    void testConstructor() {
        assertEquals("Anonymous", recipe2.getAuthor());
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
        assertFalse(recipe2.checkNotNull()); // Fields are empty
        recipe2.setName("hehe");
        assertFalse(recipe2.checkNotNull()); // Fields are empty
        recipe2.setTime(30);
        assertFalse(recipe2.checkNotNull()); // Fields are empty
        recipe2.addIngredients(i1);
        assertFalse(recipe2.checkNotNull()); // Fields are empty
        recipe2.getSteps().add("test 1");
        assertTrue(recipe2.checkNotNull()); // All fields are filled
    }

    @Test
    public void testToString() {
        String emptyIngSteps = "ID: -1\nRecipe: name\nAuthor: author\nTotal time: 0\n\nDietary notes: [lactose-free, gluten-free, vegetarian]\nIngredients: []" +
                "\n\nInstructions: ";
        assertEquals(emptyIngSteps, recipe.toString());
        recipe.addIngredients(i1);
        String emptyIng = "ID: -1\nRecipe: name\nAuthor: author\nTotal time: 0\n\nDietary notes: [gluten-free, vegetarian]\nIngredients: [i1]" +
                "\n\nInstructions: ";
        assertEquals(emptyIng, recipe.toString());
        recipe.getSteps().add("test step");
        String good = "ID: -1\nRecipe: name\nAuthor: author\nTotal time: 0\n\nDietary notes: [gluten-free, vegetarian]\nIngredients: [i1]" +
                "\n\nInstructions: \n\nStep 1: test step";
        assertEquals(good, recipe.toString());
    }
}