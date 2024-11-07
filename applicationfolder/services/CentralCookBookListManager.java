package applicationfolder.services;

import applicationfolder.models.CentralCookBookList;
import applicationfolder.models.CookBook;
import applicationfolder.models.Recipe;
import java.util.List;

/**
 * Manages the operations for a central list of CookBooks, allowing for
 * creating, adding, and removing CookBooks from the list.
 */
public class CentralCookBookListManager {
  private final CentralCookBookList centralCookBookList;

  /**
   * Initializes the manager with a specified CentralCookBookList.
   *
   * @param centralCookBookList the central list of CookBooks to manage
   */
  public CentralCookBookListManager(CentralCookBookList centralCookBookList) {
    this.centralCookBookList = centralCookBookList;
  }

  /**
   * Creates a new CookBook and adds it to the central list if it does not
   * already exist. If a CookBook with the given name exists, it does not
   * add a new one and returns a message.
   *
   * @param cookBookName        the name of the CookBook
   * @param cookBookDescription the description of the CookBook
   * @param cookBookType        the type or category of the CookBook
   * @return a message indicating if the CookBook was added or already exists
   */
  public String createAndAddCookBook(String cookBookName, String cookBookDescription,
      String cookBookType) {
    // Check if a CookBook with this name already exists
    CookBook existingCookBook = centralCookBookList.getCookBookByName(cookBookName);

    if (existingCookBook != null) {
      return "'" + cookBookName + "' is already registered.";
    }

    // Create and add a new CookBook if it does not exist
    CookBook newCookBook = new CookBook(cookBookName, cookBookDescription, cookBookType);
    centralCookBookList.addCookBook(newCookBook);

    return "An empty cook book called '" + cookBookName + "' has been added to the registry.";
  }

  /**
   * Removes an existing CookBook from the central list by its name. If no
   * CookBook with that name exists, it returns a message indicating this.
   *
   * @param cookBookName the name of the CookBook to remove
   * @return a message indicating if the CookBook was removed or not found
   */
  public String removeExistingCookBook(String cookBookName) {
    // Check if the CookBook exists before removing
    CookBook existingCookBook = centralCookBookList.getCookBookByName(cookBookName);

    if (existingCookBook == null) {
      return "There is no cook book with that name.";
    }

    // Proceed with removal if CookBook exists
    centralCookBookList.removeCookBook(existingCookBook);
    return "'" + cookBookName + "' removed from the registry.";
  }

  /**
   * Retrieves a CookBook by its name from the central list.
   *
   * @param cookBookName the name of the CookBook to retrieve
   * @return the CookBook if found; otherwise, returns a message if not found
   */
  public String getCookBookByName(String cookBookName) {
    // Check if the CookBook exists before retrieving
    CookBook cookBook = centralCookBookList.getCookBookByName(cookBookName);
    if (cookBook == null) {
      return "Cook book '" + cookBookName + "' does not exist.";
    }
    return cookBook.toString(); // or return cookBook directly if needed
  }

  /**
   * Lists all recipes in a specified CookBook.
   *
   * @param cookBookName the name of the CookBook
   * @return a message listing all recipes in the CookBook or indicating if the
   *         CookBook is empty or does not exist
   */
  public String listAllRecipesInCookBook(String cookBookName) {
    // Retrieve the CookBook by name and check if it exists
    CookBook targetCookBook = centralCookBookList.getCookBookByName(cookBookName);
    if (targetCookBook == null) {
      return "Cook book '" + cookBookName + "' does not exist.";
    }

    // Retrieve recipes in the CookBook
    List<Recipe> recipesInCookBook = targetCookBook.getAllRecipesInCookBook();

    // Format the output message based on the existence of recipes
    if (recipesInCookBook.isEmpty()) {
      return "Cook book '" + cookBookName + "' is empty.";
    } else {
      StringBuilder recipeList = new StringBuilder("Recipes in '" + cookBookName + "':\n");
      for (Recipe recipe : recipesInCookBook) {
        recipeList.append(" -> ").append(recipe.getRecipeName()).append("\n");
      }
      return recipeList.toString();
    }
  }
}