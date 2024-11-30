package client;

import services.FridgeManager;
import services.RecipeManager;
import models.FoodList;
import models.Fridge;
import models.RecipeList;
import models.Recipe;
import models.Ingredient;
import models.FridgeItem;
import java.util.Scanner;

public class TextBasedClient {
  private static final Scanner scanner = new Scanner(System.in);
  private final FridgeManager fridgeManager;
  private final RecipeManager recipeManager;
  private final FoodList foodList;
  private final Fridge fridge;
  private final RecipeList recipeList;

  public TextBasedClient() {
    foodList = new FoodList();
    fridge = new Fridge();
    recipeList = new RecipeList();
    fridgeManager = new FridgeManager(fridge, foodList);
    recipeManager = new RecipeManager(recipeList, fridge, foodList); // Updated
    populateData(); // Add example data to test
  }

  public static void main(String[] args) {
    TextBasedClient client = new TextBasedClient();
    client.mainMenu();
  }

  private void populateData() {
    // Adding example ingredients to the FoodList
    foodList.addIngredient(new Ingredient("Ground Beef", "Meat", 500, "g", 79.99));
    foodList.addIngredient(new Ingredient("Carrot", "Vegetable", 100, "g", 10.99));
    foodList.addIngredient(new Ingredient("Eggs", "Dairy", 60, "g", 15.00));
    foodList.addIngredient(new Ingredient("Flour", "Baking", 1000, "g", 25.00));
    foodList.addIngredient(new Ingredient("Butter", "Dairy", 250, "g", 40.00));
    foodList.addIngredient(new Ingredient("Tomato Sauce", "Condiment", 500, "ml", 19.99));
    foodList.addIngredient(new Ingredient("Garlic", "Vegetable", 50, "g", 5.00));
    foodList.addIngredient(new Ingredient("Onion", "Vegetable", 200, "g", 12.99));
    foodList.addIngredient(new Ingredient("Cheese", "Dairy", 300, "g", 60.00));
    foodList.addIngredient(new Ingredient("Milk", "Dairy", 1000, "ml", 20.00));
    foodList.addIngredient(new Ingredient("Yogurt", "Dairy", 500, "ml", 25.00));
    foodList.addIngredient(new Ingredient("Oats", "Cereal", 500, "g", 15.00));
    foodList.addIngredient(new Ingredient("Sugar", "Baking", 1000, "g", 10.00));
    foodList.addIngredient(new Ingredient("Salt", "Baking", 500, "g", 5.00));
    foodList.addIngredient(new Ingredient("Olive Oil", "Condiment", 500, "ml", 45.00));
    foodList.addIngredient(new Ingredient("Chicken", "Meat", 1000, "g", 90.00));
    foodList.addIngredient(new Ingredient("Spinach", "Vegetable", 200, "g", 25.00));

    // Adding some items to the fridge (with expiration dates)
    fridgeManager.addToFridge("Ground Beef", 25062024);
    fridgeManager.addToFridge("Carrot", 27062024);
    fridgeManager.addToFridge("Eggs", 24062024);
    fridgeManager.addToFridge("Flour", 20062024);
    fridgeManager.addToFridge("Butter", 29062024);
    fridgeManager.addToFridge("Tomato Sauce", 01072024);
    fridgeManager.addToFridge("Garlic", 01072024);
    fridgeManager.addToFridge("Cheese", 01072024);
    fridgeManager.addToFridge("Yogurt", 02072024);

    // Adding example recipes
    Recipe oatmealWithYogurt = new Recipe("Oatmeal with Yogurt", "Breakfast", "A simple breakfast recipe",
        "Cook oats, then top with yogurt.");
    oatmealWithYogurt.addIngredient("Oats", 100);
    oatmealWithYogurt.addIngredient("Yogurt", 150);
    recipeList.addRecipe(oatmealWithYogurt);

    Recipe lasagnaWithGroundBeef = new Recipe("Lasagna with Ground Beef", "Dinner",
        "Classic lasagna with ground beef and tomato sauce", "Layer pasta, sauce, ground beef, and cheese.");
    lasagnaWithGroundBeef.addIngredient("Ground Beef", 400);
    lasagnaWithGroundBeef.addIngredient("Tomato Sauce", 200);
    lasagnaWithGroundBeef.addIngredient("Cheese", 300);
    recipeList.addRecipe(lasagnaWithGroundBeef);

    Recipe carrotCake = new Recipe("Carrot Cake", "Dessert", "A delicious carrot cake",
        "Mix ingredients, bake, and enjoy!");
    carrotCake.addIngredient("Carrot", 200);
    carrotCake.addIngredient("Flour", 250);
    carrotCake.addIngredient("Eggs", 2);
    recipeList.addRecipe(carrotCake);
  }

  private void mainMenu() {
    while (true) {
      System.out.println("\nMain Menu:");
      System.out.println("1. Manage Fridge");
      System.out.println("2. Manage Recipes");
      System.out.println("3. Manage Food List");
      System.out.println("4. Exit");
      System.out.print("Enter your choice: ");

      int choice = scanner.nextInt();
      switch (choice) {
        case 1 -> fridgeMenu();
        case 2 -> recipeMenu();
        case 3 -> foodListMenu();
        case 4 -> {
          System.out.println("Exiting the program.");
          return;
        }
        default -> System.out.println("Invalid choice, try again.");
      }
    }
  }

