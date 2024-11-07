package applicationfolder.services;

import applicationfolder.models.CentralFoodList;
import applicationfolder.models.Ingredient;
import java.text.ParseException;

// Might need to change name to CentralIngredientListManager to match structure.

/**
 * Manager class for handling methods related to the CentralFoodList.
 * This includes creating new ingredients and adding them to the central list.
 *
 * @author Dennis Moe
 */
public class CentralFoodListManager {

  private final CentralFoodList centralFoodList;

  /**
   * Constructor to initialize the manager with a CentralFoodList instance.
   *
   * @param centralFoodList the instance of CentralFoodList to manage.
   */
  public CentralFoodListManager(CentralFoodList centralFoodList) {
    this.centralFoodList = centralFoodList;
  }

  /**
   * Creates a new ingredient and adds it to the CentralFoodList.
   *
   * @param itemName          Name of the food item/ingredient.
   * @param itemType          Type of the ingredient (e.g., Ground Meat).
   * @param itemWeight        Weight of the ingredient.
   * @param itemMeasuringUnit Measuring unit (e.g., grams, liters).
   * @param expirationDate    Expiration date in string format (dd-MM-yyyy).
   * @param costPerItem       Cost per item in NOK.
   * 
   * @return String informing user if operation was successful or not.
   * @throws ParseException if the expiration date format is invalid.
   */
  public String createAndAddIngredient(String itemName, String itemType, int itemWeight,
      String itemMeasuringUnit, String expirationDate,
      int costPerItem) throws ParseException {

    Ingredient existingIngredient = centralFoodList.getIngredientByName(itemName);

    // Checks if the Ingredient already exists and returns string
    if (existingIngredient != null) {
      return "'" + itemName + "' is already registered.";
    }
    // Create the new ingredient
    Ingredient newIngredient = new Ingredient(itemName, itemType,
        itemWeight, itemMeasuringUnit,
        expirationDate, costPerItem);

    // Add the ingredient to the CentralFoodList
    centralFoodList.addIngredient(newIngredient);

    return "'" + itemName + "' has been added to the registry.";
  }

  /**
   * Removes an Ingredient from the central food list.
   *
   * @param itemName name of Ingredient to remove.
   * @return String explaining the user that the operation was successful or not.
   */
  public String removeExistingIngredient(String itemName) {
    Ingredient existingIngredient = centralFoodList.getIngredientByName(itemName);

    // If the ingredient does not exist, return early
    if (existingIngredient == null) {
      return "There is no ingredient with that name.";
    }

    // Otherwise, remove the ingredient and return success message
    centralFoodList.removeIngredient(existingIngredient);
    return "'" + itemName + "' removed from registry.";
  }

  /**
   * Retrieves an ingredient from the CentralFoodList by its name.
   *
   * @param itemName The name of the ingredient to retrieve.
   * @return The Ingredient object if found, or null if not found.
   */
  public Ingredient getIngredientByName(String itemName) {
    return centralFoodList.getIngredientByName(itemName);
  }
}
