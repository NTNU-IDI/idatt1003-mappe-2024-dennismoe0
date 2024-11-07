package applicationfolder.services;

import applicationfolder.models.CookBook;
import applicationfolder.models.CentralCookBookList;
import applicationfolder.models.Recipe;
import applicationfolder.models.Ingredient;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Class to manage and check specific things related to the CookBook and
 * CentralCookBookList classes.
 */
public class CookBookManager {
  private final CentralCookBookList centralCookBookList;

  public CookBookManager(CentralCookBookList centralCookBookList) {
    this.centralCookBookList = centralCookBookList;
  }

  // Adds a new cookbook to the central list
  public String addCookBook(CookBook cookBook) {
    if (centralCookBookList.getCookBookByName(cookBook.getCookBookName()) != null) {
      return "A cookbook with the name '" + cookBook.getCookBookName() + "' already exists.";
    }
    centralCookBookList.addCookBook(cookBook);
    return "Cookbook '" + cookBook.getCookBookName() + "' added successfully.";
  }

  // Removes a cookbook from the central list
  public String removeCookBook(String cookBookName) {
    CookBook cookBook = centralCookBookList.getCookBookByName(cookBookName);
    if (cookBook == null) {
      return "There is no cookbook named '" + cookBookName + "'";
    }
    centralCookBookList.removeCookBook(cookBook);
    return "Cookbook '" + cookBookName + "' removed successfully.";
  }

  // Searches for a cookbook by name
  public CookBook searchCookBookByName(String cookBookName) {
    return centralCookBookList.getCookBookByName(cookBookName);
  }

  // Lists all cookbooks in the central list
  public List<CookBook> listAllCookBooks() {
    return new ArrayList<>(centralCookBookList.getAllCookBooksInList().values());
  }

  // Adds a recipe to a specific cookbook
  public String addRecipeToCookBook(String cookBookName, Recipe recipe) {
    CookBook targetCookBook = centralCookBookList.getCookBookByName(cookBookName);
    if (targetCookBook == null) {
      return "There is no cookbook named '" + cookBookName + "'";
    }
    targetCookBook.addRecipeToCookBook(recipe);
    return "Added recipe '" + recipe.getRecipeName() + "' to cookbook '" + cookBookName + "'.";
  }

  // Removes a recipe from a specific cookbook
  public String removeRecipeFromCookBook(String cookBookName, Recipe recipe) {
    CookBook targetCookBook = centralCookBookList.getCookBookByName(cookBookName);
    if (targetCookBook == null) {
      return "There is no cookbook named '" + cookBookName + "'";
    }
    targetCookBook.removeRecipeFromCookBook(recipe);
    return "Removed recipe '" + recipe.getRecipeName() + "' from cookbook '" + cookBookName + "'.";
  }

}
