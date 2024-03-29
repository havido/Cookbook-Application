package persistence;

import model.Event;
import model.EventLog;
import model.Recipe;
import model.RecipeLibrary;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of recipe library to file
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
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of recipe library to file
    public void write(RecipeLibrary library) {
        JSONObject jsonLibrary = libraryToJson(library);
        saveToFile(jsonLibrary.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Library saved to: " + destination));
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

    // EFFECTS: converts each recipe in library to json object and add them to a json array
    private JSONObject libraryToJson(RecipeLibrary library) {
        JSONObject jsonLibrary = new JSONObject();
        JSONArray recipes = new JSONArray();

        library.getAllRecipes().forEach(recipe -> {
            JSONObject jsonRecipe = recipe.toJson();
            recipes.put(jsonRecipe);
        });

        jsonLibrary.put("Library", recipes);
        return jsonLibrary;
    }
}
