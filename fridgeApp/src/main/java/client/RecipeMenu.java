package client;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import models.FoodList;
import models.Recipe;
import services.RecipeManager;

/**
 * This class represents the menu for managing recipes.
 *
 * @author Dennis Moe
 */
public class RecipeMenu {

  private final RecipeManager recipeManager;
  private final Scanner scanner;
  private final FoodList foodList;

  /**
   * Constructor for RecipeMenu.
   *
   * @param recipeManager the recipeManager instance.
   * @param scanner       the Scanner instance for user input.
   * @param foodList      the FoodList instance.
   */
  public RecipeMenu(RecipeManager recipeManager, Scanner scanner, FoodList foodList) {
    this.recipeManager = recipeManager;
    this.scanner = scanner;
    this.foodList = foodList;
  }

  /**
   * Displays the recipe menu and handles user interaction.
   */
  public void display() {
    while (true) {
      System.out.println("Recipe menu:");
      System.out.println("1. List all recipes.");
      System.out.println("2. Manage recipes.");
      System.out.println("3. Availability menu.");
      System.out.println("4. Value menu.");
      System.out.println("5. Consume the ingredients in a recipe.");
      System.out.println("6. Back to main menu.");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume the newline character

      switch (choice) {
        case 1 -> {
          System.out.println("All recipes:");
          recipeManager.printAllRecipes();
        }
        case 2 -> manageRecipesMenu();
        case 3 -> availabilityMenu();
        case 4 -> valueMenu();
        case 5 -> {
          System.out.println("Generating a list of all recipes");
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          recipeManager.printAllRecipes();

          System.out.println("Enter the name of the recipe to consume the ingredients of:");
          System.out.println("""
              This will remove the ingredients from the fridge.
              I.e. if the recipe needs 200g of Ground Beef, it will remove this amount.""");

          String recipeName = scanner.nextLine();

          String result = recipeManager.removeMultipleQuantitiesByRecipe(recipeName);
          System.out.println(result);
        }
        case 6 -> {
          return;
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  private void manageRecipesMenu() {
    while (true) {
      System.out.println("Manage recipes:");
      System.out.println("1. Create a new recipe.");
      System.out.println("2. Delete a recipe.");
      System.out.println("3. Add/remove ingredients from a recipe.");
      System.out.println("4. Change the quantity of an ingredient in a recipe.");
      System.out.println("5. Back to recipe menu.");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume the newline character

      switch (choice) {
        case 1 -> {
          System.out.println("Generating a list of currently registered ingredients...");
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          foodList.printFoodList();

          System.out.println("Enter the name of the recipe:");
          String recipeName = scanner.nextLine();

          System.out.println("Enter the description of the recipe:");
          String description = scanner.nextLine();

          System.out.println("Enter the instructions/how-to for the recipe:");
          String instructions = scanner.nextLine();

          System.out.println("Enter the type of the recipe (Dinner, Breakfast, etc.):");
          String type = scanner.nextLine();

          System.out.println("Ingredient choice:");
          System.out.println("1. Add ingredients to the recipe.");
          System.out.println("2. Finish recipe creation (empty if no ingredients added).");

          int ingredientChoice = scanner.nextInt();
          scanner.nextLine(); // Consume the newline character

          switch (ingredientChoice) {
            case 1 -> {
              Map<String, Double> ingredientsToAdd = new HashMap<>();
              boolean addingIngredients = true;

              System.out.println("Generating a list of currently registered ingredients...");
              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }

              while (addingIngredients) {
                System.out.println("Enter the name of the ingredient to add:");
                String ingredient = scanner.nextLine();

                System.out.println(
                    "Enter the quantity of the ingredient needed (do not include the unit):");
                double quantity = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character

                ingredientsToAdd.put(ingredient, quantity);

                System.out.println("Add another ingredient? (1 for Yes, 2 for No)");
                int answer = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (answer == 2) {
                  addingIngredients = false;

                  String result = recipeManager.createNewRecipeWithIngredients(
                      recipeName, description, instructions, type, ingredientsToAdd);

                  System.out.println(result);
                }
              }
            }
            case 2 -> {
              Recipe recipe = new Recipe(recipeName, description, instructions, type);
              recipeManager.addRecipe(recipe);
              System.out.println("Recipe created successfully with no ingredients.");
            }
            default -> System.out.println("Invalid choice. Please try again.");
          }
        }
        case 2 -> {
          System.out.println("Generating a list of all recipes...");
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          recipeManager.printAllRecipes();

          System.out.println("Enter the name of the recipe to delete:");
          String recipeName = scanner.nextLine();

          String result = recipeManager.removeRecipe(recipeName);
          System.out.println(result);
        }
        case 3 -> {
          System.out.println("Generating a list of all recipes...");
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          recipeManager.printAllRecipes();

          System.out.println("Enter the name of the recipe to add/remove ingredients from:");
          String recipeName = scanner.nextLine();

          System.out.println("Ingredients in the recipe:");
          recipeManager.getIngredientsInRecipe(recipeName); // Prints the ingredients

          System.out.println("Enter the name of the ingredient to modify:");
          String ingredientName = scanner.nextLine();

          String result = recipeManager.removeIngredientFromRecipe(recipeName, ingredientName);
          System.out.println(result);
        }
        case 4 -> {
          System.out.println("Generating a list of all recipes...");
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          recipeManager.printAllRecipes();

          System.out.println(
              "Enter the name of the recipe to change the quantity of an ingredient in:");
          String recipeName = scanner.nextLine();

          System.out.println("Ingredients in the recipe with quantities:");
          recipeManager.getIngredientsInRecipe(recipeName); // Prints the ingredients

          System.out.println("Enter the name of the ingredient to modify:");
          String ingredientName = scanner.nextLine();

          System.out.println("Enter the new quantity of the ingredient:");
          double quantity = scanner.nextDouble();
          scanner.nextLine(); // Consume the newline character

          String result = recipeManager.updateRecipeIngredient(recipeName,
              ingredientName, quantity);
          System.out.println(result);
        }
        case 5 -> {
          return;
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  private void availabilityMenu() {
    System.out.println("Availability menu:");
    System.out.println("1. Check the fulfillment of a recipe.");
    System.out.println("2. Get a list of all fulfilled recipes.");
    System.out.println("3. Get a list of all partially fulfilled recipes.");
    System.out.println("4. Return to recipe menu.");

    int choice = scanner.nextInt();
    scanner.nextLine(); // Consume the newline character

    switch (choice) {
      case 1 -> {
        System.out.println("Generating a list of all recipes...");
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        recipeManager.printAllRecipes();

        System.out.println("Enter the name of the recipe to check fulfillment:");
        String recipeName = scanner.nextLine();

        String result = recipeManager.checkIngredientsAvailability(
            recipeManager.getRecipeObjectByName(recipeName));
        System.out.println(result);
      }
      case 2 -> {
        System.out.println("Generating a list of all fulfilled recipes...");
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println(recipeManager.fullyFulfilledRecipes());
      }
      case 3 -> {
        System.out.println("""
            Generating a list of partially fulfilled recipes,
            based on matching categories/type of ingredients or partial amounts.""");
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println(recipeManager.suggestedRecipesBasedOnFridgeContents());
      }
      case 4 -> {
        return;
      }
      default -> System.out.println("Invalid choice. Please try again.");
    }
  }

  private void valueMenu() {
    System.out.println("Value menu:");
    System.out.println("""
        1. Get the value of a recipe.
          - Both the cost of the ingredients you need to
            use and the cost of buying the full items.""");
    System.out.println("2. Return to recipe menu.");

    int choice = scanner.nextInt();
    scanner.nextLine(); // Consume the newline character

    switch (choice) {
      case 1 -> {
        System.out.println("Generating a list of all recipes...");
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        recipeManager.printAllRecipes();

        System.out.println("Enter the name of the recipe to get the value of:");
        String recipeName = scanner.nextLine();

        System.out.println("Cost of the quantities in the recipe:");
        double partialCost = recipeManager.costOfQuantitiesInRecipe(recipeName);
        System.out.println(partialCost + " NOK.");

        System.out.println("Total cost of the recipe:");
        double fullCost = recipeManager
            .calculateRecipeCost(recipeManager.getRecipeObject(recipeName));
        System.out.println(fullCost + " NOK.");
      }
      case 2 -> {
        return;
      }
      default -> System.out.println("Invalid choice. Please try again.");
    }
  }
}
