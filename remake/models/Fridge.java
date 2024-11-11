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

  /**
   * Adds a new instance of an Ingredient (FridgeItem) to the Fridge.
   *
   * @param fridgeItem the new instance of the Ingredient to add to the Fridge.
   */
  public void addFridgeItem(FridgeItem fridgeItem) {
    String ingredientName = fridgeItem.getIngredient().getIngredientName();

    fridgeContents.computeIfAbsent(ingredientName, k -> new ArrayList<>())
        .add(fridgeItem); // If ingredient hasnt been added already.

    fridgeQuantities.put(ingredientName, fridgeQuantities.getOrDefault(ingredientName,
        0.0) + fridgeItem.getQuantity()); // Adds quantity to total quantity.
  }

  /**
   * Retrieves all instances of a specific ingredient by its name.
   *
   * @param ingredientName the name of the ingredient to retrieve instances for.
   * @return a list of FridgeItem instances for the specified ingredient.
   */
  public List<FridgeItem> getAllIngredientInstancesByName(String ingredientName) {
    return fridgeContents.getOrDefault(ingredientName, new ArrayList<>());
  }

  /**
   * Retrieves the total quantity of a specific ingredient by its name.
   *
   * @param ingredientName the name of the ingredient to retrieve the total
   *                       quantity for.
   * @return the total quantity of the specified ingredient.
   */
  public double getTotalQuantityOfIngredient(String ingredientName) {
    return fridgeQuantities.getOrDefault(ingredientName, 0.0);
  }
}
