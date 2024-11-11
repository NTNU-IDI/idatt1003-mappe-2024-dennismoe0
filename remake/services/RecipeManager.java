package services;

import java.util.Map;
import models.FoodList;
import models.Fridge;
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
}
