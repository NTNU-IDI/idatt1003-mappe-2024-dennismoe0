package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing the Fridge. All Ingredients will be stored here with
 * FridgeItem instances.
 * The Fridge will contain a Map of Ingredients and their quantities.
 *
 * @author Dennis Moe
 */
public class Fridge {

  private final Map<String, List<FridgeItem>> fridgeContents; // Ingredients in Fridge.
  private final Map<String, Double> fridgeQuantities; // Quantity of each ingredient in Fridge.

  /**
   * Constructs a new Fridge.
   *
   * @param fridgeContents   the contents of the fridge. Key = Ingredient name,
   *                         Value = List of Ingredient instances.
   * @param fridgeQuantities the quantities of the ingredients in the fridge. Key
   *                         = Ingredient name, Value = Total quantity of
   *                         instances.
   */
  public Fridge() {
    fridgeContents = new HashMap<>();
    fridgeQuantities = new HashMap<>();
  }
}
