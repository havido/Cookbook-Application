package ui;

import model.Categories;
import model.Ingredient;
import model.Recipe;
import model.RecipeLibrary;

import java.sql.SQLOutput;
import java.util.*;

public class RecipeApp {
    private RecipeLibrary library;
    private Recipe recipe1;
    private Recipe recipe2;
    private Scanner sc;

    public RecipeApp() {
        runApp();
    }

    private void runApp() {
        boolean keepGoing = true;
        String command = null;
        initialise();

        while (keepGoing) {
            menu();
            boolean commandValid = false;
            command = sc.nextLine().toLowerCase();

            while (!commandValid) {
                switch (command) {
                    case ("q"):
                        keepGoing = false;
                        commandValid = true;
                        break;
                    case ("s"):
                        searchUpRecipesMenu();
                        commandValid = true;
                        break;
                    case ("a"):
                        addRecipe();
                        commandValid = true;
                        break;
                    default:
                        System.out.println("Selection not valid...");
                        command = sc.nextLine().toLowerCase();
                }
            }
        }
        System.out.println("\nGoodbye!");
    }

    private void initialise() {
        Set<Ingredient> ing1 = new HashSet<Ingredient>();
        ing1.add(new Ingredient("cucumber", Categories.NONE));
        ing1.add(new Ingredient("flour", Categories.GLUTEN));
        recipe1 = new Recipe("Crispy Cucumber Snack", "Hannah", ing1, 20);

        Set<Ingredient> ing2 = new HashSet<Ingredient>();
        ing2.add(new Ingredient("fish", Categories.MEAT));
        ing2.add(new Ingredient("potato", Categories.NONE));
        recipe2 = new Recipe("Fish and Chips", "", ing2,20);

        library = new RecipeLibrary();
        library.getLibrary().add(recipe1);
        library.getLibrary().add(recipe2);

        sc = new Scanner(System.in);
        sc.useDelimiter("\n");
    }

    private void menu() {
        System.out.println("\nWhat do you want to do today?");
        System.out.println("\ts -> search up recipes");
        System.out.println("\ta -> add a new recipe");
        System.out.println("\tq -> quit");
    }

    private void searchUpRecipesMenu() {
        System.out.println("What do you want to based your search on?");
        System.out.println("\tn -> name of dishes");
        System.out.println("\ti -> ingredients");
        System.out.println("\td -> dietary requirements");
        System.out.println("\tt -> time consumption");

        String command = sc.nextLine().toLowerCase();
        boolean commandValid = false;

        while (!commandValid) {
            switch (command) {
                case ("n"):
                    searchByName();
                    commandValid = true;
                    break;
                case ("i"):
                    searchByIngredients();
                    commandValid = true;
                    break;
                case ("d"):
                    searchByDiet();
                    commandValid = true;
                    break;
                case ("t"):
                    searchByTime();
                    commandValid = true;
                    break;
                default:
                    System.out.println("Selection not valid...");
                    command = sc.nextLine().toLowerCase();
            }
        }
    }

    private void searchByName() {
        System.out.println("Search here:");
        String keyword = sc.nextLine();
        printList(library.filterByName(keyword));
    }

    private void searchByIngredients() {
        System.out.println("Search here:");
        String keyword = sc.nextLine();
        printList(library.filterByIngredients(keyword));
    }

    private void searchByDiet() {
        System.out.println("Search here:");
        String keyword = sc.nextLine();
        printList(library.filterByDiet(keyword));
    }

    private void searchByTime() {
        System.out.println("Enter maximum time:");
        int maxTime = sc.nextInt();
        printList(library.filterByTime(maxTime));
    }

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

    public void printList(List<Recipe> array) {
        String print = "";
        for (int i = 0; i < array.size(); i++) {
            if (!array.get(i).getName().isBlank()) {
                System.out.println("\n" + array.get(i).getName());
            }
        }
    }
}
