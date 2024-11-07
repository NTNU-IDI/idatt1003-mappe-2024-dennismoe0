package applicationfolder.services;

import applicationfolder.models.CentralRecipeList;
import applicationfolder.models.Recipe;

/**
 * Manager class for handling methods related to the CentralRecipeList.
 * This includes creating new recipes, adding them to the list,
 * removing recipes, and retrieving recipes by name.
 *
 * @author Dennis Moe
 */
public class CentralRecipeListManager {

  private final CentralRecipeList centralRecipeList;

  /**
   * Constructor to initialize the manager with a CentralRecipeList instance.
   *
   * @param centralRecipeList the instance of CentralRecipeList to manage.
   */
  public CentralRecipeListManager(CentralRecipeList centralRecipeList) {
    this.centralRecipeList = centralRecipeList;
  }

  /**
   * Creates a new recipe and adds it to the CentralRecipeList.
   * Initially, the recipe has no ingredients (empty recipe).
   *
   * @param recipeName         The name of the recipe to add.
   * @param recipeDescription  The description of the recipe.
   * @param recipeInstructions Instructions for preparing the recipe.
   * @param recipeType         The type of recipe (e.g., Dinner, Dessert).
   * @return A message indicating whether the recipe was added or if a recipe with
   *         the same name already exists.
   */
  public String createAndAddRecipe(String recipeName, String recipeDescription,
      String recipeInstructions, String recipeType) {

    Recipe existingRecipe = centralRecipeList.getRecipeByName(recipeName);

    // Check for preexisting recipe with the same name
    if (existingRecipe != null) {
      return "'" + recipeName + "' is already registered.";
    }

    // Create a new EMPTY recipe
    Recipe newRecipe = new Recipe(recipeName, recipeDescription, recipeInstructions, recipeType);

    // Add the recipe to the CentralRecipeList
    centralRecipeList.addRecipe(newRecipe);

    return "An empty recipe called '" + recipeName + "' has been added to the registry.";
  }

  /**
   * Removes an existing recipe from the CentralRecipeList by its name.
   *
   * @param recipeName The name of the recipe to remove.
   * @return A message indicating whether the recipe was successfully removed or
   *         if it was not found.
   */
  public String removeExistingRecipe(String recipeName) {
    Recipe existingRecipe = centralRecipeList.getRecipeByName(recipeName);

    // Check if the recipe exists
    if (existingRecipe == null) {
      return "There is no recipe with that name.";
    }

    // Remove the recipe and return success message
    centralRecipeList.removeRecipe(existingRecipe);
    return "'" + recipeName + "' removed from the registry.";
  }

  /**
   * Retrieves a recipe from the CentralRecipeList by its name.
   *
   * @param recipeName The name of the recipe to retrieve.
   * @return The Recipe object if found, or null if not found.
   */
  public Recipe getRecipeByName(String recipeName) {
    return centralRecipeList.getRecipeByName(recipeName);
  }
}