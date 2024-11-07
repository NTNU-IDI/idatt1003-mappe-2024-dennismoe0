package applicationfolder.models;

import applicationfolder.utilities.DateUtility;
import java.text.ParseException;
import java.util.Date;

/**
 * Class that represents a food item (ingredient) with name, type, weight,
 * measuring unit, expiration date and cost per item.
 * I use "item" in the Ingredient class to seperate them from future Ingredient
 * methods.
 *
 * @author Dennis Moe
 */
public class Ingredient {
  private final String itemName;
  private final String itemType;
  private final int itemWeight;
  private final String itemMeasuringUnit;
  private final Date expirationDate;
  private final int costPerItem;

  /**
   * Constructor to initialise Ingredient.
   *
   * @param itemName          Name of the food item/ingredient.
   * @param itemType          Type of ingredient, i.e. ground meat.
   * @param itemWeight        Weight of the item.
   * @param itemMeasuringUnit Measuring unit of ingredient, i.e. g for grams, or L
   *                          for
   *                          liter.
   * @param expirationDate    Expiration date in string format dd-MM-yyyy.
   * @param costPerItem       Cost per item in NOK, i.e. 400 NOK for 400g of
   *                          ground
   *                          beef.
   * @throws ParseException if the date string is invalid.
   */
  public Ingredient(String itemName, String itemType, int itemWeight,
      String itemMeasuringUnit, String expirationDate, int costPerItem) throws ParseException {
    this.itemName = itemName;
    this.itemType = itemType;
    this.itemWeight = itemWeight;
    this.itemMeasuringUnit = itemMeasuringUnit;
    // Removed try-catch from the Ingredient class
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

  public Date getExpirationDate() {
    return expirationDate;
  }

  public int getCostPerItem() {
    return costPerItem;
  }

  /**
   * Gets the formatted string of expirationDate.
   *
   * @return Expiration date in string-format dd-MM-yyyy.
   */
  public String getFormattedExpirationDate() {
    return DateUtility.formatDate(expirationDate);
  }

  @Override
  public String toString() { // Override to print full String.
    return itemName;
  }

  /**
   * Main method to test the printing of an object.
   */
  public static void main(String[] args) {
    try {
      Ingredient groundChicken = new Ingredient("Ground Chicken", "Ground Meat",
          400, "grams", "15-11-2024", 45);
      System.out.println(groundChicken);
    } catch (ParseException e) {
      System.out.println("Invalid date format.");
    }
  }
}