package applicationfolder.models;

import java.util.HashMap;

/**
 * Class representing a central list of all available ingredients.
 * This list serves as a reference for all possible ingredients in the system.
 */
public class CentralFoodList {

  /**
   * A HashMap to store ingredients,
   * where the key is the ingredient name and the value is the Ingredient object.
   */
  private HashMap<String, Ingredient> foodList;

  /**
   * Constructor to initialize an empty central food list.
   */
  public CentralFoodList() {
    this.foodList = new HashMap<>();
  }

  /**
   * Adds an ingredient to the central food list.
   *
   * @param ingredient the Ingredient object to be added
   */
  public void addIngredient(Ingredient ingredient) {
    foodList.put(ingredient.getItemName(), ingredient);
  }

  /**
   * Retrieves an ingredient from the central food list by its name.
   *
   * @param name the name of the ingredient to search for
   * @return the Ingredient object if found, or null if not found
   */
  public Ingredient getIngredient(String name) {
    return foodList.get(name);
  }
}
