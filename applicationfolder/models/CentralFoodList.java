package applicationfolder.models;

import java.util.HashMap;

/**
 * Class representing a central list of all available ingredients.
 * This list serves as a reference for all possible ingredients in the system.
 *
 * @author Dennis Moe
 */
public class CentralFoodList {

  /**
   * A HashMap to store ingredients,
   * where the key is the ingredient name and the value is the Ingredient object.
   */
  private HashMap<String, Ingredient> foodList;

  /**
   * Constructor to initialize an empty central food list.
   */
  public CentralFoodList() {
    this.foodList = new HashMap<>();
  }

  /**
   * Adds an ingredient to the central food list.
   *
   * @param ingredient the Ingredient object to be added
   */
  public void addIngredient(Ingredient ingredient) {
    foodList.put(ingredient.getItemName(), ingredient);
  }

  /**
   * Removes an ingredient from the list.
   *
   * @param ingredient Ingredient to be removed.
   */
  public void removeIngredient(Ingredient ingredient) {
    foodList.remove(ingredient.getItemName());
  }

  /**
   * Gets the name of an Ingredient object in string form.
   *
   * @param itemName the string name of an Ingredient.
   * @return the Ingredient object form the foodList HashMap.
   */
  public Ingredient getIngredientByName(String itemName) {
    return foodList.get(itemName);
  }

  /**
   * Retrieves all information about an ingredient from the CentralFoodList.
   * This method is static, so it requires a CentralFoodList instance to function
   * properly.
   *
   * @param itemName        The name of the ingredient to search for.
   * @param centralFoodList An instance of the CentralFoodList to retrieve the
   *                        ingredient from.
   * @return A formatted string containing all the details about the ingredient,
   *         or a message if the ingredient is not found.
   */
  public static String getIngredientInfo(String itemName, CentralFoodList centralFoodList) {
    Ingredient ingredient = centralFoodList.getIngredientByName(itemName);
    if (ingredient == null) {
      return "There is not ingredient with that name.";
    }

    StringBuilder ingredientInfo = new StringBuilder();
    ingredientInfo.append("Ingredient info:\n\r");
    ingredientInfo.append("Name: ").append(ingredient.getItemName()).append(".\n");
    ingredientInfo.append("Type: ").append(ingredient.getItemType()).append(".\n");
    ingredientInfo.append("Weight: ").append(ingredient.getItemWeight()).append(" ")
        .append(ingredient.getItemMeasuringUnit()).append(".\n");
    ingredientInfo.append("Expiration Date: ")
        .append(ingredient.getFormattedExpirationDate()).append(".\n");
    ingredientInfo.append("Cost per Item: ").append(ingredient.getCostPerItem()).append(" NOK.");

    return ingredientInfo.toString();
  }
}
