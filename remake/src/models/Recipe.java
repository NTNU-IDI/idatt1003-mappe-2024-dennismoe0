package models;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a recipe containing ingredients, quantities, instructions, and a
 * type.
 *
 * @author Dennis Moe
 */
public class Recipe {
  private final String recipeName;
  private final String recipeDescription;
  private final String instructions;
  private final String recipeType;
  private final Map<String, Double> ingredients; // Ingredient name and required quantity

  /**
   * Constructs a new Recipe.
   *
   * @param recipeName        the name of the recipe
   * @param recipeDescription a brief description of the recipe
   * @param instructions      the cooking instructions
   * @param recipeType        the type/category of the recipe, defined by the user
   */
  public Recipe(String recipeName, String recipeDescription,
      String instructions, String recipeType) {
    this.recipeName = recipeName;
    this.recipeDescription = recipeDescription;
    this.instructions = instructions;
    this.recipeType = recipeType;
    this.ingredients = new HashMap<>(); // The ingredients in the Recipe
  }

  // Getters
  public String getRecipeName() {
    return recipeName;
  }

  public String getRecipeDescription() {
    return recipeDescription;
  }

  public String getInstructions() {
    return instructions;
  }

  public String getRecipeType() {
    return recipeType;
  }

  public Map<String, Double> getIngredients() {
    return ingredients;
  }

  /**
   * Adds an ingredient with its required quantity to the recipe.
   *
   * @param ingredientName the name of the ingredient
   * @param quantity       the required quantity of the ingredient
   */
  public void addIngredient(String ingredientName, double quantity) {
    ingredients.put(ingredientName, quantity);
  }

  /**
   * Removes an ingredient from the recipe.
   *
   * @param ingredientName the name of the ingredient to remove
   */
  public void removeIngredient(String ingredientName) {
    ingredients.remove(ingredientName);
  }
}