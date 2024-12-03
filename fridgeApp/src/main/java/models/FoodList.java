package models;

import java.util.HashMap;

/**
 * This class represents a registry of all Ingredient, but not the instances in
 * the Fridge.
 * This is more similar to a representation of the Ingredients information when
 * you buy it
 * at the store.
 * Also has assorted methods for adding, removing and getting info from the
 * ingredients.
 *
 * @author Dennis Moe
 */
public class FoodList {

  private final HashMap<String, Ingredient> foodList;

  /**
   * Constructs a new FoodList.
   */
  public FoodList() {
    foodList = new HashMap<>();
  }

  /**
   * Adds an Ingredient to the FoodList.
   *
   * @param ingredient the Ingredient to add
   */
  public void addIngredient(Ingredient ingredient) {
    foodList.put(ingredient.getIngredientName(), ingredient);
  }

  /**
   * Removes an Ingredient from the FoodList.
   *
   * @param ingredient the Ingredient to remove
   */
  public void removeIngredient(Ingredient ingredient) {
    foodList.remove(ingredient.getIngredientName());
  }

  /**
   * Gets the Ingredient from the FoodList.
   *
   * @param ingredientName the name of the Ingredient to get
   * @return the Ingredient
   */
  public Ingredient getIngredientFromFoodList(String ingredientName) {
    return foodList.get(ingredientName);
  }

  /**
   * Gets the FoodList.
   *
   * @return the FoodList
   */
  public HashMap<String, Ingredient> getFoodList() {
    return foodList;
  }

  /**
   * Prints the FoodList.
   *
   * @return a String representation of the FoodList
   */
  public String printFoodList() {
    StringBuilder foodListString = new StringBuilder();
    foodListString.append("Food list:\n\r");
    for (String ingredientName : foodList.keySet()) {
      foodListString.append(ingredientName).append("\n");
    }
    return foodListString.toString();
  }

  /**
   * Gets the information of an Ingredient from the FoodList.
   *
   * @param ingredientName the name of the Ingredient to get information for
   * @param fridgeItem     the FridgeItem containing the Ingredient
   * @param foodList       the FoodList containing the Ingredient
   * @return a String containing the Ingredient information
   */
  public static String getIngredientInfo(String ingredientName,
      FridgeItem fridgeItem, FoodList foodList) {
    Ingredient ingredient = foodList.getIngredientFromFoodList(ingredientName);
    if (ingredient == null) {
      return "There is not ingredient with that name.";
    }

    StringBuilder ingredientInfo = new StringBuilder();
    ingredientInfo.append("Ingredient info:\n\r");
    ingredientInfo.append("Name: ").append(ingredient.getIngredientName()).append(".\n");
    ingredientInfo.append("Category: ").append(ingredient.getIngredientCategory()).append(".\n");
    ingredientInfo.append("Weight: ").append(ingredient.getIngredientBaseWeight()).append(" ")
        .append(ingredient.getIngredientMeasuringUnit()).append(".\n");
    ingredientInfo.append("Cost per Ingredient: ").append(ingredient.getIngredientCost())
        .append(" NOK.");

    return ingredientInfo.toString();
  }
}
