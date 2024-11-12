package services;

import models.CookBook;
import models.Recipe;
import models.RecipeList;
import java.util.Map;
import java.util.HashMap;

/**
 * Stores all CookBooks and manages everything related to CookBooks.
 * 
 * @author Dennis Moe
 */
public class CookBookManager {
  private final RecipeList recipeList;
  private final Map<String, CookBook> cookBooks;

  /**
   * Constructor for a new CookBookManager.
   */
  public CookBookManager() {
    this.recipeList = new RecipeList();
    this.cookBooks = new HashMap<>();
  }

  /**
   * Adds a CookBook to the CookBookManager through String name (Key).
   * CookBook object is stored as the value.
   *
   * @param cookBookName the name of the CookBook to add.
   * @return a message indicating success or failure.
   */
  public String createCookBook(String cookBookName, String cookBookDescription,
      String cookBookType) {
    if (cookBooks.containsKey(cookBookName)) {
      return "CookBook already exists! Delete it or use another name.";
    } else {
      cookBooks.put(cookBookName, new CookBook(cookBookName, cookBookDescription, cookBookType));
      return "CookBook created successfully!";
    }
  }

  /**
   * Deletes a CookBook by String cookBookName.
   *
   * @param cookBookName the name of the CookBook to delete.
   * @return a message indicating success or failure.
   */
  public String deleteCookBook(String cookBookName) {
    if (cookBooks.remove(cookBookName) != null) {
      return "CookBook deleted successfully!";
    } else {
      return "CookBook not found!";
    }
  }

  /**
   * Adds a recipe to specified CookBook.
   *
   * @param cookBookName String name of CookBook to add recipe to.
   * @param recipeName   String name of Recipe to add to CookBook.
   * @return a message indicating success or failure.
   */
  public String addRecipeToCookBook(String cookBookName, String recipeName) {
    CookBook cookBook = cookBooks.get(cookBookName);
    if (cookBook == null) {
      return "CookBook not found!";
    }

    // Retrieve the recipe name from the Recipe object
    Recipe recipe = recipeList.getRecipe(recipeName);
    if (recipe == null) {
      return "Recipe not found! Create it first, or check your spelling.";
    }

    if (cookBook.addRecipe(recipe)) {
      return "Recipe added to CookBook!";
    } else {
      return "Recipe already exists in CookBook!";
    }
  }

  /**
   * Removes a Recipe by name from a CookBook by name.
   *
   * @param cookBookName String name of CookBook to remove recipe from.
   * @param recipeName   String name of Recipe to remove from CookBook.
   * @return a message indicating success or failure.
   */
  public String removeRecipeFromCookBook(String cookBookName, String recipeName) {
    CookBook cookBook = cookBooks.get(cookBookName);
    if (cookBook == null) {
      return "CookBook not found!";
    }

    if (cookBook.removeRecipe(recipeName)) {
      return "Recipe removed from CookBook!";
    } else {
      return "Recipe not found in CookBook!";
    }
  }

  /**
   * Builds a String with all CookBooks.
   *
   * @return a StringBuilder with all CookBooks.
   */
  public StringBuilder listAllCookBooks() {
    StringBuilder allCookBooks = new StringBuilder();
    for (Map.Entry<String, CookBook> entry : cookBooks.entrySet()) {
      allCookBooks.append(entry.getKey()).append("\n");
    }
    return allCookBooks;
  }

  /**
   * Builds a String with all Recipes in a CookBook.
   *
   * @param cookBookName the name of the CookBook to list recipes from.
   * @return a StringBuilder with all Recipes in the CookBook.
   */
  public StringBuilder listAllRecipesInCookBook(String cookBookName) {
    StringBuilder allRecipesInCookBook = new StringBuilder();
    CookBook cookBook = cookBooks.get(cookBookName);
    if (cookBook == null) {
      return allRecipesInCookBook.append("CookBook not found!");
    } else {
      for (Map.Entry<String, Recipe> entry : cookBook.getRecipesInCookBook().entrySet()) {
        allRecipesInCookBook.append(entry.getKey()).append("\n");
      }
      return allRecipesInCookBook;
    }
  }
}