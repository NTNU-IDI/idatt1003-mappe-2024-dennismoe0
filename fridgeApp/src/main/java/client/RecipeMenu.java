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
      System.out.println("5. Back to main menu.");

      int choice = scanner.nextInt();

      switch (choice) {
        case 1 -> {
          // Need to add @override, all prints are memory addresses.
          System.out.println("All recipes:");
          recipeManager.printAllRecipes();
        }
        case 2 -> manageRecipesMenu();
        // case 3 -> availabilityMenu();
        // case 4 -> valueMenu();
        case 5 -> {
          return;
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  // In the create menu list all items in the foodlist when entering ingredients.
  // Maybe refer to the foodlist menu for creating an ingredient for user
  // convenience.
  private void manageRecipesMenu() {
    while (true) {
      System.out.println("Manage recipes:");
      System.out.println("1. Create a new recipe.");
      System.out.println("2. Delete a recipe.");
      System.out.println("3. Add/remove ingredients from a recipe.");
      System.out.println("4. Change the quantity of an ingredient in a recipe.");
      System.out.println("5. Back to recipe menu.");

      int choice = scanner.nextInt();

      switch (choice) {
        case 1 -> {
          System.out.println("Generating a list of currently registred ingredients...");
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
          }
          // This does not print anything
          foodList.printFoodList();

          System.out.println("Enter the name of the recipe:");
          String recipeName = scanner.next();
          scanner.nextLine(); // Consume the newline character

          System.out.println("Enter the description of the recipe:");
          String description = scanner.next();
          scanner.nextLine(); // Consume the newline character

          System.out.println("Enter the instructions/how-to for the recipe:");
          String instructions = scanner.next();
          scanner.nextLine(); // Consume the newline character

          System.out.println("Enter the type of the recipe (Dinner, breakfast etc.):");
          String type = scanner.next();
          scanner.nextLine(); // Consume the newline character

          System.out.println("Ingredient choice:");
          System.out.println("1. Add ingredients to the recipe.");
          System.out.println("2. Finish recipe creation (empty if no ingredients added).");

          int ingredientChoice = scanner.nextInt();

          switch (ingredientChoice) {
            case 1 -> {
              Map<String, Double> ingredientsToAdd = new HashMap<>();
              boolean addingIngredients = true;

              System.out.println("Generating a list of currently registred ingredients...");
              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
              }

              while (addingIngredients) {
                System.out.println("Enter the name of the ingredient to add:");
                String ingredient = scanner.next();
                scanner.nextLine(); // Consume the newline character

                System.out.println(
                    "Enter the quantity of the ingredient needed (do not include the unit):");
                double quantity = scanner.nextDouble();

                ingredientsToAdd.put(ingredient, quantity);

                System.out.println("Add another ingredient? (yes/no)");
                int answer = scanner.nextInt();

                if (answer == 2) {
                  addingIngredients = false;

                  String result = recipeManager.createNewRecipeWithIngredients(
                      recipeName, description, instructions, recipeName, ingredientsToAdd);

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

          System.out.println("Generating a list of all recipes");
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
          }
          recipeManager.printAllRecipes();

          System.out.println("Enter the name of the recipe to delete:");
          String recipeName = scanner.next();
          scanner.nextLine(); // Consume the newline character
          recipeManager.removeRecipe(recipeName);
          String result = recipeManager.removeRecipe(recipeName);
          System.out.println(result);
        }
        case 3 -> {
          System.out.println("Generating a list of all recipes");
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
          }
          recipeManager.printAllRecipes();

          System.out.println("Enter the name of the recipe to add/remove ingredients from:");
          String recipeName = scanner.next();
          scanner.nextLine(); // Consume the newline character
          System.out.println("Ingredients in the recipe:");
          recipeManager.getIngredientsInRecipe(recipeName); // Prints the ingredients

          System.out.println("Enter the name of the ingredient to modify:");
          String ingredientName = scanner.next();
          scanner.nextLine(); // Consume the newline character
          recipeManager.removeIngredientFromRecipe(recipeName, ingredientName);
          String result = recipeManager.removeIngredientFromRecipe(recipeName, ingredientName);
          System.out.println(result);
        }
        case 4 -> {
          System.out.println("Generating a list of all recipes");
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
          }
          recipeManager.printAllRecipes();

          System.out.println(
              "Enter the name of the recipe to change the quantity of an ingredient in:");
          String recipeName = scanner.next();
          scanner.nextLine(); // Consume the newline character
          System.out.println("Ingredients in the recipe with quantities:");
          recipeManager.getIngredientsInRecipe(recipeName); // Prints the ingredients

          System.out.println("Enter the name of the ingredient to modify:");
          String ingredientName = scanner.next();
          scanner.nextLine(); // Consume the newline character
          System.out.println("Enter the new quantity of the ingredient:");
          double quantity = scanner.nextDouble();

          recipeManager.updateRecipeIngredient(recipeName, ingredientName, quantity);
          String result = recipeManager.updateRecipeIngredient(
              recipeName, ingredientName, quantity);
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

  }

  private void valueMenu() {

  }

}