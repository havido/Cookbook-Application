package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class WorkRoom implements Writable {
    private String name;
    private List stuffs;

    // EFFECTS: constructs workroom with a name and empty list of stuffs in the recipe
    public WorkRoom(Recipe recipe) {
        this.name = recipe.getName();
        stuffs = new ArrayList();
    }

    public String getName() {
        return name;
    }


    @Override
    public JSONObject toJson() {
        return null;
    }
}
