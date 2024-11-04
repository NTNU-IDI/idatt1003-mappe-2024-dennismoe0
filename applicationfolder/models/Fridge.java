package applicationfolder.models;

import java.util.HashMap;

/**
 * Class representing a fridge that stores ingredients and tracks their
 * quantities.
 */
public class Fridge {

  /**
   * A HashMap to store the ingredients currently in the fridge and their
   * respective amounts.
   * The key is the item name (String),
   * and the value is an Integer representing the amount of that ingredient.
   */
  private final HashMap<String, Integer> fridgeContents;

  /**
   * Constructor to initialize the fridge with an empty HashMap
   * for storing ingredients and their amounts.
   */
  public Fridge() {
    this.fridgeContents = new HashMap<>();
  }

  // Gets entire content of the fridgeContents HashMap.
  public HashMap<String, Integer> getFridgeContents() {
    return fridgeContents;
  }

  /**
   * Retrieves the amount of a specific ingredient in the fridge by name.
   * If the ingredient is not found, it returns 0.
   *
   * @param itemName the name of the ingredient to search for in the fridge.
   * @return the amount of the ingredient in the fridge, or 0 if not found.
   */
  public int getIngredientAmount(String itemName) {
    return fridgeContents.getOrDefault(itemName, 0);
  }
}