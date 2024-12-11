package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing the Fridge. All Ingredients are stored here as FridgeItem
 * instances.
 * The Fridge contains a Map of Ingredients and their quantities.
 *
 * @author Dennis Moe
 */
public class Fridge {
  
  private final Map<String, List<FridgeItem>> fridgeContents;
  
  private final Map<String, Double> fridgeQuantities;

  /**
   * Constructs a new Fridge instance.
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

    
    fridgeContents.computeIfAbsent(ingredientName, k -> new ArrayList<>()).add(fridgeItem);

    
    fridgeQuantities.put(ingredientName, calculateTotalQuantity(ingredientName));
  }

  /**
   * Removes a FridgeItem by its unique ID.
   * Goes through all FridgeItems in the Fridge and removes the one with the
   * matching ID.
   *
   * @param id the unique ID of the FridgeItem to remove.
   * @return true if the item was successfully removed, false if not found.
   */
  public boolean removeFridgeItemById(int id) {
    for (Map.Entry<String, List<FridgeItem>> entry : fridgeContents.entrySet()) {
      List<FridgeItem> items = entry.getValue();
      for (FridgeItem item : items) {
        if (item.getId() == id) {
          items.remove(item);

          fridgeQuantities.put(entry.getKey(), calculateTotalQuantity(entry.getKey()));

          if (items.isEmpty()) {
            fridgeContents.remove(entry.getKey());
            fridgeQuantities.remove(entry.getKey());
          }
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Recalculates the total quantity of a specific ingredient by summing the
   * quantity of all FridgeItems of that name.
   *
   * @param ingredientName the name of the ingredient.
   * @return the total quantity of the specified ingredient.
   */
  public double calculateTotalQuantity(String ingredientName) {
    List<FridgeItem> items = fridgeContents.getOrDefault(ingredientName, new ArrayList<>());
    return items.stream().mapToDouble(FridgeItem::getQuantity).sum();
  }

  /**
   * Retrieves a FridgeItem by its unique ID.
   *
   * @param id the unique ID of the FridgeItem.
   * @return the FridgeItem if found, null otherwise.
   */
  public FridgeItem getFridgeItemById(int id) {
    for (List<FridgeItem> items : fridgeContents.values()) {
      for (FridgeItem item : items) {
        if (item.getId() == id) {
          return item;
        }
      }
    }
    return null;
  }

  /**
   * Updates the quantity of a specific FridgeItem by its ID and recalculates the
   * total quantity.
   *
   * @param id             the ID of the FridgeItem to update.
   * @param quantityChange the amount to add (positive) or remove (negative).
   * @return true if the item was found and updated, false otherwise.
   */
  public boolean updateFridgeItemQuantityById(int id, double quantityChange) {
    for (List<FridgeItem> items : fridgeContents.values()) {
      for (FridgeItem item : items) {
        if (item.getId() == id) {
          item.setQuantity(item.getQuantity() + quantityChange);

          
          String ingredientName = item.getIngredient().getIngredientName();
          fridgeQuantities.put(ingredientName, calculateTotalQuantity(ingredientName));
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Retrieves all instances of a specific ingredient by its name.
   *
   * @param ingredientName the name of the ingredient.
   * @return a list of FridgeItem instances for the specified ingredient.
   */
  public List<FridgeItem> getAllIngredientInstancesByName(String ingredientName) {
    return fridgeContents.getOrDefault(ingredientName, new ArrayList<>());
  }

  /**
   * Gets total quantity of a specific ingredient in the Fridge by name.
   *
   * @param ingredientName the name of the ingredient.
   * @return the total quantity of the ingredient in the Fridge.
   */
  public double getTotalQuantityOfIngredient(String ingredientName) {
    return fridgeQuantities.getOrDefault(ingredientName, 0.0);
  }

  /**
   * Gets all FridgeItems in the Fridge.
   * Iterates through all items in the Fridge and adds them to a list.
   *
   * @return a list of all FridgeItems in the Fridge.
   */
  public List<FridgeItem> getAllFridgeItems() {
    List<FridgeItem> allItems = new ArrayList<>();
    for (List<FridgeItem> items : fridgeContents.values()) {
      allItems.addAll(items);
    }
    return allItems;
  }
}
