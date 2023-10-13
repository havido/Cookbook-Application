package model;

public class Ingredient {
    private String name; // lowercase
    private Categories category;

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
