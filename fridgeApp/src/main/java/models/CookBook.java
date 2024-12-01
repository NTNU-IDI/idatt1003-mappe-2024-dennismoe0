package models;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing a CookBook object.
 * Stores recipes in a Map to allow for easy access through String and Recipe
 * objects.
 *
 * @author Dennis Moe
 */
public class CookBook {

  private final String cookBookName;
  private final String cookBookDescription;
  private final String cookBookType;
  private final Map<String, Recipe> recipesInCookBook;

  /**
   * Constructor for a new CookBook with a given name.
   *
   * @param cookBookName the name of the new CookBook.
   */
  public CookBook(String cookBookName, String cookBookDescription, String cookBookType) {
    this.cookBookName = cookBookName;
    this.cookBookDescription = cookBookDescription;
    this.cookBookType = cookBookType;
    this.recipesInCookBook = new HashMap<>();
  }

  /**
   * Gets the namme of the CookBook.
   *
   * @return a String with the name of the CookBook.
   */
  public String getCookBookName() {
    return cookBookName;
  }

  /**
   * Gets the description of the CookBook.
   *
   * @return a String with the description of the CookBook.
   */
  public String getCookBookDescription() {
    return cookBookDescription;
  }

  /**
   * Gets the type of the CookBook.
   *
   * @return a String with the type of the CookBook.
   */
  public String getCookBookType() {
    return cookBookType;
  }

  /**
   * Adds a recipe to the CookBook through String name (Key).
   * Recipe object is stored as the value.
   *
   * @param recipe the Recipe object to add to the CookBook.
   * @return true if the recipe was added, false if it already exists.
   */
  public boolean addRecipe(Recipe recipe) {
    if (!recipesInCookBook.containsKey(recipe.getRecipeName())) {
      recipesInCookBook.put(recipe.getRecipeName(), recipe);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Removes a recipe from the CookBook.
   *
   * @param recipeName the String name of the recipe to remove.
   * @return true if the recipe was removed, false if it was not found.
   */
  public boolean removeRecipe(String recipeName) {
    return recipesInCookBook.remove(recipeName) != null;
  }

  /**
   * Returns the name of a recipe in the CookBook.
   *
   * @param recipeName the name of the recipe to get.
   * @return a String with the recipe name.
   */
  public Recipe getRecipeFromCookBook(String recipeName) {
    return recipesInCookBook.get(recipeName);
  }

  /**
   * Checks if there is a recipe with a chosen name in the CookBook.
   *
   * @param recipeName the name of the recipe to check for.
   * @return true if the recipe is in the CookBook, false if it is not.
   */
  public boolean containsRecipe(String recipeName) {
    return recipesInCookBook.containsKey(recipeName);
  }

  /**
   * Getter for all the recipes in the CookBook.
   *
   * @return a new HashMap with all the recipes in the CookBook.
   */
  public Map<String, Recipe> getRecipesInCookBook() {
    return recipesInCookBook;
  }

  /**
   * Getter for the size of the CookBook (amount of recipes)
   *
   * @return an integer with the count of recipes in the CookBook.
   */
  public int getRecipeCount() {
    return recipesInCookBook.size();
  }
}