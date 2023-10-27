package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {
    private Recipe test1;
    private Recipe test2;
    private Ingredient i1;
    private Ingredient i2;
    private Ingredient i3;

    @BeforeEach
    void runBefore() {
        Set<Ingredient> ing1 = new HashSet<Ingredient>();
        ing1.add(i1 = new Ingredient("cucumber", IngredientCategories.NONE));
        ing1.add(i2 = new Ingredient("flour", IngredientCategories.GLUTEN));
        test1 = new Recipe("Crispy Cucumber Snack", "Hannah", RecipeTag.DEFAULT);
        test1.getSteps().add("test step 1");
        test1.getSteps().add("test step 2");
        test1.getSteps().add("test step 3");

        Set<Ingredient> ing2 = new HashSet<Ingredient>();
        ing2.add(new Ingredient("fish", IngredientCategories.MEAT));
        ing2.add(i3 = new Ingredient("potato", IngredientCategories.NONE));
        ing2.add(new Ingredient("butter", IngredientCategories.LACTOSE));
        test2 = new Recipe("Fish and Chips", "", RecipeTag.DEFAULT);
        test2.getSteps().add("test step 1");
        test2.getSteps().add("test step 2");
        test2.getSteps().add("test step 3");
    }

    @Test
    void testConstructor() {
        assertEquals("Crispy Cucumber Snack", test1.getName());

        assertEquals("Hannah", test1.getAuthor());
        assertEquals("Anonymous", test2.getAuthor());

        assertEquals(1, test1.getId());
        assertEquals(2, test2.getId());

        assertTrue(test1.getIngredients().contains(i1));
        assertTrue(test2.getIngredients().contains(i3));
        assertFalse(test2.getIngredients().contains(i2));

        assertTrue(test1.getDietaryRequirements().contains("lactose-free"));
        assertFalse(test1.getDietaryRequirements().contains("gluten-free"));
        assertTrue(test2.getDietaryRequirements().contains("gluten-free"));
        assertFalse(test2.getDietaryRequirements().contains("vegetarian"));
        assertFalse(test2.getDietaryRequirements().contains("lactose-free"));

        assertEquals(20, test1.getTime());
        assertEquals(15, test2.getTime());

        assertEquals("ID: 1\nRecipe: Crispy Cucumber Snack\nAuthor: Hannah\nTotal time: 20\n\n"
                + "Dietary notes: [lactose-free, vegetarian]" + "\nIngredients: [cucumber, flour]" + "\n\nInstructions: "
                + "\nStep 1: test step 1\nStep 2: test step 2\nStep 3: test step 3", test1.toString());
    }
}