package applicationfolder.models;

import java.util.HashMap;

/**
 * Class representing a central list of all available recipes.
 * Each recipe is stored as a HashMap entry with the recipe name as the key and
 * object as value.
 * This class provides methods to add, remove, and retrieve recipes by their
 * name.
 *
 * @author Dennis Moe
 */
public class CentralRecipeList {

  private final HashMap<String, Recipe> recipeList;

  /**
   * Constructor to initialize an empty central recipe list.
   * This creates a new HashMap where recipes are stored with their names as keys.
   */
  public CentralRecipeList() {
    this.recipeList = new HashMap<>();
  }

  /**
   * Adds a recipe to the central recipe list.
   * If a recipe with the same name already exists, it will be replaced.
   *
   * @param recipe The Recipe object to add to the list.
   */
  public void addRecipe(Recipe recipe) {
    recipeList.put(recipe.getRecipeName(), recipe);
  }

  /**
   * Removes a recipe from the central recipe list by its name.
   *
   * @param recipe The Recipe object to remove from the list.
   */
  public void removeRecipe(Recipe recipe) {
    recipeList.remove(recipe.getRecipeName());
  }

  /**
   * Retrieves a recipe from the central recipe list by its name.
   *
   * @param recipeName The name of the recipe to search for.
   * @return The Recipe object if found, or null if not found.
   */
  public Recipe getRecipeByName(String recipeName) {
    return recipeList.get(recipeName); // Retrieve the recipe using the recipeName as the key
  }
}