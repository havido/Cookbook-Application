package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientTest {
    private Ingredient test1;
    private Ingredient test2;
    private JSONObject json;

    @BeforeEach
    void runBefore() {
        test1 = new Ingredient("cucumber", IngredientCategories.NONE);
        test2 = new Ingredient("fish", IngredientCategories.MEAT);
        json = new JSONObject();
    }

    @Test
    void testConstructor() {
        assertEquals("cucumber", test1.getName());
        assertEquals(IngredientCategories.NONE, test1.getCategory());
        assertEquals("fish", test2.getName());
        assertEquals(IngredientCategories.MEAT, test2.getCategory());
    }

    @Test
    void testToStringToJson() {
        assertEquals("cucumber, NONE", test1.toString());
        json.put("name", "cucumber");
        json.put("category", IngredientCategories.NONE);
        assertEquals(json.get("name"), test1.toJson().get("name"));
        assertEquals(json.get("category"), test1.toJson().get("category"));
    }
}