package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


// Represents a reader that reads recipes from JSON data stored in file, and add them to a RecipeLibrary object
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads recipe library from file and returns it;
    // throws IOException if an error occurs reading data from file
    public RecipeLibrary read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRecipeLibrary(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // MODIFIES: library
    // EFFECTS: parses recipes from JSON object and adds them to library
    private RecipeLibrary parseRecipeLibrary(JSONObject jsonObject) {
        RecipeLibrary library = new RecipeLibrary();
        JSONArray jsonArray = jsonObject.getJSONArray("Library");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonRecipe = jsonArray.getJSONObject(i);
            Recipe recipe = parseRecipe(jsonRecipe);

            library.addRecipeToLibrary(recipe);
        }
        return library;
    }

    // EFFECTS: parses recipe from JSON object and returns it
    private Recipe parseRecipe(JSONObject jsonObject) {
        RecipeTag tag = RecipeTag.valueOf(jsonObject.getString("Tag"));
        String name = jsonObject.getString("Name");
        String author = jsonObject.getString("Author");

        Recipe recipe = new Recipe(name, author, tag);

        // Parse ingredients
        JSONArray jsonIngredients = jsonObject.getJSONArray("Ingredients");

        for (int i = 0; i < jsonIngredients.length(); i++) {
            JSONObject jsonIngredient = jsonIngredients.getJSONObject(i);
            String ingredientName = jsonIngredient.getString("name");
            IngredientCategories ingredientType = IngredientCategories.valueOf(jsonIngredient.getString("category"));
            recipe.addIngredients(new Ingredient(ingredientName, ingredientType));
        }

        recipe.setTime(jsonObject.getInt("Time"));

        // Parse steps
        JSONArray jsonSteps = jsonObject.getJSONArray("Steps");
        for (int i = 0; i < jsonSteps.length(); i++) {
            recipe.getSteps().add(jsonSteps.getString(i));
        }

        return recipe;
    }
}
