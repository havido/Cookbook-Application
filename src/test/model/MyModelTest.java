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
        test1 = new Ingredient("cucumber", Categories.NONE);
        test2 = new Ingredient("fish", Categories.MEAT);
    }

    @Test
    void testConstructor() {
        assertEquals("cucumber", test1.getName());
        assertEquals(Categories.NONE, test1.getCategory());
        assertEquals("fish", test2.getName());
        assertEquals(Categories.MEAT, test2.getCategory());
    }
}