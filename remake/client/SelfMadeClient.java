package client;

import java.util.Scanner;
import models.FoodList;
import models.Fridge;
import models.Ingredient;
import models.Recipe;
import models.RecipeList;
import services.FridgeManager;
import services.RecipeManager;
import utilities.DateValidation;

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

  /**
   * Constructs a new SelfMadeClient instance.
   */
  public SelfMadeClient() {
    foodList = new FoodList();
    fridge = new Fridge();
    recipeList = new RecipeList();
    fridgeManager = new FridgeManager(fridge, foodList);
    recipeManager = new RecipeManager(recipeList, fridge, foodList);
  }

  /**
   * Main method to start the application.
   *
   * @param args NEED TO ADD.
   */
  public static void main(String[] args) {
    SelfMadeClient client = new SelfMadeClient();
    client.populateData();
    client.mainMenu();
  }

  /**
   * Populates the FoodList with ingredients and adds them to the fridge.
   * 
   * <p>
   * Also adds some recipes to the RecipeList and CookBooks.
   * </p>
   */
  public void populateData() {
    // Adds ingredients to the FoodList that can be put in the fridge.
    foodList.addIngredient(new Ingredient("Ground Chicken", "Meat", 400, "g", 45));
    foodList.addIngredient(new Ingredient("Ground Beef", "Meat", 400, "g", 59));
    foodList.addIngredient(new Ingredient("Pasta Plates", "Pasta", 100, "g", 49));
    foodList.addIngredient(new Ingredient("Tomato Sauce", "Sauce", 200, "g", 49));
    foodList.addIngredient(new Ingredient("Hermetic Corn", "Vegetable", 120, "g", 20));
    foodList.addIngredient(new Ingredient("Grated Cheese", "Cheese", 200, "g", 59));
    foodList.addIngredient(new Ingredient("Apple", "Fruit", 1, "amount", 10));
    foodList.addIngredient(new Ingredient("Thousand Island Sauce", "Sauce", 250, "mL", 49));
    foodList.addIngredient(new Ingredient("Chicken Breast", "Meat", 1.2, "kg", 179));
    foodList.addIngredient(new Ingredient("Kidney Beans", "Vegetable", 220, "g", 9));
    foodList.addIngredient(new Ingredient("Cooking Butter", "Butter", 500, "g", 39));
    foodList.addIngredient(new Ingredient("Egg 12-pack", "Eggs", 12, "amount", 49));
    foodList.addIngredient(new Ingredient("Bread Slices", "Bread", 12, "amount", 19));
    foodList.addIngredient(new Ingredient("Butter", "Butter", 400, "g", 39));
    foodList.addIngredient(new Ingredient("Cooking Oil", "Oil", 500, "mL", 59));
    foodList.addIngredient(new Ingredient("Skimmed Milk", "Milk", 1.75, "L", 0));
    foodList.addIngredient(new Ingredient("Spinach", "Vegetable", 200, "g", 39));
    foodList.addIngredient(new Ingredient("Carrots 12-pack", "Vegetable", 12, "amount", 39));
    foodList.addIngredient(new Ingredient("Coca Cola Zero", "Soda", 1.5, "L", 39));
    foodList.addIngredient(new Ingredient("Monster Energy Drink", "Energy Drink", 500, "mL", 24));
    foodList.addIngredient(new Ingredient("Sunflower Seeds", "Seeds", 200, "g", 29));
    foodList.addIngredient(new Ingredient("Feta Cheese", "Cheese", 200, "g", 59));
    foodList.addIngredient(new Ingredient("Basmati Rice", "Rice", 750, "g", 29));
    foodList.addIngredient(new Ingredient("Taco Sauce", "Sauce", 250, "g", 29));

    // Adds them all to the fridge with expiration dates (non-realistic dates for
    // ease).

    long today = DateValidation.getTodayAsLong(); // Considered using today + 10,
                                                  // but issues can arise depending on
                                                  // date.
    fridgeManager.addToFridge("Ground Chicken", 25012025L);

    fridgeManager.addToFridge("Ground Beef", 25012025L);
    fridgeManager.addToFridge("Ground Beef", 25022025L);
    fridgeManager.addToFridge("Ground Beef", 25012023L); // Expired example

    fridgeManager.addToFridge("Pasta Plates", 25012025L); // Not enough for recipe.

    fridgeManager.addToFridge("Tomato Sauce", 25012025L);
    fridgeManager.addToFridge("Tomato Sauce", 25012022L); // Expired

    fridgeManager.addToFridge("Hermetic Corn", 25012025L);
    fridgeManager.addToFridge("Grated Cheese", 25012025L);
    fridgeManager.addToFridge("Apple", 25012025L);
    fridgeManager.addToFridge("Thousand Island Sauce", 25012025L);
    fridgeManager.addToFridge("Chicken Breast", 25012025L);
    fridgeManager.addToFridge("Kidney Beans", 25012025L);
    fridgeManager.addToFridge("Cooking Butter", 25012025L);
    fridgeManager.addToFridge("Egg 12-pack", 25012025L);
    fridgeManager.addToFridge("Bread Slices", 25012025L);
    fridgeManager.addToFridge("Butter", 25012025L);
    fridgeManager.addToFridge("Cooking Oil", 01012030L);
    fridgeManager.addToFridge("Skimmed Milk", 01012025L);
    fridgeManager.addToFridge("Spinach", 25012025L);
    fridgeManager.addToFridge("Carrots 12-pack", 25012025L);
    fridgeManager.addToFridge("Coca Cola Zero", 25012025L);
    fridgeManager.addToFridge("Monster Energy Drink", 25012026L);
    fridgeManager.addToFridge("Sunflower Seeds", 25012025L);
    fridgeManager.addToFridge("Feta Cheese", 25012025L);
    fridgeManager.addToFridge("Basmati Rice", 25012025L);
    fridgeManager.addToFridge("Taco Sauce", 25012025L);
    // Should maybe change name from fridge to Food Storage bc rice, sauce etc.

    // Adds recipes
    // Doesnt work yet, need to implement RecipeManager method that uses multiple
    // ingredients/fridgeItems.

    Recipe beefLasagna = new Recipe(
        "Beef Lasagna",
        "Lasagna made with beef, milk, cheese, pasta plates and corn.",
        "Do this mate.",
        "Dinner");
    beefLasagna.addIngredient("Ground Beef", 750);
    beefLasagna.addIngredient("Skimmed Milk", 1);
    beefLasagna.addIngredient("Grated Cheese", 200);
    beefLasagna.addIngredient("Pasta Plates", 200);
    beefLasagna.addIngredient("Hermetic Corn", 120);
    beefLasagna.addIngredient("Tomato Sauce", 200);
    recipeList.addRecipe(beefLasagna);

    Recipe chickenSalad = new Recipe(
        "Chicken Salad",
        "Salad made with spinach, cut chicken breast, corn, sunflower seeds and feta cheese.",
        "Do this mate.",
        "Lunch");
    chickenSalad.addIngredient("Spinach", 100);
    chickenSalad.addIngredient("Chicken Breast", 200);
    chickenSalad.addIngredient("Hermetic Corn", 50);
    chickenSalad.addIngredient("Sunflower Seeds", 50);
    chickenSalad.addIngredient("Feta Cheese", 100);
    recipeList.addRecipe(chickenSalad);

    Recipe riceWithChickenAndBeans = new Recipe(
        "Rice with Chicken and Beans",
        "Rice with chicken breast, kidney beans, basmati rice and taco sauce.",
        "Do this mate.",
        "Dinner");
    riceWithChickenAndBeans.addIngredient("Chicken Breast", 200);
    riceWithChickenAndBeans.addIngredient("Kidney Beans", 100);
    riceWithChickenAndBeans.addIngredient("Basmati Rice", 75);
    riceWithChickenAndBeans.addIngredient("Taco Sauce", 150);
    recipeList.addRecipe(riceWithChickenAndBeans);

    Recipe breadWithEggs = new Recipe(
        "Bread with Eggs",
        "Bread with eggs and butter.",
        "Do this mate.",
        "Breakfast");
    breadWithEggs.addIngredient("Bread Slices", 2);
    breadWithEggs.addIngredient("Egg 12-pack", 2);
    breadWithEggs.addIngredient("Butter", 40);
    recipeList.addRecipe(breadWithEggs);
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

          fridgeManager.getAllFridgeItemsSorted();

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
          fridgeManager.getAllFridgeItemsSorted();

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
          fridgeManager.getAllFridgeItemsSorted();

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
          fridgeManager.getAllFridgeItemsSorted();

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
          fridgeManager.getAllFridgeItemsSorted();

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
