package client;

import java.io.File;
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

  // Paths to CSV files
  private static final String FOODLIST_CSV = "src/main/resources/data/foodlist.csv";
  private static final String FRIDGEITEMS_CSV = "src/main/resources/data/fridgeitems.csv";
  private static final String RECIPES_CSV = "src/main/resources/data/recipes.csv";
  private static final String COOKBOOKS_CSV = "src/main/resources/data/cookbooks.csv";

  private final String foodListPath;
  private final String fridgeItemsPath;
  private final String recipesPath;
  private final String cookBooksPath;

  private static final Scanner scanner = new Scanner(System.in);
  private final FoodList foodList;
  private final Fridge fridge;
  private final RecipeList recipeList;
  private final FridgeManager fridgeManager;
  private final RecipeManager recipeManager;
  private final CookBookManager cookBookManager;

  /**
   * Constructs a new MainMenu instance and initializes the managers and lists.
   */
  public MainMenu() {
    // Paths
    this.foodListPath = getFilePath(FOODLIST_CSV);
    this.fridgeItemsPath = getFilePath(FRIDGEITEMS_CSV);
    this.recipesPath = getFilePath(RECIPES_CSV);
    this.cookBooksPath = getFilePath(COOKBOOKS_CSV);

    // Objects
    this.foodList = new FoodList();
    this.fridge = new Fridge();
    this.recipeList = new RecipeList();
    this.fridgeManager = new FridgeManager(fridge, foodList);
    this.recipeManager = new RecipeManager(recipeList, fridgeManager);
    this.cookBookManager = new CookBookManager();
  }

  private String getFilePath(String relativePath) {
    return new File(relativePath).getAbsolutePath();
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
      System.out.println("1. Manage Fridge (View, ddd, remove items from the fridge).");
      System.out.println("2. Manage Food List (Add, remove, view ingredients registered).");
      System.out.println("3. Manage Recipes (View, add, remove recipes).");
      System.out.println("4. Manage CookBooks (View, add, remove cookbooks).");
      System.out.println("5. Application Info (What does it do?)");
      System.out.println("6. Quit");

      int choice = scanner.nextInt();
      switch (choice) {
        case 1 -> new FridgeMenu(fridgeManager, scanner, foodList).display();
        case 2 -> new FoodListMenu(foodList, scanner).display();
        case 3 -> new RecipeMenu(recipeManager, scanner, foodList).display();
        // case 4 -> new CookBookMenu(cookBookManager, scanner).display();
        case 5 -> new InfoMenu(scanner).display();
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
      System.out.println("Starting data import...");

      // Import Food List
      System.out.println("Importing Food List from: " + foodListPath);
      Map<String, Ingredient> ingredients = CsvUtility.readIngredientsFromCsv(foodListPath);
      if (ingredients != null && !ingredients.isEmpty()) {
        ingredients.values().forEach(foodList::addIngredient);
        System.out.println("Food List imported successfully: "
            + ingredients.size() + " ingredients loaded.");
      } else {
        System.err.println("No ingredients found in the Food List CSV.");
      }

      // Import Fridge Items
      System.out.println("Importing Fridge Items from: " + fridgeItemsPath);

      try {
        int[] stats = CsvUtility.readFridgeItemsFromCsv(fridgeItemsPath, fridgeManager);

        System.out.println("Import Summary:");
        System.out.println("Items successfully added: " + stats[0]);
        System.out.println("Items failed to import: " + stats[1]);
      } catch (Exception e) {
        System.err.println("Error during fridge items import: " + e.getMessage());
      }

      // Import Recipes
      System.out.println("Importing Recipes from: " + recipesPath);
      int[] resultRecipeImport = CsvUtility.readRecipesFromCsv(recipesPath, recipeManager);
      if (resultRecipeImport[0] > 0) {
        System.out.println("Recipes imported successfully: "
            + resultRecipeImport[0] + " recipes loaded.");
      } else {
        System.err.println("No recipes imported. Failed to add "
            + resultRecipeImport[1] + " recipes.");
      }

      // Import CookBooks
      System.out.println("Importing CookBooks from: " + cookBooksPath);
      int[] resultCookBooksImport = CsvUtility.readCookBooksFromCsv(cookBooksPath,
          cookBookManager, recipeManager);
      if (resultCookBooksImport[0] > 0) {
        System.out.println("CookBooks imported successfully: "
            + resultCookBooksImport[0] + " cookbooks loaded.");
      } else {
        System.err.println("No CookBooks imported. Failed to add "
            + resultCookBooksImport[1] + " recipes.");
      }

      System.out.println("Data import completed successfully.");

    } catch (Exception e) {
      System.err.println("Error during data import: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Exports data to CSV files.
   */
  public void exportData() {
    try {
      System.out.println("Starting data export...");

      // Export Food List
      System.out.println("Exporting Food List to: " + foodListPath);
      CsvUtility.writeIngredientsToCsv(foodListPath, foodList.getFoodList());
      System.out.println("Food List exported successfully.");

      // Export Fridge Items
      System.out.println("Exporting Fridge Items to: " + fridgeItemsPath);
      CsvUtility.writeFridgeItemsToCsv(fridgeItemsPath, fridge.getAllFridgeItems());
      System.out.println("Fridge Items exported successfully.");

      // Export Recipes
      System.out.println("Exporting Recipes to: " + recipesPath);
      CsvUtility.writeRecipesToCsv(recipesPath,
          recipeList.getAllRecipes().values().stream().toList());
      System.out.println("Recipes exported successfully.");

      // Export CookBooks
      System.out.println("Exporting CookBooks to: " + cookBooksPath);
      CsvUtility.writeCookBooksToCsv(cookBooksPath, cookBookManager.getAllCookBooks());
      System.out.println("CookBooks exported successfully.");

      System.out.println("Data export completed successfully.");
    } catch (Exception e) {
      System.err.println("Error during data export: " + e.getMessage());
      e.printStackTrace();
    }
  }

}
