package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import models.FoodList;
import models.Fridge;
import models.FridgeItem;
import models.Ingredient;
import models.Recipe;
import models.RecipeList;

/**
 * Manages recipes and operations on the RecipeList.
 *
 * @author Dennis Moe
 */
public class RecipeManager {
  private final RecipeList recipeList; // Add RecipeList to manage recipes
  private final Fridge fridge; // Add Fridge to check ingredient availability
  private final FoodList foodList; // Add FoodList to access ingredient prices and details

  /**
   * Constructs a new RecipeManager.
   *
   * @param recipeList the RecipeList instance to manage
   * @param fridge     the Fridge instance for ingredient availability
   * @param foodList   the FoodList instance for ingredient details
   */
  public RecipeManager(RecipeList recipeList, Fridge fridge, FoodList foodList) {
    this.recipeList = recipeList;
    this.fridge = fridge;
    this.foodList = foodList; // Initialize FoodList
  }

  /**
   * Adds a new recipe to the RecipeList.
   *
   * @param recipe the recipe to add
   * @return a message indicating the outcome of the operation
   */
  public String addRecipe(Recipe recipe) {
    if (recipeList.getRecipe(recipe.getRecipeName()) != null) {
      return "A recipe already exists with that name. Delete it or use another name.";
    }
    recipeList.addRecipe(recipe);
    return "Recipe successfully created.";
  }

  /**
   * Removes a recipe from the RecipeList by name.
   *
   * @param recipeName the name of the recipe to remove
   * @return a message indicating the outcome of the operation
   */
  public String removeRecipe(String recipeName) {
    if (recipeList.getRecipe(recipeName) == null) {
      return "No recipe found with that name.";
    }
    recipeList.removeRecipe(recipeName);
    return "Recipe successfully removed.";
  }

  /**
   * Checks if all ingredients in a recipe are available in the Fridge.
   *
   * @param recipe the recipe to check
   * @return a message indicating whether all ingredients are available in the
   *         required quantities
   */
  public String checkIngredientsAvailability(Recipe recipe) {
    for (Map.Entry<String, Double> entry : recipe.getIngredients().entrySet()) {
      String ingredientName = entry.getKey();
      double requiredQuantity = entry.getValue();
      double availableQuantity = fridge.getTotalQuantityOfIngredient(ingredientName);

      if (availableQuantity < requiredQuantity) {
        return "Insufficient " + ingredientName + " in the Fridge. Needed: "
            + requiredQuantity + ", Available: " + availableQuantity;
      }
    }
    return "All ingredients are available in the required quantities.";
  }

  /**
   * Calculates the total cost of a recipe based on the cost of each ingredient
   * from the FoodList.
   *
   * @param recipe the recipe to calculate the cost for
   * @return the total cost of the recipe
   */
  public double calculateRecipeCost(Recipe recipe) {
    double totalCost = 0.0;
    for (Map.Entry<String, Double> entry : recipe.getIngredients().entrySet()) {
      String ingredientName = entry.getKey();
      Ingredient ingredient = foodList.getIngredientFromFoodList(ingredientName);
      if (ingredient != null) {
        totalCost += ingredient.getIngredientCost();
      }
    }
    return totalCost;
  }

  /**
   * Updates the quantity of a specific ingredient in a recipe.
   *
   * @param recipeName     the name of the recipe to update
   * @param ingredientName the name of the ingredient to update
   * @param quantity       the new quantity for the ingredient
   * @return a message indicating the outcome of the operation
   */
  public String updateRecipeIngredient(String recipeName, String ingredientName, double quantity) {
    Recipe recipe = recipeList.getRecipe(recipeName);
    if (recipe == null) {
      return "Recipe not found.";
    }
    if (!recipe.getIngredients().containsKey(ingredientName)) {
      return "Ingredient " + ingredientName + " not found in the recipe.";
    }
    recipe.addIngredient(ingredientName, quantity); // Updates quantity
    return "Ingredient quantity updated successfully.";
  }

  /**
   * Retrieves a recipe by its name from the RecipeList.
   *
   * @param recipeName the name of the recipe to retrieve
   * @return a message with recipe details, or an error message if not found
   */
  public String getRecipe(String recipeName) {
    Recipe recipe = recipeList.getRecipe(recipeName);
    if (recipe == null) {
      return "Recipe not found.";
    }
    return "Recipe: " + recipe.getRecipeName() + "\nDescription: " + recipe.getRecipeDescription() +
        "\nInstructions: " + recipe.getInstructions() +
        "\nEstimated Cost: " + calculateRecipeCost(recipe);
  }

  /**
   * Retrieves all recipes in the RecipeList.
   *
   * @return a formatted list of all recipes or a message if no recipes exist
   */
  public String getAllRecipes() {
    Map<String, Recipe> recipes = recipeList.getAllRecipes();
    if (recipes.isEmpty()) {
      return "No recipes available.";
    }
    StringBuilder allRecipes = new StringBuilder("All Recipes:\n");
    recipes.values().forEach(
        recipe -> allRecipes.append(recipe.getRecipeName())
            .append(" - ").append(recipe.getRecipeType()).append("\n"));
    return allRecipes.toString();
  }

