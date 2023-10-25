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
    public Recipe read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRecipe(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Recipe parseRecipe(JSONObject jsonObject) {
        String name = jsonObject.getString("Name");
        String author = jsonObject.getString("Author");

        Recipe recipe = new Recipe(name, author);

        addIngredients(recipe, jsonObject);
        recipe.setTime(jsonObject.getInt("Time"));
        addSteps(recipe, jsonObject);

        return recipe;
    }

    private void addIngredients(Recipe recipe, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Ingredients");
        for (Object json : jsonArray) {
            String ingredientName = jsonObject.getString("Ingredient");
            IngredientCategories ingredientType = IngredientCategories.valueOf(jsonObject.getString("Type"));
            recipe.addIngredients(new Ingredient(ingredientName, ingredientType));
        }
    }

    private void addSteps(Recipe recipe, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Steps");

        // I don't understand why this is wrong
        for (String step : jsonArray) {
            recipe.addSteps(step);
        }
    }

    // MODIFIES: recipe
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    // <blank class>

    // MODIFIES: recipe
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    // <blank class>
}
