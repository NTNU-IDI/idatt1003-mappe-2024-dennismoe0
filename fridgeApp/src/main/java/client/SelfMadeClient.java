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
import models.CookBook;
import services.CookBookManager;
import services.FridgeManager;
import services.RecipeManager;
import utilities.CsvUtility;

/**
 * Client class for the application.
 * 
 * <p>
 * Populates data, creates instances of the FridgeManager and RecipeManager
 * classes, and provides a main menu for the user to interact with the
 * application.
 * Also contains the main method to start the application.
 * Which is a text-based terminal menu.
 * </p>
 *
 * @author Dennis Moe
 */

// Change to accomodate wishes: init og start
public class SelfMadeClient {

  private static final Scanner scanner = new Scanner(System.in);
  private final FridgeManager fridgeManager;
  private final RecipeManager recipeManager;
  private final FoodList foodList;
  private final Fridge fridge;
  private final RecipeList recipeList;
  private final CookBookManager cookBookManager;

  private static final String FOODLIST_CSV = "data/foodlist.csv";
  private static final String FRIDGEITEMS_CSV = "data/fridgeitems.csv";
  private static final String RECIPES_CSV = "data/recipes.csv";
  private static final String COOKBOOKS_CSV = "data/cookbooks.csv";

  /**
   * Constructs a new SelfMadeClient instance.
   */
  public SelfMadeClient() {
    foodList = new FoodList();
    fridge = new Fridge();
    recipeList = new RecipeList();
    fridgeManager = new FridgeManager(fridge, foodList);
    recipeManager = new RecipeManager(recipeList, fridge, foodList);
    cookBookManager = new CookBookManager();
  }

  /**
   * Main method to start the application.
   *
   * @param args NEED TO ADD.
   */
  public static void main(String[] args) {
    SelfMadeClient client = new SelfMadeClient();
    client.importData();
    client.mainMenu();
    client.exportData();
  }

  /**
   * Imports data from CSV files and populates the food list, fridge, recipe list,
   * and cookbooks.
   */
  public void importData() {
    try {
      // Imports foodlist and adds all ingredients in the csv file to the foodlist.
      Map<String, Ingredient> ingredients = CsvUtility.readIngredientsFromCsv(FOODLIST_CSV);
      if (ingredients != null && !ingredients.isEmpty()) {
        for (Map.Entry<String, Ingredient> entry : ingredients.entrySet()) {
          foodList.addIngredient(entry.getValue());
        }
      } else {
        System.err.println("No ingredients found in the CSV file.");
      }

      // Imports FridgeItems and adds them to the fridge.
      List<FridgeItem> fridgeItems = CsvUtility.readFridgeItemsFromCsv(FRIDGEITEMS_CSV, foodList);
      for (FridgeItem fridgeItem : fridgeItems) {
        fridge.addFridgeItem(fridgeItem);
      }

      // Imports recipes and adds them to the RecipeList.
      List<Recipe> recipes = CsvUtility.readRecipesFromCsv(RECIPES_CSV, foodList);
      for (Recipe recipe : recipes) {
        recipeList.addRecipe(recipe);
      }

      // Imports cookbooks and adds them to the CookBooks.
      Map<String, List<Recipe>> cookBookData = CsvUtility.readCookBooksFromCsv(COOKBOOKS_CSV,
          recipes);
      for (Map.Entry<String, List<Recipe>> entry : cookBookData.entrySet()) {
        String cookBookName = entry.getKey();
        List<Recipe> cookBookRecipes = entry.getValue();

        String result = cookBookManager.createCookBook(cookBookName, "Description", "Type");
        System.out.println(result);

        for (Recipe recipe : cookBookRecipes) {
          cookBookManager.addRecipeToCookBook(cookBookName, recipe.getRecipeName());
        }
      }
      System.out.println("Data imported successfully.");

    } catch (Exception e) {
      System.err.println("Error importing data: " + e.getMessage());
    }
  }

  public void exportData() {
    // Export foodlist
    CsvUtility.writeIngredientsToCsv(FOODLIST_CSV, foodList.getFoodList());

    // Export fridgeitems
    CsvUtility.writeFridgeItemsToCsv(FRIDGEITEMS_CSV, fridge.getAllFridgeItems());

    // Export recipes
    CsvUtility.writeRecipesToCsv(RECIPES_CSV,
        recipeList.getAllRecipes().values().stream().toList());

    // Export cookbooks
    CsvUtility.writeCookBooksToCsv(COOKBOOKS_CSV, cookBookManager.getAllCookBooks());
  }

