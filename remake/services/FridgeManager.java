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
    if (String.valueOf(expirationDate).length() != 8) {
      return "Invalid expiration date.";
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
   * This doesnt work, gives same amount as value of all items?
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
   * This doesnt work, gives same amount as value of all items?
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

  public static void main(String[] args) {
    // Initialize the FoodList and Fridge
    FoodList foodList = new FoodList();
    Fridge fridge = new Fridge();
    FridgeManager manager = new FridgeManager(fridge, foodList);

    // Add sample ingredients to the FoodList
    foodList.addIngredient(new Ingredient("Cheese", "Dairy", 500, "grams", 50.0));
    foodList.addIngredient(new Ingredient("Meat", "Protein", 1000, "grams", 120.0));
    foodList.addIngredient(new Ingredient("Apple", "Fruit", 200, "grams", 10.0));

    // Add sample FridgeItems with expiration dates
    long today = DateValidation.getTodayAsLong();
    manager.addToFridge("Cheese", today - 1); // Expired yesterday
    manager.addToFridge("Meat", today + 5); // Expires in 5 days
    manager.addToFridge("Apple", today - 10); // Expired 10 days ago

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