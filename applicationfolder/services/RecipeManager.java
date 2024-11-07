package applicationfolder.services;

import applicationfolder.models.Recipe;
import applicationfolder.models.CentralRecipeList;
import applicationfolder.models.Ingredient;
import java.util.Map;

/**
 * Class to manage and check specific things related to the Recipe and
 * CentralRecipeList classes.
 */
public class RecipeManager {

  private final CentralRecipeList centralRecipeList;
  private final FridgeManager fridgeManager;

  public RecipeManager(CentralRecipeList centralRecipeList, FridgeManager fridgeManager) {
    this.centralRecipeList = centralRecipeList;
    this.fridgeManager = fridgeManager;
  }

  // Adds specific ingredient to specific recipe

  public String addIngredientToRecipe(String recipeName, Ingredient ingredient, int amount) {
    Recipe targetRecipe = centralRecipeList.getRecipeByName(recipeName);

    if (targetRecipe == null) {
      return "There is no recipe named '" + recipeName + "'";
    }

    targetRecipe.addIngredientToRecipe(ingredient, amount);
    return "Added " + amount + " of '" + ingredient.getItemName() + " to recipe.";
  }

  /**
   * Checks if there are enough ingredients for a given recipe and if any
   * ingredients are expired.
   *
   * @param recipeName the name of the recipe to check
   * @return a message indicating whether there are enough ingredients and if any
   *         are expired
   */
  public String enoughIngredientsForRecipe(String recipeName) {
    // Retrieves the Recipe object from the central recipe list by name.
    Recipe recipe = centralRecipeList.getRecipeByName(recipeName);

    // Checks if it exists.
    if (recipe == null) {
      return "There is no recipe named '" + recipeName + "'";
    }

    // Map of required ingredients for recipe.
    Map<Ingredient, Integer> requiredIngredients = recipe.getIngredientsInRecipe();
    // Map of ingredients in fridge.
    Map<String, Integer> fridgeContents = fridgeManager.getFridgeContent();

    StringBuilder missingItems = new StringBuilder();
    StringBuilder expiredItems = new StringBuilder();

    // Loops through each required ingredient.
    for (Map.Entry<Ingredient, Integer> entry : requiredIngredients.entrySet()) {
      Ingredient ingredient = entry.getKey(); // retrieves ingredient object
      int requiredAmount = entry.getValue(); // retieves required amount

      // retrieves amount in fridge
      int availableAmount = fridgeContents.getOrDefault(ingredient.getItemName(), 0);

      // If not enough in fridge
      if (availableAmount < requiredAmount) {
        if (missingItems.length() > 0) {
          missingItems.append(", ");
        }
        missingItems.append("Missing: ")
            .append(requiredAmount - availableAmount) // Amount missing
            .append(ingredient.getItemMeasuringUnit()) // Measuring unit
            .append(" of ")
            .append(ingredient.getItemName()); // Ingredient name
      }

      // Checks if ingredient is expired, if it is, adds to expiredItems
      if (fridgeManager.isExpired(ingredient.getItemName())) {
        if (expiredItems.length() > 0) {
          expiredItems.append(", ");
        }
        expiredItems.append(ingredient.getItemName()).append(" is expired.");
      }
    }

    // Builds final string
    if (missingItems.length() == 0 && expiredItems.length() == 0) {
      return "You have enough ingredients for " + recipeName;
    } else {
      return "You are missing: " + missingItems + ". And these are expired: " + expiredItems;
    }
  }

  /**
   * Removes an ingredient from a recipe.
   *
   * @param recipeName the name of the recipe to remove the ingredient from
   * @param ingredient the ingredient to remove
   * @param amount     amount of ingredient to be removed.
   * @return a message indicating whether the ingredient was removed or not
   */
  public String removeIngredientFromRecipe(String recipeName, Ingredient ingredient, int amount) {
    Recipe targetRecipe = centralRecipeList.getRecipeByName(recipeName);

    if (targetRecipe == null) {
      return "There is no recipe named '" + recipeName + "'";
    }

    targetRecipe.removeAmountOfIngredientFromRecipe(ingredient, amount);
    return "Removed " + amount + "of " + ingredient.getItemName() + " from " + recipeName;
  }

  public String deleteIngredientFromRecipe(String recipeName, Ingredient ingredient) {
    Recipe targetRecipe = centralRecipeList.getRecipeByName(recipeName);

    if (targetRecipe == null) {
      return "There is no recipe named '" + recipeName + "'";
    }

    targetRecipe.deleteIngredientFromRecipe(ingredient);
    return "Deleted " + ingredient.getItemName() + " from " + recipeName;
  }

  public String deleteRecipe(String recipeName) {
    Recipe targetRecipe = centralRecipeList.getRecipeByName(recipeName);

    if (targetRecipe == null) {
      return "There is no recipe named '" + recipeName + "'";
    }

    centralRecipeList.removeRecipe(targetRecipe);
    return "Deleted recipe named '" + recipeName + "'";
  }

  /**
   * Creates a new recipe from the given ingredients.
   *
   * @param recipeName         the name of the recipe
   * @param recipeDescription  the description of the recipe
   * @param recipeInstructions the instructions for the recipe
   * @param recipeType         the type of the recipe
   * @param ingredients        the ingredients for the recipe
   * @return the created Recipe object
   */
  public Recipe createRecipeFromIngredientInput(String recipeName, String recipeDescription,
      String recipeInstructions, String recipeType, Map<Ingredient, Integer> ingredients) {
    Recipe newRecipe = new Recipe(recipeName, recipeDescription, recipeInstructions, recipeType);

    for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
      newRecipe.addIngredientToRecipe(entry.getKey(), entry.getValue());
    }

    centralRecipeList.addRecipe(newRecipe);
    return newRecipe;
  }

  public String weightOfRecipe(String recipeName) {
    Recipe recipe = centralRecipeList.getRecipeByName(recipeName);

    if (recipe == null) {
      return "There is no recipe named '" + recipeName + "'";
    }

    int totalWeight = 0;
    Map<Ingredient, Integer> ingredients = recipe.getIngredientsInRecipe();

    for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
      totalWeight += entry.getKey().getItemWeight() * entry.getValue();
    }

    return "The total weight of the recipe '" + recipeName + "' is " + totalWeight + " grams.";
  }

  public String valueOfRecipe(String recipeName) {
    Recipe recipe = centralRecipeList.getRecipeByName(recipeName);

    if (recipe == null) {
      return "There is no recipe named '" + recipeName + "'";
    }

    int totalValue = 0;
    Map<Ingredient, Integer> ingredients = recipe.getIngredientsInRecipe();

    for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
      totalValue += entry.getKey().getCostPerItem() * entry.getValue();
    }

    return "The total value of the recipe '" + recipeName + "' is " + totalValue + " currency units.";
  }
}