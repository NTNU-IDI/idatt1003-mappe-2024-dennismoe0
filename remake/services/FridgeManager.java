package services;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import models.FoodList;
import models.Fridge;
import models.FridgeItem;
import models.Ingredient;
import utilities.DateValidation;

/**
 * FridgeManager provides operations to add, remove, and update items in the
 * Fridge, as well as retrieve sorted lists and specific details about
 * ingredients.
 *
 * @author Dennis Moe
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
   * @return a message indicating the result of the operation
   */
  public String addToFridge(String ingredientName, long expirationDate) {
    Ingredient ingredient = foodList.getIngredientFromFoodList(ingredientName);
    if (ingredient == null) {
      return "Ingredient not found in FoodList.";
    }
    FridgeItem newItem = new FridgeItem(ingredient,
        ingredient.getIngredientBaseWeight(), expirationDate);
    fridge.addFridgeItem(newItem);
    return "Ingredient added to fridge successfully.";
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
   * @return a message indicating the result of the operation
   */
  public String createAndAddToFridge(String ingredientName,
      String ingredientType, double baseWeight,
      String measuringUnit, double cost, long expirationDate) {
    Ingredient ingredient = foodList.getIngredientFromFoodList(ingredientName);
    if (ingredient == null) {
      ingredient = new Ingredient(ingredientName, ingredientType, baseWeight, measuringUnit, cost);
      foodList.addIngredient(ingredient);
    }
    FridgeItem newItem = new FridgeItem(ingredient, baseWeight, expirationDate);
    fridge.addFridgeItem(newItem);
    return "Ingredient created and added to fridge successfully.";
  }

  /**
   * Removes a FridgeItem by its ID from the fridge.
   *
   * @param id the unique ID of the FridgeItem to remove
   * @return a message indicating the result of the operation
   */
  public String removeFromFridgeById(int id) {
    boolean removed = fridge.removeFridgeItemById(id);
    return removed ? "Fridge item removed successfully." : "Fridge item not found.";
  }

  /**
   * Updates the quantity of a specific FridgeItem by ID.
   *
   * @param id             the ID of the FridgeItem to update
   * @param quantityChange the amount to add or subtract
   * @return a message indicating the result of the operation
   */
  public String updateFridgeItemQuantityById(int id, double quantityChange) {
    boolean updated = fridge.updateFridgeItemQuantityById(id, quantityChange);
    return updated ? "Fridge item quantity updated successfully."
        : "Fridge item not found or insufficient quantity.";
  }

  /**
   * Sets a specific quantity for a FridgeItem by ID.
   *
   * @param id       the ID of the FridgeItem to update
   * @param quantity the new quantity to set
   * @return a message indicating the result of the operation
   */
  public String setFridgeItemQuantityById(int id, double quantity) {
    FridgeItem item = fridge.getFridgeItemById(id);
    if (item != null) {
      item.setQuantity(quantity);
      return "Fridge item quantity set successfully.";
    }
    return "Fridge item not found.";
  }

  /**
   * Retrieves all FridgeItems sorted by Category, Name, Expiration Date, and
   * Quantity.
   *
   * @return a sorted list of FridgeItems
   */
  public List<FridgeItem> getAllFridgeItemsSorted() {
    List<FridgeItem> allItems = fridge.getAllFridgeItems();

    // Sort by Quantity
    allItems.sort(Comparator.comparing(FridgeItem::getQuantity));
    // Sort by Expiration Date (overrides within the same Quantity order)
    allItems.sort(Comparator.comparing(FridgeItem::getExpirationDate));
    // Sort by Name (overrides within the same Expiration Date order)
    allItems.sort(Comparator.comparing(item -> item.getIngredient().getIngredientName()));
    // Sort by Type (overrides within the same Name order)
    allItems.sort(Comparator.comparing(item -> item.getIngredient().getIngredientCategory()));

    return allItems;
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
   * @return a list of expired FridgeItems
   */
  public List<FridgeItem> getAllExpiredItems() {
    long todayAsLong = DateValidation.getTodayAsLong();
    return fridge.getAllFridgeItems().stream()
        .filter(item -> item.getExpirationDate() < todayAsLong)
        .collect(Collectors.toList());
  }

  /**
   * Calculates the total value of all expired items in the fridge.
   *
   * @return a message with the total value of expired items
   */
  public String getExpiredItemsValue() {
    double value = getAllExpiredItems().stream()
        .mapToDouble(item -> item.getIngredient().getIngredientCost())
        .sum();
    return "Total value of expired items: " + value;
  }

  /**
   * Calculates the total value of all items in the fridge based on individual
   * item cost rather than quantity.
   *
   * @return a message with the total value of items in the fridge
   */
  public String getTotalValueOfFridge() {
    double totalValue = fridge.getAllFridgeItems().stream()
        .mapToDouble(item -> item.getIngredient().getIngredientCost())
        .sum();
    return "Total value of items in fridge: " + totalValue;
  }

  /**
   * Retrieves the total quantity of a specific ingredient
   * in the fridge using the same method from the Fridge class.
   *
   * @param ingredientName the name of the ingredient to sum up.
   * @return a double of the total quantity of the specified ingredient.
   */
  public double getTotalQuantityOfIngredient(String ingredientName) {
    return fridge.calculateTotalQuantity(ingredientName);
  }

  /**
   * Retrieves a list of quantities for a specific ingredient by name, including
   * their IDs.
   *
   * @param ingredientName the name of the ingredient
   * @return a string listing the IDs and quantities of the ingredient
   */
  public String getQuantityListOfIngredientWithId(String ingredientName) {
    List<FridgeItem> items = fridge.getAllIngredientInstancesByName(ingredientName);

    if (ingredientName == null) {
      return "No items found for " + ingredientName;
    }

    StringBuilder sb = new StringBuilder();
    for (FridgeItem item : items) {
      sb.append("ID: ").append(item.getId()).append(", Quantity: ").append(item.getQuantity())
          .append(item.getExpirationDate()).append("\n");
    }

    return sb.toString();
  }
}