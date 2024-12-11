package utilities;

import models.FridgeItem;

/**
 * Utility class for managing the quantity of FridgeItem objects.
 * I believe this is a non-used utility class, however I will keep it because of
 * the closeness to the deadline of the project.
 */
public class QuantityManager {

  /**
   * Adds a specified amount to the quantity of a FridgeItem.
   *
   * @param item   the FridgeItem to modify
   * @param amount the amount to add
   */
  public static void addQuantity(FridgeItem item, double amount) {
    item.setQuantity(item.getQuantity() + amount);
  }

  /**
   * Deducts a specified amount from the quantity of a FridgeItem.
   * If the amount exceeds the current quantity, sets the quantity to 0.
   *
   * @param item   the FridgeItem to modify
   * @param amount the amount to deduct
   */
  public static void deductQuantity(FridgeItem item, double amount) {
    double newQuantity = item.getQuantity() - amount;
    item.setQuantity(Math.max(newQuantity, 0));
  }

  /**
   * Sets the quantity of a FridgeItem.
   *
   * @param item     the FridgeItem to modify
   * @param quantity the new quantity to set
   */
  public static void setQuantity(FridgeItem item, double quantity) {
    item.setQuantity(quantity);
  }
}
