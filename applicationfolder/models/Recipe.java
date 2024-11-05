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
  private HashMap<Ingredient, Integer> ingredientsInRecipe;

  /**
   * Constructor to initialize Recipe.
   *
   * @param recipeName         Name of the recipe.
   * @param recipeDescription  Description of the recipe, such as ingredients etc.
   * @param recipeInstructions How to go forward with the recipe to create the
   *                           dish.
   */
  public Recipe(String recipeName, String recipeDescription, String recipeInstructions) {
    this.recipeName = recipeName;
    this.recipeDescription = recipeDescription;
    this.recipeInstructions = recipeInstructions;
    this.ingredientsInRecipe = new HashMap<>();
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

  public HashMap<Ingredient, Integer> getIngredientsInRecipe() {
    return ingredientsInRecipe;
  }

  @Override
  public String toString() { // Override to print full string
    return "Recipe: " + recipeName + "\r\n" + "Description: " + recipeDescription
        + "\r\n" + "Instructions: " + recipeInstructions;
  }

  /**
   * Small tester
   * 
   * @param args
   */
  public static void main(String[] args) {
    Recipe beefLasagna = new Recipe("Beef Lasagna", "Lasagna with ground beef", "Ooga booka");
    System.out.println(beefLasagna);
  }

}
