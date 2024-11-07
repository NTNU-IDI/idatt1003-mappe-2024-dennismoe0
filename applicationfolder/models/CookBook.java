package applicationfolder.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a CookBook with a name, description, type, and a HashMap
 * that tracks which recipes are included in the CookBook.
 * Uses a boolean value to indicate the presence of a recipe.
 *
 * @author Dennis Moe
 */
public class CookBook {

  private final String cookBookName;
  private final String cookBookDescription;
  private final String cookBookType;
  private final HashMap<Recipe, Boolean> recipesInCookBook;

  /**
   * Constructs a new CookBook with the specified name, description, and type.
   *
   * @param cookBookName        the name of the cookbook
   * @param cookBookDescription a description of the cookbook
   * @param cookBookType        the type or category of the cookbook
   */
  public CookBook(String cookBookName, String cookBookDescription, String cookBookType) {
    this.cookBookName = cookBookName;
    this.cookBookDescription = cookBookDescription;
    this.cookBookType = cookBookType;
    this.recipesInCookBook = new HashMap<>();
  }

  /**
   * Adds a recipe to the CookBook, marking it as present with a boolean.
   *
   * @param recipe the Recipe to add to the cookbook
   */
  public void addRecipeToCookBook(Recipe recipe) {
    recipesInCookBook.put(recipe, true);
  }

  /**
   * Removes a recipe from the CookBook by setting its presence status to false.
   *
   * @param recipe the Recipe to remove from the cookbook
   */
  public void removeRecipeFromCookBook(Recipe recipe) {
    recipesInCookBook.put(recipe, false);
  }

  /**
   * Checks if a recipe is present in the CookBook.
   *
   * @param recipe the Recipe to check
   * @return true if the recipe is in the cookbook, false otherwise
   */
  public boolean isRecipeInCookBook(Recipe recipe) {
    return recipesInCookBook.getOrDefault(recipe, false);
  }

  /**
   * Gets the name of the CookBook.
   *
   * @return the name of the cookbook
   */
  public String getCookBookName() {
    return cookBookName;
  }

  /**
   * Gets the description of the CookBook.
   *
   * @return the description of the cookbook
   */
  public String getCookBookDescription() {
    return cookBookDescription;
  }

  /**
   * Gets the type or category of the CookBook.
   *
   * @return the type of the cookbook
   */
  public String getCookBookType() {
    return cookBookType;
  }

  public List<Recipe> getAllRecipesInCookBook() {
    List<Recipe> activeRecipes = new ArrayList<>();

    for (Map.Entry<Recipe, Boolean> entry : recipesInCookBook.entrySet()) {
      if (entry.getValue()) {
        activeRecipes.add(entry.getKey());
      }
    }
    return activeRecipes;
  }
}
