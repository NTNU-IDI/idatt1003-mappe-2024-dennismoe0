package models;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a list of recipes.
 *
 * @author Dennis Moe
 */
public class RecipeList {
  private final Map<String, Recipe> recipes; // Maps recipe names to Recipe objects

  /**
   * Constructs a new RecipeList.
   */
  public RecipeList() {
    recipes = new HashMap<>();
  }

  /**
   * Adds a new recipe to the list.
   *
   * @param recipe the recipe to add
   */
  public void addRecipe(Recipe recipe) {
    recipes.put(recipe.getRecipeName().trim().toLowerCase(), recipe);
  }

  /**
   * Removes a recipe from the list by name.
   *
   * @param recipeName the name of the recipe to remove
   */
  public void removeRecipe(String recipeName) {
    recipes.remove(recipeName);
  }

  /**
   * Retrieves a recipe by name.
   *
   * @param recipeName the name of the recipe to retrieve
   * @return the recipe, or null if it does not exist
   */
  public Recipe getRecipe(String recipeName) {
    return recipes.get(recipeName.trim().toLowerCase());
  }

  /**
   * Retrieves all recipes in the list.
   *
   * @return a map of recipe names and Recipe objects
   */
  public Map<String, Recipe> getAllRecipes() {
    return recipes;
  }
}
