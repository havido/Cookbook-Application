package ui;

import model.IngredientCategories;
import model.Ingredient;
import model.Recipe;
import model.RecipeLibrary;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.util.*;

// Cookbook application
public class RecipeApp {
    private static final String JSON_STORE = "./data/library.json";
    private RecipeLibrary library;
    private Recipe recipe1;
    private Recipe recipe2;
    private Scanner sc;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: runs the recipe application
    public RecipeApp() throws FileNotFoundException {
        runApp();
        library = new RecipeLibrary();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    @SuppressWarnings("methodlength")
    private void runApp() {
        boolean keepGoing = true;
        String command;
        initialise();

        while (keepGoing) {
            menu();
            boolean commandValid = false;
            command = sc.nextLine().toLowerCase();

            while (!commandValid) {
                switch (command) {
                    case ("q"): keepGoing = false;
                        commandValid = true;
                        break;
                    case ("s"): searchUpRecipesMenu();
                        commandValid = true;
                        break;
                    case ("a"): menuOfAddRecipe();
                        commandValid = true;
                        break;
                    default: System.out.println("Selection not valid...");
                        command = sc.nextLine().toLowerCase();
                }
            }
        }
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes recipes and library
    private void initialise() {
        Set<Ingredient> ing1 = new HashSet<Ingredient>();
        ing1.add(new Ingredient("cucumber", IngredientCategories.NONE));
        ing1.add(new Ingredient("flour", IngredientCategories.GLUTEN));
        recipe1 = new Recipe("Crispy Cucumber Snack", "Hannah", 20);
        recipe1.getSteps().add("test step 1, please don't fry cucumbers they taste like shit, no offense");
        recipe1.getSteps().add("test step 2, i made fried rice with cucumbers once bc there was nothing left to eat");
        recipe1.getSteps().add("test step 3, it was horrendous");

        Set<Ingredient> ing2 = new HashSet<Ingredient>();
        ing2.add(new Ingredient("fish", IngredientCategories.MEAT));
        ing2.add(new Ingredient("potato", IngredientCategories.NONE));
        recipe2 = new Recipe("Fish and Chips", "", 20);
        recipe2.getSteps().add("test step 1, this is like the easiest dish to eat");
        recipe2.getSteps().add("test step 2, even the frozen fish and fried fries are good");
        recipe2.getSteps().add("test step 3, i like it, but it's kinda unhealthy");

        library = new RecipeLibrary();
        library.getLibrary().add(recipe1);
        library.getLibrary().add(recipe2);

        sc = new Scanner(System.in);
        sc.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void menu() {
        System.out.println("\nWhat do you want to do today?");
        System.out.println("\ts -> search up recipes");
        System.out.println("\ta -> add a new recipe");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays menu of options to user inside the "search" branch
    private void searchMenu() {
        System.out.println("What do you want to based your search on?");
        System.out.println("\tn -> name of dishes");
        System.out.println("\ti -> ingredients");
        System.out.println("\td -> dietary requirements");
        System.out.println("\tt -> time consumption");
    }

    // MODIFIES: this
    // EFFECTS: processes user input, and after the list of filtered recipes is shown,
    // they can choose to view the recipe by ID
    private void searchUpRecipesMenu() {
        searchMenu();
        String command = sc.nextLine().toLowerCase();
        boolean commandValid = false;

        while (!commandValid) {
            switch (command) {
                case ("n"): searchByName();
                    commandValid = true;
                    break;
                case ("i"): searchByIngredients();
                    commandValid = true;
                    break;
                case ("d"): searchByDiet();
                    commandValid = true;
                    break;
                case ("t"): searchByTime();
                    commandValid = true;
                    break;
                default: System.out.println("Selection not valid...");
                    command = sc.nextLine().toLowerCase();
            }
        }
        viewRecipe();
    }

    // EFFECTS: filter the library by names and print a filtered list
    private void searchByName() {
        System.out.println("Search here:");
        String keyword = sc.nextLine();
        printList(library.filterByName(keyword));
    }

    // EFFECTS: filter the library by ingredients and print a filtered list
    private void searchByIngredients() {
        System.out.println("Search here:");
        String keyword = sc.nextLine();
        printList(library.filterByIngredients(keyword));
    }

    // EFFECTS: filter the library by dietary requirements and print a filtered list
    private void searchByDiet() {
        System.out.println("Search here:");
        String keyword = sc.nextLine();
        printList(library.filterByDiet(keyword));
    }

    // EFFECTS: filter the library by time taken and print a filtered list
    private void searchByTime() {
        System.out.println("Enter maximum time:");
        int maxTime = sc.nextInt();
        printList(library.filterByTime(maxTime));
    }

    // MODIFIES: this
    // EFFECTS: construct a new Recipe object and add this object to the library
    @SuppressWarnings("methodlength")
    private void addRecipe() {
        System.out.println("Enter a name for the recipe: ");
        String name = sc.nextLine();
        System.out.println("Enter your name as the author: ");
        String author = sc.nextLine();
        System.out.println("Enter the time needed to make this: ");
        int time = sc.nextInt();
        sc.nextLine();

        Recipe newRecipe = new Recipe(name, author, time);

        Set<Ingredient> tempIngredients = new HashSet<Ingredient>();
        System.out.println("Enter a list of tempIngredients; each ingredient on a separate line; press d to finish: ");

        String ingredientInput;
        while (!(ingredientInput = sc.nextLine()).equals("d")) {
            if (!ingredientInput.isBlank()) {
                Ingredient ing = new Ingredient(ingredientInput, IngredientCategories.NONE);
                tempIngredients.add(ing);
            }
        }

        for (Ingredient i : tempIngredients) {
            newRecipe.getIngredients().add(i);
        }

        System.out.println("Enter a list of steps; each step on a separate line, press d to finish: ");
        String stepInput;
        while (!(stepInput = sc.nextLine()).equals("d")) {
            if (!stepInput.isBlank()) {
                newRecipe.getSteps().add(stepInput);
            }
        }
        System.out.println("\nYour recipe has been added successfully!");
        System.out.println(newRecipe.toString());
        library.getLibrary().add(newRecipe);
    }

    // EFFECTS: print a list of type Recipe
    private void printList(List<Recipe> array) {
        for (Recipe recipe : array) {
            if (!recipe.getName().isBlank()) {
                System.out.println("[ID: " + recipe.getId() + "] " + recipe.getName());
            }
        }
    }

    // EFFECTS: print a recipe with its information
    private void viewRecipe() {
        System.out.println("Enter the corresponding ID to view the recipe, or enter 0 to return to main menu: ");
        int input = sc.nextInt();
        sc.nextLine();
        boolean valid = false;

        if (input == 0) {
            return;
        } else {
            while (!valid) {
                try {
                    System.out.println(library.getLibrary().get(input - 1).toString());
                    // because each recipe's id = its index in the List library - 1
                    valid = true;
                } catch (Exception e) {
                    System.out.println("Invalid ID! Please choose one from the list: ");
                    input = sc.nextInt();
                    sc.nextLine();
                }
            }
        }
        System.out.println("\nEnd of recipe.");
    }

    public void menuOfAddRecipe() {
        System.out.println("Do you want to create a new recipe or load one from drafts?");
        System.out.println("\tn -> new draft");
        System.out.println("\tl -> load a draft");

        String command = sc.nextLine().toLowerCase();
        boolean commandValid = false;

        while (!commandValid) {
            switch (command) {
                case ("n"): addRecipe();
                    commandValid = true;
                    break;
                case ("l"): viewDrafts();
                    commandValid = true;
                    break;
                default: System.out.println("Selection not valid...");
                    command = sc.nextLine().toLowerCase();
            }
        }
    }

    public void viewDrafts() {
        // stub
    }

    public void menuChangeFieldsInRecipe() {
        System.out.println("Choose a field to rewrite: ");
        System.out.println("\tn -> recipe name");
        System.out.println("\ta -> author's name");
        System.out.println("\ti -> list of ingredients");
        System.out.println("\tt -> time needed");
        System.out.println("\ts -> steps");

        String command = sc.nextLine().toLowerCase();
        boolean commandValid = false;

        while (!commandValid) {
            switch (command) {
                case ("n"): //stub
                    commandValid = true;
                    break;
                case ("a"): //stub
                    commandValid = true;
                    break;
                case ("i"): //stub
                    commandValid = true;
                    break;
                case ("t"): //stub
                    commandValid = true;
                    break;
                case ("s"): //stub
                    commandValid = true;
                    break;
                default: System.out.println("Selection not valid...");
                    command = sc.nextLine().toLowerCase();
            }
        }
    }
}
