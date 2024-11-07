package applicationfolder.models;

import java.util.HashMap;

/**
 * Represents a central list holding all available CookBooks by their names.
 * This class allows adding, removing, and retrieving CookBooks by name.
 */
public class CentralCookBookList {

  private final HashMap<String, CookBook> cookBookList;

  /**
   * Creates a new CentralCookBookList with an empty collection of CookBooks.
   */
  public CentralCookBookList() {
    this.cookBookList = new HashMap<>();
  }

  /**
   * Adds a CookBook to the central list by its name.
   * If a CookBook with the same name already exists, it will be replaced.
   *
   * @param cookBook the CookBook to add or replace in the list
   */
  public void addCookBook(CookBook cookBook) {
    cookBookList.put(cookBook.getCookBookName(), cookBook);
  }

  /**
   * Removes a CookBook from the central list by its name.
   * If the CookBook is not in the list, no action is taken.
   *
   * @param cookBook the CookBook to remove from the list
   */
  public void removeCookBook(CookBook cookBook) {
    cookBookList.remove(cookBook.getCookBookName());
  }

  /**
   * Retrieves a CookBook by its name.
   *
   * @param cookBookName the name of the CookBook to retrieve
   * @return the CookBook if found; otherwise, returns null
   */
  public CookBook getCookBookByName(String cookBookName) {
    return cookBookList.get(cookBookName);
  }

  /**
   * Retrieves all CookBooks in the central list.
   *
   * @return a collection of all CookBooks in the list
   */
  public HashMap<String, CookBook> getAllCookBooksInList() {
    return cookBookList;
  }
}