package client;

import services.FridgeManager;
import services.RecipeManager;
import models.FoodList;
import models.Fridge;
import models.RecipeList;
import models.Recipe;
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
    recipeManager = new RecipeManager(recipeList, fridge, foodList);
  }

  public static void main(String[] args) {
    TextBasedClient client = new TextBasedClient();
    client.mainMenu();
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

    // Sorting by type -> name -> expiration date -> quantity
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