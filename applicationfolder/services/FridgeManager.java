package applicationfolder.services;

import applicationfolder.models.CentralFoodList;
import applicationfolder.models.Fridge;
import applicationfolder.models.Ingredient;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Manages operations related to the fridge and its contents, including adding
 * ingredients,
 * calculating total value, and retrieving ingredient details.
 *
 * @author Dennis Moe
 */
public class FridgeManager {

  private final Fridge fridge;
  private final CentralFoodList centralFoodList;

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
    System.out.println(amount + " " + itemName + " has been added to the fridge.");
  }

  /**
   * Removes a specified amount of an ingredient from the fridge.
   *
   * @param itemName The name of the ingredient to be removed.
   * @param amount   The amount to be removed.
   */
  public void removeIngredientFromFridge(String itemName, int amount) {
    // Checks if there is a preexisting amount
    int currentAmount = fridge.getFridgeContents().getOrDefault(this, 0);

    // Removes amount from fridge
    fridge.getFridgeContents().put(itemName, currentAmount - amount);
    System.err.println(amount + " " + itemName + " has been removed from the fridge.");
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
   * Might be replaced with .containsValue or .containsKey (HashMap)
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

    // Iterates for each item in the fridge by name
    for (String itemName : fridge.getFridgeContents().keySet()) {
      // Use the isExpired method to check if the item is expired
      if (isExpired(itemName)) {
        expiredItems.add(itemName);
      }
    }

    // Returns list of expired items
    return expiredItems;
  }

  // Specific ingredient
  public boolean isExpired(String itemName) {
    Ingredient ingredient = centralFoodList.getIngredientByName(itemName);
    if (ingredient == null) {
      // If the ingredient does not exist, assume it's not expired
      return false;
    }
    Date expirationDate = ingredient.getExpirationDate();
    if (expirationDate == null) {
      // If there's no expiration date, assume it's not expired
      return false;
    }
    Date currentDate = new Date();
    return currentDate.after(expirationDate);
  }

  /**
   * Find the value of all expired ingredients with the same logic as
   * FindValueOfFridge,
   * but iterates across the list made by findExpiredItems instead of the entire
   * fridge.
   *
   * @return Total value of expired ingredients in the fridge.
   */
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

  public static void main(String[] args) {

    Fridge fridge = new Fridge();
    CentralFoodList centralFoodList = new CentralFoodList();

    FridgeManager fridgeManager = new FridgeManager(fridge, centralFoodList);

    try {
      Ingredient wholeMilk = new Ingredient("Whole Milk", "Milk", 1000, "ml", "06-11-2024", 35);
      Ingredient groundBeef = new Ingredient("Ground Beef", "Meat", 400, "grams", "04-11-2024", 59);
      Ingredient pastaSheets = new Ingredient("Pasta Sheets", "Pasta",
          200, "grams", "01-02-2025", 20);
    } catch (ParseException e) {
      System.out.println("Invalid date format.");
    }

    centralFoodList.addIngredient(wholeMilk);

  }
}