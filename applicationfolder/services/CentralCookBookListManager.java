package applicationfolder.services;

import applicationfolder.models.CentralCookBookList;
import applicationfolder.models.CookBook;

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
    CookBook existingCookBook = centralCookBookList.getCookBookByName(cookBookName);

    if (existingCookBook != null) {
      return "'" + cookBookName + "' is already registered.";
    }

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
    CookBook existingCookBook = centralCookBookList.getCookBookByName(cookBookName);

    if (existingCookBook == null) {
      return "There is no cook book with that name.";
    }

    centralCookBookList.removeCookBook(existingCookBook);
    return "'" + cookBookName + "' removed from the registry.";
  }

  /**
   * Retrieves a CookBook by its name from the central list.
   *
   * @param cookBookName the name of the CookBook to retrieve
   * @return the CookBook if found; otherwise, null
   */
  public CookBook getCookBookByName(String cookBookName) {
    return centralCookBookList.getCookBookByName(cookBookName);
  }
}