package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.util.*;

// Represents a cookbook application
public class RecipeApp {
    private static final String JSON_STORE = "./data/library.json";
    private RecipeLibrary library;
    private Scanner sc;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: constructs recipe library and runs application
    public RecipeApp() throws FileNotFoundException {
        sc = new Scanner(System.in);
        library = new RecipeLibrary();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    @SuppressWarnings("methodlength")
    private void runApp() {
        boolean keepGoing = true;
        String command;
        loadLibrary();

        while (keepGoing) {
            menu();
            boolean commandValid = false;
            command = sc.nextLine().toLowerCase();

            while (!commandValid) {
                switch (command) {
                    case ("q"): keepGoing = false;
                        quitPrompt();
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
        // test commit
    }

    // EFFECTS: prompt user to save changes before quitting
    private void quitPrompt() {
        System.out.println("Save all changes to data?");
        System.out.println("y -> yes");
        System.out.println("n -> no");
        System.out.println("WARNING: all changes will be lost if you close the app without saving!");

        String command = sc.nextLine().toLowerCase();
        boolean commandValid = false;

        while (!commandValid) {
            switch (command) {
                case ("y"): saveLibrary();
                    commandValid = true;
                    break;
                case ("n"): commandValid = true;
                    break;
                default: System.out.println("Selection not valid...");
                    command = sc.nextLine().toLowerCase();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: load library from file
    private void loadLibrary() {
        try {
            library = jsonReader.read();
        } catch (Exception e) {
            System.out.println("Error loading library from " + JSON_STORE);
        }
    }

    // EFFECTS: save library to file
    private void saveLibrary() {
        try {
            jsonWriter.open();
            jsonWriter.write(library);
            jsonWriter.close();
            System.out.println("All changes saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Error saving library to " + JSON_STORE);
        }
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
    // EFFECTS: processes user input and return a list of recipes that meet descriptions
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
    }

    // EFFECTS: filter the library by names and print a filtered list
    private void searchByName() {
        System.out.println("Search here:");
        String keyword = sc.nextLine();
        printList(library.filterByName(keyword),"");
    }

    // EFFECTS: filter the library by ingredients and print a filtered list
    private void searchByIngredients() {
        System.out.println("Search here:");
        String keyword = sc.nextLine();
        printList(library.filterByIngredients(keyword), "");
    }

    // EFFECTS: filter the library by dietary requirements and print a filtered list
    private void searchByDiet() {
        System.out.println("Search here:");
        String keyword = sc.nextLine();
        printList(library.filterByDiet(keyword), "");
    }

    // EFFECTS: filter the library by time taken and print a filtered list
    private void searchByTime() {
        System.out.println("Enter maximum time:");
        int maxTime = sc.nextInt();
        printList(library.filterByTime(maxTime), "");
    }

    // MODIFIES: this
    // EFFECTS: construct a new Recipe object and add this object to the library
    private void addRecipe() {
        System.out.println("Enter a name for the recipe: ");
        String name = sc.nextLine();
        System.out.println("Enter your name as the author: ");
        String author = sc.nextLine();

        Recipe newRecipe = new Recipe(name, author, RecipeTag.DRAFT);
        System.out.println("Your draft has been created successfully! ID: " + newRecipe.getId() + " (-1 -> unsaved)");
        System.out.println("Once you have completed all fields, "
                + "you have the option to officially add your recipe to the library.");

        System.out.println("\nEnter the time needed to make this: ");
        newRecipe.setTime(sc.nextInt());
        sc.nextLine(); // Consume new line

        addIngredients(newRecipe);
        addSteps(newRecipe);

        saveMenu(newRecipe);
    }

    // EFFECTS: prints to user their chosen draft & prompts user to save a chosen draft to library
    // (if requirements are met)
    @SuppressWarnings("methodlength")
    private void saveMenu(Recipe newRecipe) {
        System.out.println("Your current work:");
        System.out.println(newRecipe.toString()); // Print recipe

        saveMenuPrompts();

        String command = sc.nextLine().toLowerCase();
        boolean commandValid = false;

        while (!commandValid) {
            switch (command) {
                case ("s"):
                    if (library.getDrafts().contains(newRecipe)) {
                        saveLibrary();
                    } else {
                        library.addRecipeToLibrary(newRecipe);
                        saveLibrary();
                    }
                    commandValid = true;
                    break;
                case ("a"): boolean b = addRecipeToOfficialLibrary(newRecipe);
                    if (b) {
                        commandValid = true;
                    } else if (!b) {
                        saveMenuPrompts();
                        command = sc.nextLine().toLowerCase();
                    }
                    break;
                case ("c"): changeFieldsInRecipe(newRecipe);
                    commandValid = true;
                    break;
                default: System.out.println("Selection not valid...");
                    command = sc.nextLine().toLowerCase();
            }
        }
    }

    // EFFECTS: display menu of options to user
    private static void saveMenuPrompts() {
        System.out.println("\nWhat do you want to do next?");
        System.out.println("\ts -> save as drafts");
        System.out.println("\ta -> officially save & add to recipe library");
        System.out.println("\tc -> edit a selected field in the current recipe");
        System.out.println("WARNING: all changes will be lost if you close the app without saving!");
    }

    // EFFECTS: check recipe if it meets the requirements to be added to the library
    private boolean addRecipeToOfficialLibrary(Recipe recipe) {
        if (recipe.checkNotNull()) {
            if (library.getDrafts().contains(recipe)) {
                library.getDrafts().remove(recipe);
                library.getLibrary().add(recipe);
                recipe.setTag(RecipeTag.DEFAULT);
            } else {
                library.addRecipeToLibrary(recipe);
            }
            saveLibrary();
            return true;
        } else {
            System.out.println("One or more fields are blank or have inappropriate values! (e.g., time = 0 minute)");
            System.out.println("Please make sure all fields are completed before adding recipe to library.");
            return false;
        }
    }

    // EFFECTS: prompts user to add a list of steps to the recipe or delete the current list
    private void addSteps(Recipe newRecipe) {
        System.out.println("Enter a list of steps to add to recipe; each step on a separate line; "
                + "enter del to delete all steps; press d to finish: ");
        String stepInput;
        while (!(stepInput = sc.nextLine()).equals("d")) {
            if (stepInput.equals("del")) {
                for (String step : newRecipe.getSteps()) {
                    newRecipe.getSteps().remove(step);
                }
            } else if (!stepInput.isBlank()) {
                newRecipe.getSteps().add(stepInput);
            }
        }
    }

    // EFFECTS: prompts user to add a list of ingredients to the recipe or delete the current list
    private void addIngredients(Recipe newRecipe) {
        Set<Ingredient> tempIngredients = new HashSet<Ingredient>();
        System.out.println("Enter a list of ingredients to add to recipe; each ingredient on a separate line; "
                + "enter del to delete all ingredients; press d to finish: ");

        String ingredientInput;
        while (!(ingredientInput = sc.nextLine()).equals("d")) {
            if (ingredientInput.equals("del")) {
                for (Ingredient i : tempIngredients) {
                    tempIngredients.remove(i);
                }
            } else if (!ingredientInput.isBlank()) {
                Ingredient ing = new Ingredient(ingredientInput, IngredientCategories.NONE);
                tempIngredients.add(ing);
            }
        }

        for (Ingredient i : tempIngredients) {
            newRecipe.addIngredients(i); // Add elements of HashSet into field ArrayList in object Recipe
        }
    }

    // EFFECTS: print a list of type Recipe
    private int printList(List<Recipe> array, String caller) {
        if (!array.isEmpty()) {
            for (Recipe recipe : array) {
                if (!recipe.getName().isBlank()) {
                    System.out.println("[ID: " + recipe.getId() + "] " + recipe.getName());
                }
            }
            if (caller.equals("menuOfAddRecipe()")) {
                return array.size();
            } else {
                viewRecipeFromList("");
                return array.size();
            }
        } else {
            System.out.println("No data matched! Returning to menu...");
            return 0;
        }
    }

    // REQUIRES: input is integer
    // EFFECTS: print a recipe with its information
    @SuppressWarnings("methodlength")
    private Recipe viewRecipeFromList(String caller) {
        System.out.println("Enter the corresponding ID to view the recipe, or enter 0 to return to menu: ");
        int input = sc.nextInt();
        sc.nextLine();
        boolean valid = false;

        if (input == 0) {
            return null;
        } else {
            while (!valid) {
                try {
                    if (caller.equals("menuOfAddRecipe()")) {
                        return library.getAllRecipes().get(input - 1);
                    } else {
                        System.out.println(library.getAllRecipes().get(input - 1).toString());
                        // because each recipe's id = its index in the List library - 1
                        valid = true;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid ID! Please choose one from the list: ");
                    input = sc.nextInt();
                    sc.nextLine();
                }
            }
        }
        System.out.println("\nEnd of recipe.");
        return library.getAllRecipes().get(input - 1);
    }

    // EFFECTS: prompts user to choose between creating a new draft and load a list of existing drafts
    @SuppressWarnings("methodlength")
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
                case ("l"): int temp = printList(library.getDrafts(), "menuOfAddRecipe()");
                    if (temp == 0) {
                        return;
                        // empty list -> return to main menu
                    } else {
                        // list not empty -> prompt to choose to view a recipe -> saveMenu()
                        Recipe tempRecipe = viewRecipeFromList("menuOfAddRecipe()");
                        if (tempRecipe == null) {
                            return;
                        } else {
                            saveMenu(tempRecipe);
                            commandValid = true;
                            break;
                        }
                    }
                default: System.out.println("Selection not valid...");
                    command = sc.nextLine().toLowerCase();
            }
        }
    }

    // EFFECTS: processes user command to choose what field in a recipe to edit
    @SuppressWarnings("methodlength")
    public void changeFieldsInRecipe(Recipe recipe) {
        menuChangeFieldsInRecipe();
        String command = sc.nextLine().toLowerCase();
        boolean commandValid = false;

        while (!commandValid) {
            switch (command) {
                case ("n"): System.out.println("Type a name to change to: ");
                    recipe.setName(sc.nextLine());
                    commandValid = true;
                    changeFieldsInRecipe(recipe);
                    break;
                case ("a"): System.out.println("Type an author's name to change to: ");
                    recipe.setAuthor(sc.nextLine());
                    commandValid = true;
                    changeFieldsInRecipe(recipe);
                    break;
                case ("i"): addIngredients(recipe);
                    commandValid = true;
                    changeFieldsInRecipe(recipe);
                    break;
                case ("t"): System.out.println("Type a duration to change to (in minutes): ");
                    recipe.setTime(sc.nextInt());
                    sc.nextLine();
                    commandValid = true;
                    changeFieldsInRecipe(recipe);
                    break;
                case ("s"): addSteps(recipe);
                    commandValid = true;
                    changeFieldsInRecipe(recipe);
                    break;
                case ("c"): saveMenu(recipe);
                    commandValid = true;
                    break;
                default: System.out.println("Selection not valid...");
                    command = sc.nextLine().toLowerCase();
            }
        }
    }

    // EFFECTS: displays menu of options to user
    private static void menuChangeFieldsInRecipe() {
        System.out.println("Choose a field to rewrite: ");
        System.out.println("\tn -> recipe name");
        System.out.println("\ta -> author's name");
        System.out.println("\ti -> list of ingredients");
        System.out.println("\tt -> time needed");
        System.out.println("\ts -> steps");
        System.out.println("\tc -> close editing menu");
    }
}
