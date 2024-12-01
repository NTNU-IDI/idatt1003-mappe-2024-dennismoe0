package client;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import models.FoodList;
import models.Fridge;
import models.FridgeItem;
import models.Ingredient;
import models.Recipe;
import models.RecipeList;
import services.CookBookManager;
import services.FridgeManager;
import services.RecipeManager;
import utilities.CsvUtility;

/**
 * Main menu class for the application.
 * Handles initialization, user interaction, and data management.
 *
 * @author Dennis Moe
 */
public class MainMenu {

  private static final Scanner scanner = new Scanner(System.in);
  private final FoodList foodList;
  private final Fridge fridge;
  private final RecipeList recipeList;
  private final FridgeManager fridgeManager;
  private final RecipeManager recipeManager;
  private final CookBookManager cookBookManager;

  private static final String FOODLIST_CSV = "src/data/foodlist.csv";
  private static final String FRIDGEITEMS_CSV = "src/data/fridgeitems.csv";
  private static final String RECIPES_CSV = "src/data/recipes.csv";
  private static final String COOKBOOKS_CSV = "src/data/cookbooks.csv";

  /**
   * Constructs a new MainMenu instance and initializes the managers and lists.
   */
  public MainMenu() {
    foodList = new FoodList();
    fridge = new Fridge();
    recipeList = new RecipeList();
    fridgeManager = new FridgeManager(fridge, foodList);
    recipeManager = new RecipeManager(recipeList, fridge, foodList);
    cookBookManager = new CookBookManager();
  }

  /**
   * The main method to start the application.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    MainMenu mainMenu = new MainMenu();
    mainMenu.init();
    mainMenu.start();
  }

  /**
   * Initializes the application by importing data and preparing the environment.
   */
  public void init() {
    try {
      System.out.println("Initializing application...");
      // Import data from CSV files
      importData();
      System.out.println("Initialization complete.");
    } catch (Exception e) {
      System.err.println("Error during initialization: " + e.getMessage());
    }
  }

  /**
   * Starts the application and provides a text-based menu interface.
   */
  public void start() {
    try {
      System.out.println("Starting application...");
      // Start the main menu loop
      mainMenuLoop();
    } catch (Exception e) {
      System.err.println("Error during start: " + e.getMessage());
    }
  }

  private void mainMenuLoop() {
    boolean running = true;
    while (running) {
      System.out.println("Main Menu:");
      System.out.println("1. Manage Fridge");
      System.out.println("2. Manage Food List");
      System.out.println("3. Manage Recipes");
      System.out.println("4. Manage CookBooks");
      System.out.println("5. Application Info");
      System.out.println("6. Quit");

      int choice = scanner.nextInt();
      switch (choice) {
        case 1 -> new FridgeMenu(fridgeManager, scanner).display();
        // case 2 -> new FoodListMenu(foodList, scanner).display();
        // case 3 -> new RecipeMenu(recipeManager, scanner).display();
        // case 4 -> new CookBookMenu(cookBookManager, scanner).display();
        // case 5 -> new InfoMenu().display();
        case 6 -> {
          System.out.println("Exiting application.");
          exportData();
          running = false;
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  /**
   * Imports data from CSV files.
   */
  public void importData() {
    try {
      System.out.println("Importing data...");

      // Import Food List
      Map<String, Ingredient> ingredients = CsvUtility.readIngredientsFromCsv(FOODLIST_CSV);
      if (ingredients != null && !ingredients.isEmpty()) {
        ingredients.values().forEach(foodList::addIngredient);
      } else {
        System.err.println("No ingredients found in the CSV file.");
      }

      // Import Fridge Items
      List<FridgeItem> fridgeItems = CsvUtility.readFridgeItemsFromCsv(FRIDGEITEMS_CSV, foodList);
      if (fridgeItems != null && !fridgeItems.isEmpty()) {
        fridgeItems.forEach(fridge::addFridgeItem);
      } else {
        System.err.println("No fridge items found in the CSV file.");
      }

      // Import Recipes
      List<Recipe> recipes = CsvUtility.readRecipesFromCsv(RECIPES_CSV, foodList);
      if (recipes != null && !recipes.isEmpty()) {
        recipes.forEach(recipeList::addRecipe);
      } else {
        System.err.println("No recipes found in the CSV file.");
      }

      // Import CookBooks
      Map<String, List<Recipe>> cookBooks = CsvUtility.readCookBooksFromCsv(COOKBOOKS_CSV, recipes);
      if (cookBooks != null && !cookBooks.isEmpty()) {
        cookBooks.forEach((cookBookName, recipesInBook) -> {
          cookBookManager.createCookBook(cookBookName, "Description", "Type");
          recipesInBook.forEach(recipe -> cookBookManager.addRecipeToCookBook(
              cookBookName, recipe.getRecipeName()));
        });
      } else {
        System.err.println("No cookbooks found in the CSV file.");
      }

      System.out.println("Data imported successfully.");

    } catch (Exception e) {
      System.err.println("Error importing data: " + e.getMessage());
    }
  }

  /**
   * Exports data to CSV files.
   */
  public void exportData() {
    try {
      System.out.println("Exporting data...");
      CsvUtility.writeIngredientsToCsv(FOODLIST_CSV, foodList.getFoodList());
      CsvUtility.writeFridgeItemsToCsv(FRIDGEITEMS_CSV, fridge.getAllFridgeItems());
      CsvUtility.writeRecipesToCsv(RECIPES_CSV,
          recipeList.getAllRecipes().values().stream().toList());
      CsvUtility.writeCookBooksToCsv(COOKBOOKS_CSV, cookBookManager.getAllCookBooks());
      System.out.println("Data exported successfully.");
    } catch (Exception e) {
      System.err.println("Error exporting data: " + e.getMessage());
    }
  }
}
