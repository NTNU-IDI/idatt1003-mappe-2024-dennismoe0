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
   * Creates a new recipe with the specified ingredients.
   *
   * @param recipeName        the name of the recipe
   * @param recipeDescription the description of the recipe
   * @param instructions      the instructions for the recipe
   * @param recipeType        the type of the recipe
   * @param ingredients       the ingredients for the recipe with their quantities
   * @return a message indicating the outcome of the operation
   */
  public String createNewRecipeWithIngredients(String recipeName, String recipeDescription,
      String instructions, String recipeType, Map<String, Double> ingredients) {

    if (recipeList.getRecipe(recipeName) != null) {
      return "A recipe already exists with that name. Delete it or use another name.";
    }

    Recipe recipe = new Recipe(recipeName, recipeDescription, instructions, recipeType);
    for (Map.Entry<String, Double> entry : ingredients.entrySet()) {
      String ingredientName = entry.getKey();
      double quantity = entry.getValue();

      if (foodList.getIngredientFromFoodList(ingredientName) == null) {
        return "Ingredient " + ingredientName + " not found in the FoodList. Create it first.";
      }

      recipe.addIngredient(ingredientName, quantity);
    }
    recipeList.addRecipe(recipe);
    return "Successfully created the recipe.";
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
   * Adds an ingredient to a recipe.
   *
   * @param recipeName     the name of the recipe to add to.
   * @param ingredientName the name of the ingredient to add.
   * @return a message indicating the outcome of the operation.
   */
  public String addIngredientToRecipe(String recipeName, String ingredientName, double quantity) {
    Recipe recipe = recipeList.getRecipe(recipeName);
    if (recipe == null) {
      return "Recipe not found.";
    }
    if (recipe.getIngredients().containsKey(ingredientName)) {
      return "Ingredient " + ingredientName + " already exists in the recipe.";
    }
    recipe.addIngredient(ingredientName, quantity);
    return "Ingredient " + ingredientName + " added to recipe.";
  }

  /**
   * Removes a specific ingredient from a recipe.
   *
   * @param recipeName     the name of the recipe
   * @param ingredientName the name of the ingredient to remove
   * @return a message indicating the outcome of the operation
   */
  public String removeIngredientFromRecipe(String recipeName, String ingredientName) {
    Recipe recipe = recipeList.getRecipe(recipeName);
    if (recipe == null) {
      return "Recipe not found.";
    }
    if (!recipe.getIngredients().containsKey(ingredientName)) {
      return "Ingredient " + ingredientName + " not found in the recipe.";
    }
    recipe.removeIngredient(ingredientName);
    return ingredientName + " removed from recipe.";
  }

  /**
   * Retrieves a Recipe object by its name from the RecipeList.
   * With checks.
   *
   * @param recipeName the name of the recipe to retrieve
   * @return the Recipe object, or null if not found
   */
  public Recipe getRecipeObjectByName(String recipeName) {
    System.out.println("Getting recipe: " + recipeName);
    Recipe recipe = recipeList.getRecipe(recipeName);
    if (recipe == null) {
      System.out.println("Recipe not found: " + recipeName);
    }
    return recipe;
  }

  /**
   * Checks if all ingredients in a recipe are available in the Fridge.
   *
   * @param recipe the recipe to check
   * @return a message indicating whether all ingredients are available in the
   *         required quantities
   */
  public String checkIngredientsAvailability(Recipe recipe) {
    if (recipe == null) {
      return "Error: Recipe is null.";
    }

    for (Map.Entry<String, Double> entry : recipe.getIngredients().entrySet()) {
      String ingredientName = entry.getKey();
      double requiredQuantity = entry.getValue();

      String ingredientUnit = foodList.getIngredientFromFoodList(ingredientName)
          .getIngredientMeasuringUnit();
      double availableQuantity = fridgeManager.getTotalQuantityOfIngredient(ingredientName,
          ingredientUnit);

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
   * Calculates the total cost of the quantities of ingredients in a recipe.
   *
   * @param recipeName The name of the recipe to calculate the cost for.
   * @return The total cost of the recipe's ingredients.
   */
  public double costOfQuantitiesInRecipe(String recipeName) {
    Recipe recipe = recipeList.getRecipe(recipeName);
    if (recipe == null) {
      throw new IllegalArgumentException("Recipe not found: " + recipeName);
    }

    double totalCost = 0.0;

    for (Map.Entry<String, Double> entry : recipe.getIngredients().entrySet()) {
      String ingredientName = entry.getKey();
      double requiredQuantity = entry.getValue();

      Ingredient ingredient = fridgeManager.getFoodList().getIngredientFromFoodList(ingredientName);
      if (ingredient == null) {
        System.out.println("Ingredient not found: " + ingredientName);
        continue;
      }

      double ingredientCost = ingredient.getIngredientCost();
      double baseWeight = ingredient.getIngredientBaseWeight();


      double costForIngredient = (requiredQuantity / baseWeight) * ingredientCost;
      totalCost += costForIngredient;
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
    recipe.addIngredient(ingredientName, quantity);
    return "Ingredient quantity updated successfully.";
  }

  /**
   * Retrieves a recipe with info by its name from the RecipeList.
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

  /**
   * Retrieves the ingredients and their quantities for a specified recipe.
   *
   * @param recipeName the name of the recipe to retrieve ingredients for
   * @return a formatted string of ingredients and their quantities, or an error
   *         message if not found
   */
  public String getIngredientsInRecipe(String recipeName) {
    Recipe recipe = recipeList.getRecipe(recipeName);
    if (recipe == null) {
      return "Recipe not found.";
    }
    Map<String, Double> ingredients = recipe.getIngredients();

    StringBuilder ingredientList = new StringBuilder();

    for (Map.Entry<String, Double> entry : ingredients.entrySet()) {
      String ingredientName = entry.getKey();
      double quantity = entry.getValue();

      Ingredient ingredient = foodList.getIngredientFromFoodList(ingredientName);
      if (ingredient == null) {
        return "Ingredient " + ingredientName + " not found in the FoodList.";
      }

      String measuringUnit = ingredient.getIngredientMeasuringUnit();

      ingredientList.append(ingredientName).append(" -> ")
          .append(quantity).append(" ").append(measuringUnit).append("\n");
    }

    if (!ingredientList.isEmpty()) {
      ingredientList.setLength(ingredientList.length() - 2);
    }

    return ingredientList.toString();
  }

  /**
   * Retrieves a Recipe object by its name from the RecipeList.
   *
   * @param recipeName the name of the recipe to retrieve
   * @return the Recipe object, or null if not found
   */
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

  /**
   * Suggests recipes to the user based on matches of ingredient quantities that
   * exist in the fridge and includes missing ingredients (name only).
   *
   * @return a formatted string with recipe suggestions for partial matches only.
   */
  public String suggestedRecipesBasedOnFridgeContents() {
    StringBuilder suggestions = new StringBuilder();
    suggestions.append("Suggested Recipes Based on Fridge Contents (Partial Matches Only):\n");

    for (Recipe recipe : recipeList.getAllRecipes().values()) {
      int matchingIngredients = 0;
      List<String> missingIngredients = new ArrayList<>();

      for (Map.Entry<String, Double> ingredientEntry : recipe.getIngredients().entrySet()) {
        String ingredientName = ingredientEntry.getKey();
        double requiredQuantity = ingredientEntry.getValue();
        String requiredUnit = foodList.getIngredientFromFoodList(ingredientName)
            .getIngredientMeasuringUnit();


        double availableQuantityInRequiredUnit = fridgeManager
            .getTotalQuantityOfIngredient(ingredientName,
                requiredUnit);


        if (availableQuantityInRequiredUnit >= requiredQuantity) {
          matchingIngredients++;
        } else {

          missingIngredients.add(ingredientName);
        }
      }

      int totalIngredients = recipe.getIngredients().size();
      if (matchingIngredients > 0 && matchingIngredients < totalIngredients) {
        suggestions.append("---------------------------------------------------\n")
            .append("You can partially make ")
            .append(recipe.getRecipeName())
            .append(" (")
            .append(matchingIngredients)
            .append("/")
            .append(totalIngredients)
            .append(" ingredients available).\n");


        if (!missingIngredients.isEmpty()) {
          suggestions.append("You need to buy/add: ")
              .append(String.join(", ", missingIngredients))
              .append(".\n");
        }
      }
    }

    return suggestions.toString();
  }

  /**
   * Retrieves a list of recipes that can be fully fulfilled
   * based on the ingredients available in the fridge.
   *
   * @return a formatted string with recipe names and a message for fully
   *         fulfilled recipes
   */
  public String fullyFulfilledRecipes() {
    StringBuilder suggestions = new StringBuilder();
    suggestions.append("Recipes You Can Fully Make Based on Fridge Contents:\n");

    for (Recipe recipe : recipeList.getAllRecipes().values()) {
      boolean isFullMatch = true;

      for (Map.Entry<String, Double> ingredientEntry : recipe.getIngredients().entrySet()) {
        String ingredientName = ingredientEntry.getKey();
        double requiredQuantity = ingredientEntry.getValue();


        String requiredUnit = foodList.getIngredientFromFoodList(ingredientName)
            .getIngredientMeasuringUnit();
        double availableQuantity = fridgeManager
            .getTotalQuantityOfIngredient(ingredientName, requiredUnit);


        if (availableQuantity < requiredQuantity) {
          isFullMatch = false;
          break;
        }
      }


      if (isFullMatch) {
        suggestions.append("--------------------------\n")
            .append("You can fully make ")
            .append(recipe.getRecipeName())
            .append("\n");
      }
    }

    if (suggestions.toString()
            .trim().equals("Recipes You Can Fully Make Based on Fridge Contents:")) {
      suggestions.append("No recipes can be fully made with the current fridge contents.\n");
    }

    return suggestions.toString();
  }

  /**
   * Removes the quantities of ingredients required for a recipe from the fridge.
   *
   * @param recipeName the name of the recipe whose ingredients are to be removed
   * @return a message indicating the outcome of the operation
   */
  public String removeMultipleQuantitiesByRecipe(String recipeName) {
    Recipe recipe = recipeList.getRecipe(recipeName);


    if (recipe == null) {
      return "Recipe not found.";
    }

    long todayAsLong = DateValidation.getTodayAsLong();


    for (Map.Entry<String, Double> entry : recipe.getIngredients().entrySet()) {
      String ingredientName = entry.getKey();
      double requiredQuantity = entry.getValue();

      double totalAvailableQuantity = fridgeManager.getAllFridgeItems().stream()
          .filter(item -> item.getIngredient().getIngredientName().equals(ingredientName))
          .mapToDouble(FridgeItem::getQuantity)
          .sum();

      if (totalAvailableQuantity < requiredQuantity) {
        return "Insufficient " + ingredientName + " in the fridge. Needed: "
            + requiredQuantity + ", Available: " + totalAvailableQuantity;
      }
    }


    for (Map.Entry<String, Double> entry : recipe.getIngredients().entrySet()) {
      String ingredientName = entry.getKey();
      double requiredQuantity = entry.getValue();

      List<FridgeItem> fridgeItems = fridgeManager.getAllFridgeItems().stream()
          .filter(item -> item.getIngredient().getIngredientName().equals(ingredientName))
          .filter(item -> DateValidation.compareDates(item.getExpirationDate(), todayAsLong) >= 0)
          .sorted((item1, item2) -> DateValidation.compareDates(
              item1.getExpirationDate(), item2.getExpirationDate()))
          .toList();

      for (FridgeItem fridgeItem : fridgeItems) {
        double availableQuantity = fridgeItem.getQuantity();

        if (availableQuantity >= requiredQuantity) {

          fridgeManager.updateFridgeItemQuantityById(fridgeItem.getId(), -requiredQuantity);
          requiredQuantity = 0;

          if (fridgeItem.getQuantity() - requiredQuantity <= 0) {
            fridgeManager.removeByIdWithoutString(fridgeItem.getId());
          }
          break;
        } else {

          fridgeManager.updateFridgeItemQuantityById(fridgeItem.getId(), -availableQuantity);
          requiredQuantity -= availableQuantity;

          if (availableQuantity == 0) {
            fridgeManager.removeByIdWithoutString(fridgeItem.getId());
          }
        }
      }

      if (requiredQuantity > 0) {
        System.out.println("Error: Could not remove full quantity for " + ingredientName);
      }
    }

    return "Recipe ingredients removed from fridge, prioritizing items with earliest expiration.";
  }

  /**
   * Prints all recipes in the RecipeList.
   */
  public void printAllRecipes() {
    if (recipeList.getAllRecipes().isEmpty()) {
      System.out.println("No recipes found.");
      return;
    }

    System.out.println("All Recipes:");
    for (Recipe recipe : recipeList.getAllRecipes().values()) {
      System.out.println(getFormattedRecipeDetails(recipe.getRecipeName()));
      System.out.println("---------------------------------------------------");
    }
  }

  /**
   * Retrieves the formatted details of a recipe by its name.
   *
   * @param recipeName the name of the recipe to retrieve details for
   * @return a formatted string with the recipe details, or an error message if
   *         not found
   */
  public String getFormattedRecipeDetails(String recipeName) {
    Recipe recipe = recipeList.getRecipe(recipeName);
    if (recipe == null) {
      return "Recipe not found: " + recipeName;
    }

    StringBuilder sb = new StringBuilder();
    sb.append("Recipe Name: ").append(recipe.getRecipeName()).append("\n");
    sb.append("Description: ").append(recipe.getRecipeDescription()).append("\n");
    sb.append("Ingredients:\n");

    for (Map.Entry<String, Double> entry : recipe.getIngredients().entrySet()) {
      String ingredientName = entry.getKey();
      double quantity = entry.getValue();


      Ingredient ingredient = foodList.getIngredientFromFoodList(ingredientName);
      String unit = (ingredient != null) ? ingredient.getIngredientMeasuringUnit()
          : "Unit not found";

      sb.append("- ").append(ingredientName)
          .append(": ").append(quantity).append(" ").append(unit).append("\n");
    }

    sb.append("Instructions: ").append(recipe.getInstructions()).append("\n");
    return sb.toString();
  }

}