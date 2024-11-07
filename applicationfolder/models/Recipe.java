package applicationfolder.models;

import java.util.HashMap;

/**
 * Class that represents a recipe with a name, description and instructions
 * for making the recipe.
 *
 * @author Dennis Moe
 */
public class Recipe {

  private final String recipeName;
  private final String recipeDescription;
  private final String recipeInstructions;
  private final String recipeType;
  private final HashMap<Ingredient, Integer> ingredientsInRecipe;

  /**
   * Constructor to initialize Recipe.
   *
   * @param recipeName         Name of the recipe.
   * @param recipeDescription  Description of the recipe, such as ingredients
   *                           etc.
   * @param recipeInstructions How to go forward with the recipe to create the
   *                           dish.
   */
  public Recipe(String recipeName, String recipeDescription, String recipeInstructions,
      String recipeType) {
    this.recipeName = recipeName;
    this.recipeDescription = recipeDescription;
    this.recipeInstructions = recipeInstructions;
    this.recipeType = recipeType;
    this.ingredientsInRecipe = new HashMap<>();
  }

  // Method to add ingredients to the recipe
  /**
   * Adds ingredient and a specified amount to a recipe.
   *
   * @param ingredient Specified ingredient object to add.
   * @param amount     Specified amount of object to add.
   */
  public void addIngredientToRecipe(Ingredient ingredient, int amount) {
    ingredientsInRecipe.put(ingredient, amount); // add Ingredient and amount
  }

  /**
   * Removes a specified amount of an ingredient from the recipe.
   *
   * @param ingredient Specified ingredient object to remove.
   * @param amount     Specified amount of the ingredient to remove.
   */
  public void removeAmountOfIngredientFromRecipe(Ingredient ingredient, int amount) {
    ingredientsInRecipe.remove(ingredient, amount); // remove Ingredient and amount
  }

  /**
   * Deletes an ingredient from the recipe.
   *
   * @param ingredient Specified ingredient object to delete.
   */
  public void deleteIngredientFromRecipe(Ingredient ingredient) {
    ingredientsInRecipe.remove(ingredient); // remove Ingredient
  }

  // Getters
  public String getRecipeName() {
    return recipeName;
  }

  public String getRecipeDescription() {
    return recipeDescription;
  }

  public String getRecipeInstructions() {
    return recipeInstructions;
  }

  public String getRecipeType() {
    return recipeType;
  }

  public HashMap<Ingredient, Integer> getIngredientsInRecipe() {
    return ingredientsInRecipe;
  }

  @Override
  public String toString() { // Override to print full string
    return "Recipe: " + recipeName + "\r\n" + "Description: " + recipeDescription
        + "\r\n" + "Instructions: " + recipeInstructions + "\r\n" + "Recipe type: " + recipeType;
  }
}
