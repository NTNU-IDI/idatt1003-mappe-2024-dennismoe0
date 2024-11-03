package applicationfolder.models;

import java.util.HashMap;

/**
 * Class representing a fridge that stores ingredients and tracks their quantities.
 */
public class Fridge {

  /**
   * A HashMap to store the ingredients currently in the fridge and their respective amounts.
   * The key is an Ingredient object,
   * and the value is an Integer representing the amount of that ingredient.
   */
  private final HashMap<Ingredient, Integer> fridgeContents;

  /**
   * Constructor to initialize the fridge with an empty HashMap 
   * for storing ingredients and their amounts.
   */
  public Fridge() {
    this.fridgeContents = new HashMap<>();
  }

  /**
   * MOVE TO FRIDGE MANAGER!!!!
   * !!!!!!!!!!!!!!!!!
   * 
   * Adds an ingredient to the fridge along with a specified amount.
   * If the ingredient already exists, it updates the amount.
   *
   * @param ingredient the Ingredient object to be added to the fridge.
   * @param amount     the amount of the ingredient to be added.
   */
  public void addIngredientToFridge(Ingredient ingredient, int amount) {
    fridgeContents.put(ingredient, amount);
  }

  // Copy to FridgeManager for clarity (do not remove here,
  // just add the getFridgeContents to another getter in Manager)
  // Gets entire content of the fridgeContents HashMap.
  public HashMap<Ingredient, Integer> getFridgeContents() {
    return fridgeContents;
  }

  /**
   * !!!!
   *  MOVE TO FRIDGE MANAGER
   * !!!!
   * 
   * Retrieves the amount of a specific ingredient in the fridge.
   * If the ingredient is not found, it returns 0.
   *
   * @param ingredient the Ingredient object to search for in the fridge.
   * @return the amount of the ingredient in the fridge, or 0 if not found.
   */
  public int getIngredientAmount(Ingredient ingredient) {
    return fridgeContents.getOrDefault(ingredient, 0);
  }
}
