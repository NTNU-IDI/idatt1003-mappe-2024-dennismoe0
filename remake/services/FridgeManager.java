package services;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import models.FoodList;
import models.Fridge;
import models.FridgeItem;
import models.Ingredient;

/**
 * FridgeManager provides operations to add, remove, and update items in the
 * Fridge,
 * as well as retrieve sorted lists and specific details about ingredients.
 */
public class FridgeManager {
  private final Fridge fridge;
  private final FoodList foodList;

  /**
   * Constructs a FridgeManager with the specified Fridge and FoodList.
   *
   * @param fridge   the fridge to manage
   * @param foodList the food list to reference
   */
  public FridgeManager(Fridge fridge, FoodList foodList) {
    this.fridge = fridge;
    this.foodList = foodList;
  }

  /**
   * Adds a new FridgeItem to the fridge for an existing ingredient in the
   * FoodList.
   *
   * @param ingredientName the name of the ingredient to add
   * @param expirationDate the expiration date of the new FridgeItem
   */
  public void addToFridge(String ingredientName, long expirationDate) {
    Ingredient ingredient = foodList.getIngredientFromFoodList(ingredientName);
    if (ingredient == null) {
      throw new IllegalArgumentException("Ingredient not found in FoodList.");
    }
    FridgeItem newItem = new FridgeItem(ingredient,
        ingredient.getIngredientBaseWeight(), expirationDate);
    fridge.addFridgeItem(newItem);
  }

  /**
   * Creates a new Ingredient if it doesn't exist in FoodList, then adds it to the
   * fridge.
   *
   * @param ingredientName the name of the ingredient
   * @param ingredientType the type or category of the ingredient
   * @param baseWeight     the base weight of the ingredient
   * @param measuringUnit  the unit of measurement for the ingredient
   * @param cost           the cost per unit of the ingredient
   * @param expirationDate the expiration date of the ingredient to be added to
   *                       the fridge
   */
  public void createAndAddToFridge(String ingredientName, String ingredientType,
      double baseWeight, String measuringUnit, double cost,
      long expirationDate) {
    Ingredient ingredient = foodList.getIngredientFromFoodList(ingredientName);
    if (ingredient == null) {
      ingredient = new Ingredient(ingredientName, ingredientType, baseWeight, measuringUnit, cost);
      foodList.addIngredient(ingredient);
    }
    FridgeItem newItem = new FridgeItem(ingredient, baseWeight, expirationDate);
    fridge.addFridgeItem(newItem);
  }

  /**
   * Removes a FridgeItem by its ID from the fridge.
   *
   * @param id the unique ID of the FridgeItem to remove
   * @return true if successfully removed, false otherwise
   */
  public boolean removeFromFridgeById(int id) {
    return fridge.removeFridgeItemById(id);
  }

  /**
   * Updates the quantity of a specific FridgeItem by ID.
   *
   * @param id             the ID of the FridgeItem to update
   * @param quantityChange the amount to add or subtract
   * @return true if successfully updated, false otherwise
   */
  public boolean updateFridgeItemQuantityById(int id, double quantityChange) {
    return fridge.updateFridgeItemQuantityById(id, quantityChange);
  }

  /**
   * Sets a specific quantity for a FridgeItem by ID.
   *
   * @param id       the ID of the FridgeItem to update
   * @param quantity the new quantity to set
   * @return true if successfully updated, false otherwise
   */
  public boolean setFridgeItemQuantityById(int id, double quantity) {
    FridgeItem item = fridge.getFridgeItemById(id);
    if (item != null) {
      item.setQuantity(quantity);
      return true;
    }
    return false;
  }

  /**
   * Retrieves all FridgeItems sorted by a specified criterion.
   *
   * @param sortBy the criterion to sort by ("name", "expiration", "quantity")
   * @return a sorted list of FridgeItems
   */
  public List<FridgeItem> getAllFridgeItemsSorted(String sortBy) {
    List<FridgeItem> allItems = fridge.getAllFridgeItems();

    return switch (sortBy.toLowerCase()) {
      case "name" -> allItems.stream()
          .sorted(Comparator.comparing(item -> item.getIngredient().getIngredientName()))
          .collect(Collectors.toList());
      case "expiration" -> allItems.stream()
          .sorted(Comparator.comparing(FridgeItem::getExpirationDate))
          .collect(Collectors.toList());
      case "quantity" -> allItems.stream()
          .sorted(Comparator.comparing(FridgeItem::getQuantity))
          .collect(Collectors.toList());
      default -> allItems;
    };
  }

  /**
   * Retrieves all instances of a specific ingredient by name, sorted by
   * expiration date.
   *
   * @param ingredientName the name of the ingredient
   * @return a sorted list of FridgeItem instances
   */
  public List<FridgeItem> getAllInstancesOfIngredient(String ingredientName) {
    return fridge.getAllIngredientInstancesByName(ingredientName).stream()
        .sorted(Comparator.comparing(FridgeItem::getExpirationDate))
        .collect(Collectors.toList());
  }

  /**
   * Retrieves all expired items from the fridge.
   *
   * @param currentDate the current date to check for expiration
   * @return a list of expired FridgeItems
   */
  public List<FridgeItem> getAllExpiredItems(long currentDate) {
    return fridge.getAllFridgeItems().stream()
        .filter(item -> item.getExpirationDate() < currentDate)
        .collect(Collectors.toList());
  }

  /**
   * Calculates the total value of all expired items in the fridge.
   *
   * @param currentDate the current date to check for expiration
   * @return the total value of expired items
   */
  public double getExpiredItemsValue(long currentDate) {
    return getAllExpiredItems(currentDate).stream()
        .mapToDouble(item -> item.getQuantity() * item.getIngredient().getIngredientCost())
        .sum();
  }

  /**
   * Calculates the total value of all items in the fridge.
   *
   * @return the total value of items in the fridge
   */
  public double getTotalValueOfFridge() {
    return fridge.getAllFridgeItems().stream()
        .mapToDouble(item -> item.getQuantity() * item.getIngredient().getIngredientCost())
        .sum();
  }
}