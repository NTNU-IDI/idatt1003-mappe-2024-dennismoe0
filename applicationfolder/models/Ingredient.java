package applicationfolder.models;

import applicationfolder.utilities.UnitConverter;
import applicationfolder.utilities.DateUtility;
import java.text.ParseException;
import java.util.Date;

/**
 * Class that represents a food item (ingredient) with name, type, weight,
 * measuring unit, quantity, expiration date, and cost per item.
 *
 * @author Dennis Moe
 */
public class Ingredient {
  private final String itemName;
  private final String itemType;
  private final int itemWeight;
  private final String itemMeasuringUnit;
  private double quantity;
  private final Date expirationDate;
  private final int costPerItem;

  /**
   * Constructor to initialize Ingredient.
   *
   * @param itemName          Name of the food item/ingredient.
   * @param itemType          Type of ingredient, e.g., ground meat.
   * @param itemWeight        Weight of the item.
   * @param itemMeasuringUnit Measuring unit of ingredient, e.g., "g" for grams,
   *                          or "L" for liters.
   * @param quantity          Quantity of the ingredient.
   * @param expirationDate    Expiration date in string format "dd-MM-yyyy".
   * @param costPerItem       Cost per item in NOK.
   * @throws ParseException if the date string is invalid.
   */
  public Ingredient(String itemName, String itemType, int itemWeight,
      String itemMeasuringUnit, double quantity, String expirationDate,
      int costPerItem) throws ParseException {
    this.itemName = itemName;
    this.itemType = itemType;
    this.itemWeight = itemWeight;
    this.itemMeasuringUnit = itemMeasuringUnit;
    this.quantity = quantity;
    this.expirationDate = DateUtility.parseDate(expirationDate);
    this.costPerItem = costPerItem;
  }

  // Getters
  public String getItemName() {
    return itemName;
  }

  public String getItemType() {
    return itemType;
  }

  public int getItemWeight() {
    return itemWeight;
  }

  public String getItemMeasuringUnit() {
    return itemMeasuringUnit;
  }

  public double getQuantity() {
    return quantity;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public int getCostPerItem() {
    return costPerItem;
  }

  /**
   * Gets the formatted string of expirationDate.
   *
   * @return Expiration date in string format "dd-MM-yyyy".
   */
  public String getFormattedExpirationDate() {
    return DateUtility.formatDate(expirationDate);
  }

  /**
   * Deducts the specified amount from the quantity of the ingredient.
   *
   * @param amount        The amount to deduct.
   * @param measuringUnit The unit of the amount to deduct.
   * @return true if the quantity was successfully deducted, false otherwise.
   */
  public boolean deductQuantity(double amount, String measuringUnit) {
    // Converts the amount to the ingredient's base unit
    double amountInBaseUnit = UnitConverter.convertUnit(amount,
        measuringUnit, this.itemMeasuringUnit);

    // Check if there is enough to deduct from
    if (amountInBaseUnit <= this.quantity) {
      this.quantity -= amountInBaseUnit;
      return true;
    }
    return false; // Not enough quantity to deduct
  }

  /**
   * Combines the quantity of this ingredient with another ingredient of the same
   * type.
   *
   * @param otherIngredient The other ingredient to combine with.
   * @throws IllegalArgumentException if the ingredients do not have the same name
   *                                  or compatible units.
   */
  public void combineQuantity(Ingredient otherIngredient) {
    // Check if both ingredients have the same name
    if (!this.itemName.equalsIgnoreCase(otherIngredient.getItemName())) {
      throw new IllegalArgumentException("Ingredients must have the same name to combine.");
    }

    // Check if both ingredients have compatible units
    if (!UnitConverter.areUnitsCompatible(this.itemMeasuringUnit,
        otherIngredient.getItemMeasuringUnit())) {
      throw new IllegalArgumentException("Ingredients must have compatible units to combine.");
    }

    // Convert the other ingredient's quantity to this ingredient's unit and add it
    double otherQuantityInBaseUnit = UnitConverter.convertUnit(
        otherIngredient.getQuantity(),
        otherIngredient.getItemMeasuringUnit(), this.itemMeasuringUnit);
    this.quantity += otherQuantityInBaseUnit;
  }

  @Override
  public String toString() {
    return itemName + ": " + quantity + " " + itemMeasuringUnit
        + ", expires on " + getFormattedExpirationDate();
  }
}