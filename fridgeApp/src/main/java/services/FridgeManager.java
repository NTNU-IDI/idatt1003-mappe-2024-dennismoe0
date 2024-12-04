package services;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import models.FoodList;
import models.Fridge;
import models.FridgeItem;
import models.Ingredient;
import utilities.DateValidation;
import utilities.UnitUtility;

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
    if (String.valueOf(expirationDate).length() != 8) {
      return "Invalid expiration date.";
    }
    FridgeItem newItem = new FridgeItem(ingredient,
        ingredient.getIngredientBaseWeight(), expirationDate);
    fridge.addFridgeItem(newItem);
    return "Ingredient added to fridge successfully.";
  }

  // gets foodlist
  public FoodList getFoodList() {
    return foodList;
  }

  /**
   * Retrieves the cost of an ingredient from the FoodList.
   * Returns -1 if the ingredient is not found.
   *
   * @param ingredientName the name of the ingredient
   * @return the cost of the ingredient
   */
  public double getIngredientCost(String ingredientName) {
    Ingredient ingredient = foodList.getIngredientFromFoodList(ingredientName);
    if (ingredient == null) {
      return -1;
    }
    return ingredient.getIngredientCost();
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

    System.out.println("Attempting to retrieve ingredient: " + ingredientName);
    Ingredient ingredient = foodList.getIngredientFromFoodList(ingredientName);

    if (ingredient == null) {
      System.out.println("Ingredient not found, creating new ingredient...");
      ingredient = new Ingredient(ingredientName, ingredientType, baseWeight, measuringUnit, cost);
      System.out.println("Created ingredient: " + ingredient.getIngredientName());
      foodList.addIngredient(ingredient);
    }

    System.out.println("Adding ingredient to fridge: " + ingredient.getIngredientName());
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
   * Removes a FridgeItem by its ID from the fridge without returning a message.
   *
   * @param id the unique ID of the FridgeItem to remove
   */
  public void removeByIdWithoutString(int id) {
    fridge.removeFridgeItemById(id);
  }

  /**
   * Retrieves quantity information about a specific FridgeItem by ID.
   *
   * @param id the ID of the FridgeItem to get the quantity of.
   * @return a message containing the id and current quantity of the FridgeItem.
   */
  public String getQuantityInfoById(int id) {
    FridgeItem item = fridge.getFridgeItemById(id);
    if (item == null) {
      return "Fridge item not found.";
    }
    return "ID: " + item.getId() + ". Current quantity: "
        + item.getQuantity() + " " + item.getIngredient().getIngredientMeasuringUnit();
  }

  /**
   * Updates the quantity of a specific FridgeItem by ID.
   *
   * @param id             the ID of the FridgeItem to update
   * @param quantityChange the amount to add or subtract
   * @return a message indicating the result of the operation
   */
  public String updateFridgeItemQuantityById(int id, double quantityChange) {
    FridgeItem item = fridge.getFridgeItemById(id);
    if (item == null) {
      return "Fridge item not found.";
    }
    double newQuantity = item.getQuantity() + quantityChange;
    if (newQuantity <= 0) {
      fridge.removeFridgeItemById(id);
      return "Fridge item removed due to zero or negative quantity.";
    } else {
      item.setQuantity(newQuantity);
      return "Fridge item quantity updated successfully.";
    }
  }

  /**
   * Removes the specified quantity of an ingredient from the fridge,
   * prioritizing items with the earliest expiration date.
   *
   * @param ingredientName   the name of the ingredient to remove
   * @param requiredQuantity the quantity to remove
   * @param requiredUnit     the unit of the required quantity
   * @return a message indicating the outcome of the operation
   */
  public String removeIngredient(String ingredientName,
      double requiredQuantity, String requiredUnit) {
    List<FridgeItem> sortedItems = fridge.getAllIngredientInstancesByName(ingredientName).stream()
        .sorted(Comparator.comparing(FridgeItem::getExpirationDate))
        .toList();

    double remainingQuantity = requiredQuantity;

    for (FridgeItem item : sortedItems) {
      String itemUnit = item.getIngredient().getIngredientMeasuringUnit();
      double availableQuantity = UnitUtility.convertUnit(item.getQuantity(),
          itemUnit, requiredUnit);

      if (availableQuantity >= remainingQuantity) {
        double quantityToRemove = UnitUtility.convertUnit(remainingQuantity,
            requiredUnit, itemUnit);
        fridge.updateFridgeItemQuantityById(item.getId(), -quantityToRemove);
        remainingQuantity = 0;
        break;
      } else {
        double quantityToRemove = UnitUtility.convertUnit(availableQuantity,
            requiredUnit, itemUnit);
        fridge.updateFridgeItemQuantityById(item.getId(), -quantityToRemove);
        remainingQuantity -= availableQuantity;
      }
    }

    return remainingQuantity == 0
        ? "Ingredient removed successfully."
        : "Insufficient quantity to fulfill request.";
  }

  /**
   * Checks if an ingredient with the specified ID exists in the fridge.
   *
   * @param id the ID of the ingredient to check
   * @return {@code true} if the ingredient exists, {@code false} otherwise
   */
  public boolean checkIngredientIdValidity(int id) {
    return fridge.getFridgeItemById(id) != null;
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
      if (quantity == 0) {
        fridge.removeFridgeItemById(id);
        return "Fridge item removed.";
      }
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

  public List<FridgeItem> getAllFridgeItems() {
    return fridge.getAllFridgeItems();
  }

  /**
   * Prints all FridgeItems sorted by Category, Name, Expiration Date, and
   * Quantity.
   */
  public void printAllFridgeItemsSorted() {
    List<FridgeItem> sortedItems = getAllFridgeItemsSorted();
    sortedItems.forEach(item -> System.out.println(item));
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
        .filter(item -> DateValidation.compareDates(item.getExpirationDate(), todayAsLong) < 0)
        .collect(Collectors.toList());
  }

  /**
   * Prints all expired items in the fridge.
   */
  public void printAllExpiredItems() {
    List<FridgeItem> expiredItems = getAllExpiredItems();
    expiredItems.forEach(item -> System.out.println(item));
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
   * Retrieves the total quantity of a specific ingredient in the fridge,
   * converting units to the target unit if necessary.
   *
   * @param ingredientName the name of the ingredient
   * @param targetUnit     the desired unit for the quantity
   * @return the total quantity of the ingredient in the specified unit
   */
  public double getTotalQuantityOfIngredient(String ingredientName, String targetUnit) {
    return fridge.getAllIngredientInstancesByName(ingredientName).stream()
        .mapToDouble(item -> {
          String itemUnit = item.getIngredient().getIngredientMeasuringUnit();
          return UnitUtility.convertUnit(item.getQuantity(), itemUnit, targetUnit);
        })
        .sum();
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

  public static void main(String[] args) {
    // Initialize the FoodList and Fridge
    FoodList foodList = new FoodList();
    Fridge fridge = new Fridge();
    FridgeManager manager = new FridgeManager(fridge, foodList);

    // Add sample ingredients to the FoodList
    foodList.addIngredient(new Ingredient("Cheese", "Dairy", 500, "grams", 50.0));
    foodList.addIngredient(new Ingredient("Meat", "Protein", 1000, "grams", 120.0));
    foodList.addIngredient(new Ingredient("Apple", "Fruit", 1, "amount", 10.0));

    // Add sample FridgeItems with expiration dates
    manager.addToFridge("Cheese", 25012025);
    manager.addToFridge("Meat", 25012025);
    manager.addToFridge("Apple", 27012024);

    // Print all FridgeItems
    System.out.println("All Fridge Items:");
    fridge.getAllFridgeItems().forEach(System.out::println);

    // Print expired items
    System.out.println("\nExpired Items:");
    manager.getAllExpiredItems().forEach(System.out::println);

    // Print total value of expired items
    System.out.println("\n" + manager.getExpiredItemsValue());

    // Print total value of all items in the fridge
    System.out.println(manager.getTotalValueOfFridge());
  }
}