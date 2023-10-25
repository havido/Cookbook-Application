package persistence;

import model.Recipe;
import model.RecipeLibrary;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of recipe to file
    public void write(RecipeLibrary library) {
        JSONObject jsonLibrary = libraryToJson(library);
        saveToFile(jsonLibrary.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    private JSONObject libraryToJson(RecipeLibrary library) {
        JSONObject jsonLibrary = new JSONObject();
        JSONArray recipes = new JSONArray();

        library.getLibrary().forEach(recipe -> {
            JSONObject jsonRecipe = recipe.toJson();
            recipes.put(jsonRecipe);
        });

        jsonLibrary.put("Library", recipes);
        return jsonLibrary;
    }
}
