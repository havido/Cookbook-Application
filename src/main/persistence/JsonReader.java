package persistence;

import model.Recipe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;


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

        int id = jsonObject.getInt("ID");
        List ingredients = (List) jsonObject.getJSONArray("Ingredients");
        List dietaryRequirements = (List) jsonObject.getJSONArray("Dietary restrictions");
        int time = jsonObject.getInt("Time");
        List steps = (List) jsonObject.getJSONArray("Steps");
        return recipe;
    }

    // MODIFIES: recipe
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    // <blank class>

    // MODIFIES: recipe
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    // <blank class>
}
