package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;


// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public RecipeLibrary read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseRecipeLibrary(jsonArray);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private RecipeLibrary parseRecipeLibrary(JSONArray jsonArray) {
        RecipeLibrary library = new RecipeLibrary();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonRecipe = jsonArray.getJSONObject(i);
            Recipe recipe = parseRecipe(jsonRecipe);
            if (recipe.getTag() == RecipeTag.DEFAULT) {
                library.getLibrary().add(recipe);
            } else if (recipe.getTag() ==  RecipeTag.DRAFT) {
                library.getDrafts().add(recipe);
            }
        }
        return library;
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Recipe parseRecipe(JSONObject jsonObject) {
        RecipeTag tag = RecipeTag.valueOf(jsonObject.getString("Tag"));
        String name = jsonObject.getString("Name");
        String author = jsonObject.getString("Author");

        Recipe recipe = new Recipe(name, author, tag);

        // Parse ingredients
        JSONArray jsonIngredients = jsonObject.getJSONArray("Ingredients");
        for (Object json : jsonIngredients) {
            String ingredientName = jsonObject.getString("Ingredient");
            IngredientCategories ingredientType = IngredientCategories.valueOf(jsonObject.getString("Type"));
            recipe.addIngredients(new Ingredient(ingredientName, ingredientType));
        }

        recipe.setTime(jsonObject.getInt("Time"));

        // Parse steps
        JSONArray jsonSteps = jsonObject.getJSONArray("Steps");
        for (int i = 0; i < jsonSteps.length(); i++) {
            recipe.getSteps().add(jsonSteps.getString(i).substring(0, 7));
        }

        return recipe;
    }
}