  private void mainMenu() {
    boolean running = true;
    while (running) {
      System.out.println("Main Menu:");
      System.out.println("1. Manage Fridge.");
      System.out.println("2. Manage food registry.");
      System.out.println("3. Manage recipes.");
      System.out.println("4. Manage CookBooks");
      System.out.println("5. Info about the application.");
      System.out.println("6. Quit.");

      int choice = scanner.nextInt();
      switch (choice) {
        case 1 -> fridgeMenu();
        // case 2 -> foodListMenu();
        // case 3 -> recipeMenu();
        // case 4 -> cookBookMenu();
        // case 5 -> infoMenu();
        case 6 -> {
          System.out.println("Exiting the application.");
          running = false;
        }
        default -> System.out.println("Invalid choice, try again.");
      }
    }
  }

  private void fridgeMenu() {
    while (true) {
      System.out.println("Fridge Menu:");
      System.out.println("1. See all items in the fridge.");
      System.out.println("2. See all expired items.");
      System.out.println("3. Value menu");
      System.out.println("4. Manage ingredients in the fridge.");
      System.out.println("5. Back to main menu.");

      int choice = scanner.nextInt();
      switch (choice) {
        case 1 -> {
          System.out.println("All items in the fridge:");
          fridgeManager.printAllFridgeItemsSorted();
        }
        case 2 -> {
          System.out.println("All expired items in the fridge:");
          fridgeManager.printAllExpiredItems();
        }
        case 3 -> valueMenu();
        case 4 -> manageFridgeMenu();
        case 5 -> {
          return;
        }
        default -> System.out.println("Invalid choice, try again.");
      }
    }
  }

  private void valueMenu() {
    while (true) {
      System.out.println("Value menu:");
      System.out.println("1. The total value of the fridge.");
      System.out.println("2. The total value of expired items.");
      System.out.println("3. Exit value menu.");

      int choice = scanner.nextInt();

      switch (choice) {

        case 1 -> {
          System.out.println("Total value of fridge:");
          System.out.print(fridgeManager.getTotalValueOfFridge());
          System.out.print(" NOK.");
        }
        case 2 -> {
          System.out.println("Total value of fridge:");
          System.out.print(fridgeManager.getExpiredItemsValue());
          System.out.print(" NOK.");
        }
        case 3 -> {
          return;
        }
        default -> System.out.println("Invalid choice, try again.");
      }
    }
  }

