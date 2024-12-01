package client;

import java.util.List;
import java.util.Scanner;
import models.Recipe;
import services.RecipeManager;

/**
 * Menu for managing recipes.
 *
 * @author Dennis Moe
 */
public class RecipeMenu {

  private final RecipeManager recipeManager;
  private final Scanner scanner;

  /**
   * Constructor for RecipeMenu.
   *
   * @param recipeManager the RecipeManager instance
   * @param scanner       the Scanner instance for user input
   */
  public RecipeMenu(RecipeManager recipeManager, Scanner scanner) {
    this.recipeManager = recipeManager;
    this.scanner = scanner;
  }

  /**
   * Displays the recipe menu and handles user interaction.
   */
  public void display() {
    while (true) {
      System.out.println("Recipe Menu:");
      System.out.println("1. Manage recipes.");
      System.out.println("2. Check availability menu.");
      System.out.println("3. Value menu.");
      System.out.println("4. Back to main menu.");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume the newline character

      switch (choice) {
        case 1 -> manageRecipesMenu();
        case 2 -> availabilityMenu();
        case 3 -> valueMenu();
        case 4 -> {
          return; // Exit to main menu
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  private void manageRecipesMenu() {
    while (true) {
      System.out.println("Manage Recipes:");
      System.out.println("1. Add a new recipe.");
      System.out.println("2. Remove a recipe.");
      System.out.println("3. Remove ingredients for a recipe.");
      System.out.println("4. List all recipes.");
      System.out.println("5. Back to recipe menu.");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume the newline character

      switch (choice) {
        case 1 -> addRecipe();
        case 2 -> removeRecipe();
        case 3 -> removeIngredientsForRecipe();
        case 4 -> listAllRecipes();
        case 5 -> {
          return; // Exit to recipe menu
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  private void availabilityMenu() {
    while (true) {
      System.out.println("Check What You Can Make:");
      System.out.println("1. Check recipe ingredient availability.");
      System.out.println("2. List fully fulfilled recipes.");
      System.out.println("3. Suggest recipes based on fridge contents.");
      System.out.println("4. Back to recipe menu.");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume the newline character

      switch (choice) {
        case 1 -> checkRecipeAvailability();
        case 2 -> listFullyFulfilledRecipes();
        case 3 -> suggestRecipes();
        case 4 -> {
          return; // Exit to recipe menu
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  private void valueMenu() {
    while (true) {
      System.out.println("Recipe Value Menu:");
      System.out.println("1. Calculate the cost of a specific recipe.");
      System.out.println("2. Back to recipe menu.");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume the newline character

      switch (choice) {
        case 1 -> calculateRecipeCost();
        case 2 -> {
          return; // Exit to recipe menu
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  private void addRecipe() {
    System.out.println("Enter recipe name:");
    String name = scanner.nextLine();

    System.out.println("Enter recipe type (e.g., Lunch, Dinner, Dessert):");
    String type = scanner.nextLine();

    System.out.println("Enter recipe description:");
    String description = scanner.nextLine();

    System.out.println("Enter recipe instructions:");
    String instructions = scanner.nextLine();

    Recipe recipe = new Recipe(name, description, instructions, type);

    while (true) {
      System.out.println("Enter ingredient name (or type 'done' to finish):");
      String ingredientName = scanner.nextLine();
      if (ingredientName.equalsIgnoreCase("done")) {
        break;
      }

      System.out.println("Enter the required quantity of " + ingredientName + ":");
      double quantity = scanner.nextDouble();
      scanner.nextLine(); // Consume the newline character

      recipe.addIngredient(ingredientName, quantity);
    }

    String result = recipeManager.addRecipe(recipe);
    System.out.println(result);
  }

  private void removeRecipe() {
    System.out.println("Enter the name of the recipe to remove:");
    String recipeName = scanner.nextLine();
    String result = recipeManager.removeRecipe(recipeName);
    System.out.println(result);
  }

  private void removeIngredientsForRecipe() {
    System.out.println("Enter the name of the recipe to remove ingredients for:");
    String recipeName = scanner.nextLine();
    String result = recipeManager.removeMultipleQuantitiesByRecipe(recipeName);
    System.out.println(result);
  }

  private void listAllRecipes() {
    String result = recipeManager.getAllRecipes();
    System.out.println(result);
  }

  private void checkRecipeAvailability() {
    System.out.println("Enter the name of the recipe to check availability:");
    String recipeName = scanner.nextLine();
    Recipe recipe = recipeManager.getRecipeObject(recipeName);
    if (recipe == null) {
      System.out.println("Recipe not found.");
    } else {
      String result = recipeManager.checkIngredientsAvailability(recipe);
      System.out.println(result);
    }
  }

  private void suggestRecipes() {
    List<String> suggestions = recipeManager.suggestedRecipesBasedOnFridgeContents();
    if (suggestions.isEmpty()) {
      System.out.println("No recipes can be suggested based on the fridge contents.");
    } else {
      System.out.println("Recipe Suggestions:");
      suggestions.forEach(System.out::println);
    }
  }

  private void listFullyFulfilledRecipes() {
    List<String> fullyFulfilled = recipeManager.fullyFulfilledRecipes();
    if (fullyFulfilled.isEmpty()) {
      System.out.println("No recipes can be fully fulfilled based on the fridge contents.");
    } else {
      System.out.println("Fully Fulfilled Recipes:");
      fullyFulfilled.forEach(System.out::println);
    }
  }

  private void calculateRecipeCost() {
    System.out.println("Enter the name of the recipe to calculate the cost:");
    String recipeName = scanner.nextLine();
    Recipe recipe = recipeManager.getRecipeObject(recipeName);
    if (recipe == null) {
      System.out.println("Recipe not found.");
    } else {
      double cost = recipeManager.calculateRecipeCost(recipe);
      System.out.println("Total cost of the recipe: " + cost + " NOK");
    }
  }
}
