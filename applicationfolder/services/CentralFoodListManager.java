package applicationfolder.services;

import applicationfolder.models.CentralFoodList;
import applicationfolder.models.Ingredient;
import java.text.ParseException;

/**
 * Manager class for handling methods related to the CentralFoodList.
 * This includes creating new ingredients and adding them to the central list.
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
   * @param itemName           Name of the food item/ingredient.
   * @param itemType           Type of the ingredient (e.g., Ground Meat).
   * @param itemWeight         Weight of the ingredient.
   * @param itemMeasuringUnit  Measuring unit (e.g., grams, liters).
   * @param expirationDate     Expiration date in string format (dd-MM-yyyy).
   * @param costPerItem        Cost per item in NOK.
   * @throws ParseException    if the expiration date format is invalid.
   */
  public void createAndAddIngredient(String itemName, String itemType, int itemWeight,
                                     String itemMeasuringUnit, String expirationDate, 
                                     int costPerItem) throws ParseException {
    // Create the new ingredient
    Ingredient newIngredient = new Ingredient(itemName, itemType,
                                              itemWeight, itemMeasuringUnit,
                                              expirationDate, costPerItem);

    // Add the ingredient to the CentralFoodList
    centralFoodList.addIngredient(newIngredient);
  }

  /**
   * Retrieves an ingredient from the CentralFoodList by its name.
   *
   * @param itemName The name of the ingredient to retrieve.
   * @return The Ingredient object if found, or null if not found.
   */
  public Ingredient getIngredientByName(String itemName) {
    return centralFoodList.getIngredient(itemName);
  }
}