  private void fridgeMenu() {
    while (true) {
      System.out.println("\nFridge Menu:");
      System.out.println("1. Add item to Fridge");
      System.out.println("2. Remove item from Fridge");
      System.out.println("3. View all items in Fridge");
      System.out.println("4. Go back to Main Menu");
      System.out.print("Enter your choice: ");

      int choice = scanner.nextInt();
      switch (choice) {
        case 1 -> addFridgeItem();
        case 2 -> removeFridgeItem();
        case 3 -> viewFridgeContents();
        case 4 -> {
          return;
        }
        default -> System.out.println("Invalid choice, try again.");
      }
    }
  }

  private void addFridgeItem() {
    System.out.print("Enter ingredient name: ");
    String name = scanner.next();
    System.out.print("Enter expiration date (as long, e.g., 25062024): ");
    long expirationDate = scanner.nextLong();
    System.out.println(fridgeManager.addToFridge(name, expirationDate));
  }

  private void removeFridgeItem() {
    System.out.print("Enter item ID to remove: ");
    int id = scanner.nextInt();
    System.out.println(fridgeManager.removeFromFridgeById(id));
  }

  private void viewFridgeContents() {
    System.out.println("Fridge Contents:");
    fridgeManager.getAllFridgeItemsSorted()
        .forEach(item -> System.out.println(item.getIngredient().getIngredientName() +
            " - Quantity: " + item.getQuantity() +
            ", Expiration: " + item.getFormattedExpirationDate() +
            ", Type: " + item.getIngredient().getIngredientCategory()));
  }

  private void recipeMenu() {
    while (true) {
      System.out.println("\nRecipe Menu:");
      System.out.println("1. Add Recipe");
      System.out.println("2. Remove Recipe");
      System.out.println("3. Check Recipe Ingredients Availability");
      System.out.println("4. Calculate Recipe Cost");
      System.out.println("5. Go back to Main Menu");
      System.out.print("Enter your choice: ");

      int choice = scanner.nextInt();
      switch (choice) {
        case 1 -> addRecipe();
        case 2 -> removeRecipe();
        case 3 -> checkRecipeIngredients();
        case 4 -> calculateRecipeCost();
        case 5 -> {
          return;
        }
        default -> System.out.println("Invalid choice, try again.");
      }
    }
  }

  private void addRecipe() {
    System.out.print("Enter recipe name: ");
    String name = scanner.next();
    System.out.print("Enter recipe type (e.g., Breakfast, Dinner): ");
    String type = scanner.next();
    System.out.print("Enter recipe description: ");
    String description = scanner.next();
    System.out.print("Enter instructions: ");
    String instructions = scanner.next();
    Recipe recipe = new Recipe(name, type, description, instructions);

    while (true) {
      System.out.print("Enter ingredient name to add to recipe (or 'done' to finish): ");
      String ingredientName = scanner.next();
      if (ingredientName.equalsIgnoreCase("done"))
        break;
      System.out.print("Enter quantity: ");
      double quantity = scanner.nextDouble();
      recipe.addIngredient(ingredientName, quantity);
    }

    System.out.println(recipeManager.addRecipe(recipe));
  }

  private void removeRecipe() {
    System.out.print("Enter recipe name to remove: ");
    String name = scanner.next();
    System.out.println(recipeManager.removeRecipe(name));
  }

  private void checkRecipeIngredients() {
    System.out.print("Enter recipe name to check availability: ");
    String name = scanner.next();
    Recipe recipe = recipeList.getRecipe(name);
    if (recipe != null) {
      System.out.println(recipeManager.checkIngredientsAvailability(recipe));
    } else {
      System.out.println("Recipe not found.");
    }
  }

  private void calculateRecipeCost() {
    System.out.print("Enter recipe name to calculate cost: ");
    String name = scanner.next();
    Recipe recipe = recipeList.getRecipe(name);
    if (recipe != null) {
      System.out.println("Estimated cost: " + recipeManager.calculateRecipeCost(recipe));
    } else {
      System.out.println("Recipe not found.");
    }
  }

  private void foodListMenu() {
    while (true) {
      System.out.println("\nFood List Menu:");
      System.out.println("1. View all ingredients in Food List");
      System.out.println("2. Go back to Main Menu");
      System.out.print("Enter your choice: ");

      int choice = scanner.nextInt();
      switch (choice) {
        case 1 -> viewFoodList();
        case 2 -> {
          return;
        }
        default -> System.out.println("Invalid choice, try again.");
      }
    }
  }

  private void viewFoodList() {
    System.out.println("Food List:");
    foodList.getFoodList().values().forEach(ingredient -> System.out.println("Name: " + ingredient.getIngredientName() +
        ", Category: " + ingredient.getIngredientCategory() +
        ", Cost: " + ingredient.getIngredientCost()));
  }
}