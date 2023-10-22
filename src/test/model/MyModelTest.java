package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientTest {
    private Ingredient test1;
    private Ingredient test2;
    private Ingredient test3;

    @BeforeEach
    void runBefore() {
        test1 = new Ingredient("cucumber", IngredientCategories.NONE);
        test2 = new Ingredient("fish", IngredientCategories.MEAT);
    }

    @Test
    void testConstructor() {
        assertEquals("cucumber", test1.getName());
        assertEquals(IngredientCategories.NONE, test1.getCategory());
        assertEquals("fish", test2.getName());
        assertEquals(IngredientCategories.MEAT, test2.getCategory());
    }
}