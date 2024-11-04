package applicationfolder.services;

import applicationfolder.models.CentralFoodList;
import applicationfolder.models.Fridge;
import applicationfolder.models.Ingredient;
import applicationfolder.utilities.DateUtility;
import java.text.ParseException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.HashMap;

/**
 * Manages operations related to the fridge and its contents, including adding
 * ingredients,
 * calculating total value, and retrieving ingredient details.
 */
public class FridgeManager {

  private Fridge fridge;
  private CentralFoodList centralFoodList;

  /**
   * Constructor for the FridgeManager class.
   *
   * @param fridge          The Fridge object that stores ingredients.
   * @param centralFoodList The CentralFoodList that stores ingredient details
   *                        like price.
   */
  public FridgeManager(Fridge fridge, CentralFoodList centralFoodList) {
    this.fridge = fridge;
    this.centralFoodList = centralFoodList;
  }

  /**
   * Adds a specified amount of an ingredient to the fridge.
   *
   * @param itemName The name of the ingredient to be added.
   * @param amount   The amount of the ingredient to be added.
   */
  public void addIngredientToFridge(String itemName, int amount) {
    // Checks if the ingredient already has an amount in the fridge.
    int currentAmount = fridge.getFridgeContents().getOrDefault(itemName, 0);

    // Adds the current amount (if any) and the new amount.
    fridge.getFridgeContents().put(itemName, currentAmount + amount);
    System.out.println(amount + " " + itemName + " has been added to the Fridge");
  }

  /**
   * Retrieves the contents of the fridge.
   *
   * @return A HashMap where the key is the name of the ingredient and the value
   *         is the amount.
   */
  public HashMap<String, Integer> getFridgeContent() {
    return fridge.getFridgeContents();
  }

  /**
   * Calculates the total value of the ingredients in the fridge by multiplying
   * the amount
   * of each ingredient by its cost per item.
   *
   * @return The total value of the items in the fridge.
   */
  public int findValueOfFridge() {
    int totalValue = 0;

    // Go through each ingredient in the fridge and compares itemName to the key
    // (i.e. "Apple")
    for (String itemName : fridge.getFridgeContents().keySet()) {
      int amount = fridge.getFridgeContents().get(itemName);

      // Gets the Ingredient object from the centralFoodList by the itemName String to
      // be used below.
      Ingredient ingredient = centralFoodList.getIngredientByName(itemName);

      // If the object exists it multiplies the amount in the fridge with the cost per
      // item and adds it to the total value.
      if (ingredient != null) {
        int pricePerItem = ingredient.getCostPerItem();
        totalValue += amount * pricePerItem;
      }
    }
    // Returns total value of fridge.
    return totalValue;
  }

  /**
   * Searches for an ingredient in the fridge and prints its amount.
   *
   * @param ingredient The Ingredient object to search for in the fridge.
   */
  public void findIngredientInFridge(Ingredient ingredient) {
    String itemName = ingredient.getItemName();

    // Use the item name to get the amount from the fridge.
    int amount = fridge.getIngredientAmount(itemName);

    // This should probably be moved to the client.!!!!!!!!!!!!!!!!!!!!!
    if (amount > 0) {
      System.out.println("You have " + amount + " * " + ingredient.getItemWeight()
          + " " + ingredient.getItemMeasuringUnit() + " of " + ingredient.getItemName() + ".");
    } else {
      System.out.println("You don't have any " + ingredient.getItemName() + " in the fridge.");
    }
  }

  /**
   * Finds all expired items in the fridge based on today's date.
   * Creates an ArrayList with the items using java.util.Date's easy methods.
   * Such as
   *
   * @return A list of names of expired ingredients.
   */
  public List<String> findExpiredItems() {
    List<String> expiredItems = new ArrayList<>();

    // Get the date today
    Date todaysDate = new Date();

    // Iterates for each item in the fridge by name
    for (String itemName : fridge.getFridgeContents().keySet()) {
      // Find matching Ingredient object
      Ingredient ingredient = centralFoodList.getIngredient(itemName);

      // If it exists, gets expiration date
      if (ingredient != null) {
        Date expirationDate = ingredient.getExpirationDate();

        // If the expiration date is before todays date it adds it to the list.
        if (expirationDate.before(todaysDate)) {
          expiredItems.add(itemName);
        }
      }
    }

    // Returns list of expired items.
    return expiredItems;
  }

  // Same logic as in findValueOfFridge()
  // However it uses the findExpiredItems method to find a list instead
  // of the contents of the fridge.
  public int findValueOfExpiredItems() {
    int expiredValue = 0;

    List<String> expiredItems = findExpiredItems();

    for (String itemName : expiredItems) {
      int amount = fridge.getFridgeContents().get(itemName); // Gets integer bc no .KeySet
      Ingredient ingredient = centralFoodList.getIngredientByName(itemName);

      if (ingredient != null) {
        int costPerItem = ingredient.getCostPerItem();
        expiredValue += amount * costPerItem;
      }
    }

    return expiredValue;
  }

}