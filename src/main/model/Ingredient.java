package model;

public class Ingredient {
    private String name; // lowercase
    private Categories category;

    /*
     * EFFECTS: Set this.name to name, and this.category to category
     */
    public Ingredient(String name, Categories category) {
        this.name = name.toLowerCase();
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public Categories getCategory() {
        return category;
    }
}
