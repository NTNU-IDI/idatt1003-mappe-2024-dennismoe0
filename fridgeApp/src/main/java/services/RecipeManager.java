package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import models.FoodList;
import models.FridgeItem;
import models.Ingredient;
import models.Recipe;
import models.RecipeList;
import utilities.DateValidation;

/**
 * Manages recipes and operations on the RecipeList.
 *
 * @author Dennis Moe
 */
public class RecipeManager {
  private final RecipeList recipeList;
  private final FridgeManager fridgeManager;
  private final FoodList foodList;

  /**
   * Constructs a new RecipeManager.
   *
   * @param recipeList    the RecipeList instance to manage
   * @param fridgeManager the FridgeManager instance to manage fridge operations
   */
  public RecipeManager(RecipeList recipeList, FridgeManager fridgeManager) {
    this.recipeList = recipeList;
    this.fridgeManager = fridgeManager;
    this.foodList = fridgeManager.getFoodList();
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

      // IDK IF THIS WILL WORK
      String ingredientUnit = foodList.getIngredientFromFoodList(ingredientName)
          .getIngredientMeasuringUnit();
      double availableQuantity = fridgeManager.getTotalQuantityOfIngredient(ingredientName,
          ingredientUnit);
      //

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
    return "Recipe: " + recipe.getRecipeName() + "\nDescription: " + recipe.getRecipeDescription()
        + "\nInstructions: " + recipe.getInstructions()
        + "\nEstimated Cost: " + calculateRecipeCost(recipe);
  }

  public Recipe getRecipeObject(String recipeName) {
    return recipeList.getRecipe(recipeName);
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

  // Check if this one still works with new methods iterating over multiple items
  // + unit conversion

  // Probably need to make methods that prints the lists, probably same issue
  // as with the fridge list.

  /**
   * Suggests recipes to the user based on matches of ingredient quantities that
   * exist in the fridge.
   *
   * @return a list of recipe suggestions indicating full or partial matches
   */
  public List<String> suggestedRecipesBasedOnFridgeContents() {
    List<String> suggestedRecipes = new ArrayList<>();

    for (Recipe recipe : recipeList.getAllRecipes().values()) {
      int matchingIngredients = 0; // Count of ingredients with sufficient quantity
      boolean isFullMatch = true; // Indicates if all ingredients match fully

      for (Map.Entry<String, Double> ingredientEntry : recipe.getIngredients().entrySet()) {
        String ingredientName = ingredientEntry.getKey();
        double requiredQuantity = ingredientEntry.getValue();

        // Use FridgeManager to calculate total available quantity with unit conversion
        String requiredUnit = foodList.getIngredientFromFoodList(ingredientName)
            .getIngredientMeasuringUnit();
        double availableQuantity = fridgeManager.getTotalQuantityOfIngredient(ingredientName,
            requiredUnit);

        // Check if the available quantity is sufficient
        if (availableQuantity >= requiredQuantity) {
          matchingIngredients++;
        } else {
          isFullMatch = false;
        }
      }

      // Add to suggestions based on match type
      if (isFullMatch) {
        suggestedRecipes.add("Full match: You can make " + recipe.getRecipeName());
      } else if (matchingIngredients >= recipe.getIngredients().size() / 2) {
        suggestedRecipes.add("Partial match: You have enough ingredients to partially make "
            + recipe.getRecipeName());
      }
    }

    return suggestedRecipes;
  }

  /**
   * Retrieves a list of recipes that can be fully fulfilled
   * based on the ingredients available in the fridge.
   *
   * @return a list of recipe names that are fully fulfilled
   */
  public List<String> fullyFulfilledRecipes() {
    List<String> fullyFulfilled = new ArrayList<>();

    for (Recipe recipe : recipeList.getAllRecipes().values()) {
      boolean isFullMatch = true; // Tracks if all ingredients match fully

      for (Map.Entry<String, Double> ingredientEntry : recipe.getIngredients().entrySet()) {
        String ingredientName = ingredientEntry.getKey();
        double requiredQuantity = ingredientEntry.getValue();

        // Get the required unit from the FoodList
        String requiredUnit = foodList.getIngredientFromFoodList(ingredientName)
            .getIngredientMeasuringUnit();
        double availableQuantity = fridgeManager.getTotalQuantityOfIngredient(ingredientName,
            requiredUnit);

        // If any ingredient doesn't meet the required quantity, break out
        if (availableQuantity < requiredQuantity) {
          isFullMatch = false;
          break;
        }
      }

      // Add recipe to the list if fully fulfilled
      if (isFullMatch) {
        fullyFulfilled.add(recipe.getRecipeName());
      }
    }

    return fullyFulfilled;
  }

  /**
   * Removes the quantities of ingredients required for a recipe from the fridge.
   *
   * @param recipeName the name of the recipe whose ingredients are to be removed
   * @return a message indicating the outcome of the operation
   */
  public String removeMultipleQuantitiesByRecipe(String recipeName) {
    Recipe recipe = recipeList.getRecipe(recipeName);

    // Check if recipe exists
    if (recipe == null) {
      return "Recipe not found.";
    }

    long todayAsLong = DateValidation.getTodayAsLong();

    // Checks if the quantity is sufficient for removal before iterating through
    // available items.
    for (Map.Entry<String, Double> entry : recipe.getIngredients().entrySet()) {
      String ingredientName = entry.getKey();
      double requiredQuantity = entry.getValue();

      // Calculate total available quantity for the ingredient
      double totalAvailableQuantity = fridgeManager.getAllFridgeItems().stream()
          .filter(item -> item.getIngredient().getIngredientName().equals(ingredientName))
          .mapToDouble(FridgeItem::getQuantity)
          .sum();

      if (totalAvailableQuantity < requiredQuantity) {
        return "Insufficient " + ingredientName + " in the Fridge. Needed: "
            + requiredQuantity + ", Available: " + totalAvailableQuantity;
      }
    }

    // Removal process after pre-check
    for (Map.Entry<String, Double> entry : recipe.getIngredients().entrySet()) {
      String ingredientName = entry.getKey();
      double requiredQuantity = entry.getValue();

      // Gets all items in the fridge sorted by expiration date
      // New version should not use expired items
      List<FridgeItem> fridgeItems = fridgeManager.getAllFridgeItems().stream()
          .filter(item -> item.getIngredient().getIngredientName().equals(ingredientName))
          // Excludes expired items from the list
          .filter(item -> DateValidation.compareDates(item.getExpirationDate(), todayAsLong) >= 0)
          .sorted((item1, item2) -> DateValidation.compareDates(
              item1.getExpirationDate(), item2.getExpirationDate()))
          .toList();

      for (FridgeItem fridgeItem : fridgeItems) {
        double availableQuantity = fridgeItem.getQuantity();

        if (availableQuantity >= requiredQuantity) {
          fridgeManager.updateFridgeItemQuantityById(fridgeItem.getId(), -requiredQuantity);
          requiredQuantity = 0; // If full removal achieved.

          if (fridgeItem.getQuantity() == 0) {
            // removes item if quantity is 0
            fridgeManager.removeByIdWithoutString(fridgeItem.getId());
          }
          break;
        } else {
          fridgeManager.updateFridgeItemQuantityById(fridgeItem.getId(), -availableQuantity);
          requiredQuantity -= availableQuantity; // Partial removal
        }
      }
    }
    return "Recipe ingredients removed from fridge, prioritizing items with earliest expiration.";
  }

}