  private void manageFridgeMenu() {
    while (true) {
      System.out.println("What do you want to do?");
      System.out.println("1. Add/create an ingredient to the fridge.");
      System.out.println("2. Remove an ingredient from the fridge.");
      System.out.println("3. Change quantity of an ingredient in the fridge.");
      System.out.println("4. Go back to fridge menu.");

      int choice = scanner.nextInt();
      switch (choice) {
        case 1 -> {
          System.out.print("Enter ingredient name:");
          String name = scanner.next();
          foodList.getIngredientFromFoodList(name);
          if (name == null) {
            System.out.println("No such ingredient found. \n Do you want to create one?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            int createChoice = scanner.nextInt();
            switch (createChoice) {
              case 1 -> {
                System.out.print("Enter ingredient category:");
                String category = scanner.next();
                System.out.print("Enter ingredient base weight:");
                double baseWeight = scanner.nextDouble();
                System.out.print("Enter ingredient base unit ('g', 'kg', etc.):");
                String baseUnit = scanner.next();
                System.out.print("Enter ingredient cost (NOK per whole item):");
                double cost = scanner.nextDouble();
                foodList.addIngredient(new Ingredient(name, category, baseWeight, baseUnit, cost));
                System.out.print("Enter expiration date of the item being put in the fridge:");
                long expirationDate = scanner.nextLong();
                fridgeManager.addToFridge(name, expirationDate);
                System.out.println(fridgeManager.createAndAddToFridge(
                    name, category, baseWeight, baseUnit, cost, expirationDate));
              }
              case 2 -> {
                return;
              }
              default -> System.out.println("Invalid choice, try again.");
            }
          } else {
            System.out.print("Enter expiration date of the item being put in the fridge:");
            long expirationDate = scanner.nextLong();
            fridgeManager.addToFridge(name, expirationDate);
            System.out.println("Ingredient added to the fridge.");
          }
        }
        case 2 -> {
          System.out.println("A list of all items in the fridge is generating...");

          try {
            Thread.sleep(2500);
          } catch (InterruptedException e) {
            System.err.println("An error occurred: " + e.getMessage());
          }

          fridgeManager.printAllFridgeItemsSorted();

          System.out.print("\nEnter the ID of the item you want to remove:");
          int id = scanner.nextInt();
          System.out.println(fridgeManager.removeFromFridgeById(id));
          fridgeManager.checkIngredientIdValidity(id);
          if (!fridgeManager.checkIngredientIdValidity(id)) {
            System.out.println("Invalid ID, try again.");
          } else {
            System.out.println(fridgeManager.removeFromFridgeById(id));
          }
        }
        case 3 -> {
          quantityInFridgeMenu();
        }
        case 4 -> {
          return;
        }
        default -> System.out.println("Invalid choice, try again.");
      }
    }
  }

  private void quantityInFridgeMenu() {
    while (true) {
      System.out.println("Quantity menu:");
      System.out.println("1. Set quantity of an ingredient in the fridge.");
      System.out.println("2. Add to quantity of an ingredient in the fridge.");
      System.out.println("3. Subtract from quantity of an ingredient in the fridge.");
      System.out.println("4. Delete/remove an ingredient from the fridge.");
      System.out.println("5. Go back to fridge menu.");

      int choice = scanner.nextInt();

      switch (choice) {
        case 1 -> {
          System.out.println("Generating list of everything in the fridge...");
          try {
            Thread.sleep(2500);
          } catch (InterruptedException e) {
            System.err.println("An error occurred: " + e.getMessage());
          }
          fridgeManager.printAllFridgeItemsSorted();

          System.out.println("Enter the ID of the item you want to change the quantity of:");
          int id = scanner.nextInt();
          System.out.println("Enter the new quantity:");
          double quantity = scanner.nextDouble();
          System.out.println(fridgeManager.setFridgeItemQuantityById(id, quantity));
        }
        case 2 -> {
          System.out.println("Generating list of everything in the fridge...");
          try {
            Thread.sleep(2500);
          } catch (InterruptedException e) {
            System.err.println("An error occurred: " + e.getMessage());
          }
          fridgeManager.printAllFridgeItemsSorted();

          System.out.println("Enter the ID of the item you want to add to:");
          int id = scanner.nextInt();

          String validationCheck = fridgeManager.updateFridgeItemQuantityById(id, 0);
          if (validationCheck.equals("Fridge item not found.")) {
            System.out.println(fridgeManager.updateFridgeItemQuantityById(id, 0));
            return;
          }

          System.out.println("Chosen item: " + fridgeManager.getQuantityInfoById(id));
          System.out.println("Enter the amount you want to add:");
          double amount = scanner.nextDouble();
          String result = fridgeManager.updateFridgeItemQuantityById(id, amount);
          System.out.println(result);

        }
        case 3 -> {
          System.out.println("Generating list of everything in the fridge...");
          try {
            Thread.sleep(2500);
          } catch (InterruptedException e) {
            System.err.println("An error occurred: " + e.getMessage());
          }
          fridgeManager.printAllFridgeItemsSorted();

          System.out.println("Enter the ID of the item you want to subtract from:");
          int id = scanner.nextInt();

          String validationCheck = fridgeManager.updateFridgeItemQuantityById(id, 0);
          if (validationCheck.equals("Fridge item not found.")) {
            System.out.println(fridgeManager.updateFridgeItemQuantityById(id, 0));
            return;
          }

          System.out.println("Chosen item: " + fridgeManager.getQuantityInfoById(id));
          System.out.println("Enter the amount you want to subtract:");
          double amount = scanner.nextDouble();
          String result = fridgeManager.updateFridgeItemQuantityById(id, -amount);
          System.out.println(result);
        }
        case 4 -> {
          System.out.println("Generating list of everything in the fridge...");
          try {
            Thread.sleep(2500);
          } catch (InterruptedException e) {
            System.err.println("An error occurred: " + e.getMessage());
          }
          fridgeManager.printAllFridgeItemsSorted();

          System.out.println("Enter the ID of the item you want to remove:");
          int id = scanner.nextInt();
          System.out.println(fridgeManager.removeFromFridgeById(id));
        }
        case 5 -> {
          return;
        }
        default -> System.out.println("Invalid choice, try again.");
      }
    }
  }

}
