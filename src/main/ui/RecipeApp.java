package ui;

import model.Categories;
import model.Ingredient;
import model.Recipe;
import model.RecipeLibrary;
import java.util.*;

// Cookbook application
public class RecipeApp {
    private RecipeLibrary library;
    private Recipe recipe1;
    private Recipe recipe2;
    private Scanner sc;

    // EFFECTS: runs the recipe application
    public RecipeApp() {
        runApp();
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
                    case ("a"): addRecipe();
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
        ing1.add(new Ingredient("cucumber", Categories.NONE));
        ing1.add(new Ingredient("flour", Categories.GLUTEN));
        recipe1 = new Recipe("Crispy Cucumber Snack", "Hannah", ing1, 20);
        recipe1.getSteps().add("test step 1, please don't fry cucumbers they taste like shit, no offense");
        recipe1.getSteps().add("test step 2, i made fried rice with cucumbers once bc there was nothing left to eat");
        recipe1.getSteps().add("test step 3, it was horrendous");

        Set<Ingredient> ing2 = new HashSet<Ingredient>();
        ing2.add(new Ingredient("fish", Categories.MEAT));
        ing2.add(new Ingredient("potato", Categories.NONE));
        recipe2 = new Recipe("Fish and Chips", "", ing2,20);
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
        Set<Ingredient> ingredients = new HashSet<Ingredient>();
        System.out.println("Enter a list of ingredients; each ingredient on a separate line; press d to finish: ");

        String ingredientInput;
        while (!(ingredientInput = sc.nextLine()).equals("d")) {
            if (!ingredientInput.isBlank()) {
                Ingredient ing = new Ingredient(ingredientInput, Categories.NONE);
                ingredients.add(ing);
            }
        }

        System.out.println("Enter the time needed to make this: ");
        int time = sc.nextInt();
        sc.nextLine();

        Recipe newRecipe = new Recipe(name, author, ingredients, time);

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
}
