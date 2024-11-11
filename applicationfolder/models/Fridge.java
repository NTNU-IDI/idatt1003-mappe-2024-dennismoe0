package applicationfolder.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a fridge that stores ingredients and tracks their
 * quantities. Each ingredient is stored with its name (String) as the key and
 * an
 * Ingredient object as the value, allowing for flexible management of
 * quantities,
 * units, and other details.
 *
 * @author Dennis Moe
 */
public class Fridge {

  /**
   * A HashMap to store the ingredients currently in the fridge and their
   * respective details. The key is the item name (String), and the value is an
   * Ingredient object representing that ingredient with quantity, unit, and other
   * properties.
   */
  private final Map<String, Map<Date, Ingredient>> fridgeContents = new HashMap<>();

  /**
   * Combines/adds the value of a "new" ingredient to the original ingredient.
   * If you buy more of the same ingredient, this method will combine them.
   *
   * @param newIngredient the ingredient to add or combine
   */
  public void addToOrCombineWithIngredient(Ingredient ingredient) {
    String ingredientName = ingredient.getItemName();
    Ingredient existingIngredient = fridgeContents.get(ingredientName);
    if (existingIngredient != null) {
      existingIngredient.combineQuantity(existingIngredient.getQuantity() + ingredient.getQuantity());
    } else {
      fridgeContents.put(ingredientName, ingredient);
    }
  }

  /**
   * Deducts a specified amount of an ingredient from the fridge, handling unit
   * conversion via UnitConverter.
   *
   * @param ingredientName the name of the ingredient
   * @param amount         the amount to deduct
   * @param measuringUnit  the unit of the amount to deduct
   * @return boolean indicating if the deduction was successful (true if enough
   *         quantity was available)
   */
  public boolean updateIngredientQuantity(String ingredientName,
      double amount, String measuringUnit) {
    Ingredient ingredient = fridgeContents.get(ingredientName);
    if (ingredient != null) {
      return ingredient.deductQuantity(amount, measuringUnit);
    }
    return false; // Ingredient not found in fridge
  }

  /**
   * Retrieves the quantity of an ingredient currently in the fridge.
   *
   * @param ingredientName the name of the ingredient
   * @return the current quantity if the ingredient exists, or 0.0 if not found
   */
  public double getIngredientQuantity(String ingredientName) {
    Ingredient ingredient = fridgeContents.get(ingredientName);
    return ingredient != null ? ingredient.getQuantity() : 0.0;
  }

  /**
   * Retrieves a string representation of the fridge contents for easy viewing.
   *
   * @return a string showing all ingredients and their quantities in the fridge
   */
  @Override
  public String toString() {
    StringBuilder contents = new StringBuilder("Fridge Contents:\n");
    for (Map.Entry<String, Ingredient> entry : fridgeContents.entrySet()) {
      contents.append(entry.getValue().toString()).append("\n");
    }
    return contents.toString();
  }
}