  /**
   * Suggests recipes to the user based on matches of ingredient categories that
   * exist in the fridge.
   *
   * @return a list of recipe names that match the ingredient categories in the
   *         fridge
   */
  public List<String> suggestedRecipesBasedOnFridgeContents() {
    List<String> suggestedRecipes = new ArrayList<>();

    for (Recipe recipe : recipeList.getAllRecipes().values()) {
      int matchingCategories = 0; // Count of ingredients with sufficient quantity
      boolean isFullMatch = true; // Indicates if all ingredients match fully

      for (Map.Entry<String, Double> ingredientEntry : recipe.getIngredients().entrySet()) {
        String ingredientName = ingredientEntry.getKey();
        double requiredQuantity = ingredientEntry.getValue();
        double availableQuantity = fridge.getTotalQuantityOfIngredient(ingredientName);

        if (availableQuantity >= requiredQuantity) {
          matchingCategories++;
        } else {
          isFullMatch = false;
        }
      }

      // If all ingredients match with required quantity
      if (isFullMatch) {
        suggestedRecipes.add("Full match: You can make " + recipe.getRecipeName());
      } else if (matchingCategories >= recipe.getIngredients().size() / 2) {
        suggestedRecipes.add("Partial match: You have enough ingredients to partially make "
            + recipe.getRecipeName() + ".");
      }
    }

    return suggestedRecipes;
  }

  // IMportant: Need to add method that takes from multiple items!!!!

  /**
   * Removes the quantity of ingredients in a recipe from the Fridge.
   *
   * @param recipeName the name of the recipe to remove ingredients for
   * @return a message indicating the outcome of the operation
   */
  public String removeQuantityByRecipe(String recipeName) {

    Recipe recipe = recipeList.getRecipe(recipeName);

    // Check if recipe exists
    if (recipe == null) {
      return "Recipe not found.";
    }

    // Iterates through all ingredients in the recipe
    for (Map.Entry<String, Double> entry : recipe.getIngredients().entrySet()) {

      String ingredientName = entry.getKey();
      double requiredQuantity = entry.getValue();

      // Gets all specific instances of an ingredient in the fridge
      List<FridgeItem> fridgeItems = fridge.getAllFridgeItems().stream()
          .filter(item -> item.getIngredient().getIngredientName()
              .equals(ingredientName))
          .toList();

      boolean itemFound = false; // Tracks if an item was found

      // Iterates through all instances of the ingredient.
      for (FridgeItem fridgeItem : fridgeItems) {
        double availableQuantity = fridgeItem.getQuantity();

        if (availableQuantity >= requiredQuantity) {
          fridge.updateFridgeItemQuantityById(fridgeItem.getId(), -requiredQuantity);
          itemFound = true;
          break;
        }
      }
      if (!itemFound) {
        return "Insufficient " + ingredientName + " in the Fridge. Needed: "
            + requiredQuantity + ", Available: "
            + fridge.getTotalQuantityOfIngredient(ingredientName);
      }
    }
    return "Recipe ingredients removed from the fridge.";
  }

  /**
   * Tester for the RecipeManager and related classes.
   */
  public static void main(String[] args) {
    // Initialize required objects
    FoodList foodList = new FoodList();
    Fridge fridge = new Fridge();
    FridgeManager fridgeManager = new FridgeManager(fridge, foodList);
    RecipeList recipeList = new RecipeList();
    RecipeManager recipeManager = new RecipeManager(recipeList, fridge, foodList);

    // Add ingredients to FoodList
    foodList.addIngredient(new Ingredient("Tomato", "Vegetable", 200.0, "g", 5.0));
    foodList.addIngredient(new Ingredient("Cheese", "Dairy", 200.0, "g", 15.0));
    foodList.addIngredient(new Ingredient("Chicken", "Meat", 500.0, "g", 50.0));

    // Add sufficient quantities of FridgeItems to Fridge
    fridge.addFridgeItem(new FridgeItem(foodList.getIngredientFromFoodList("Tomato"),
        300.0, 25062024));
    fridge.addFridgeItem(new FridgeItem(foodList.getIngredientFromFoodList("Cheese"),
        100.0, 15072024));
    fridge.addFridgeItem(new FridgeItem(foodList.getIngredientFromFoodList("Chicken"),
        200.0, 10082024));

    // Create and add a recipe
    Recipe recipe = new Recipe("Chicken Salad", "A healthy chicken salad.",
        "Mix ingredients.", "Lunch");
    recipe.addIngredient("Tomato", 120.0);
    recipe.addIngredient("Chicken", 200.0);
    recipe.addIngredient("Cheese", 50.0);
    recipeManager.addRecipe(recipe);

    // Test checkIngredientsAvailability
    System.out.println(recipeManager.checkIngredientsAvailability(recipe));

    // Test calculateRecipeCost
    System.out.println("Total cost of recipe: " + recipeManager.calculateRecipeCost(recipe));

    // Test suggestedRecipesBasedOnFridgeContents
    System.out.println("Suggested Recipes: " +
        recipeManager.suggestedRecipesBasedOnFridgeContents());

    // Display Fridge Items sorted before removing quantities
    List<FridgeItem> fridgeItemsSortedBefore = fridgeManager.getAllFridgeItemsSorted();
    System.out.println("Fridge items sorted before recipe test:");
    for (FridgeItem item : fridgeItemsSortedBefore) {
      System.out.println(item.getIngredient().getIngredientName() + " - "
          + item.getIngredient().getIngredientCategory() + " - Expiration: "
          + item.getExpirationDate() + " - Quantity: " + item.getQuantity());
    }

    // Test removeQuantityByRecipe
    System.out.println(recipeManager.removeQuantityByRecipe("Chicken Salad"));

    // Display Fridge Items sorted after removing quantities
    List<FridgeItem> fridgeItemsSortedAfter = fridgeManager.getAllFridgeItemsSorted();
    System.out.println("Fridge items sorted after recipe test:");
    for (FridgeItem item : fridgeItemsSortedAfter) {
      System.out.println(item.getIngredient().getIngredientName() + " - "
          + item.getIngredient().getIngredientCategory() + " - Expiration: "
          + item.getExpirationDate() + " - Quantity: " + item.getQuantity());
    }

    // Test getAllRecipes
    System.out.println(recipeManager.getAllRecipes());
  }

}