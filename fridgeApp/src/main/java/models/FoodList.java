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
   * Creates and adds an Ingredient to the FoodList.
   *
   * @param ingredientName          the name of the Ingredient
   * @param ingredientCategory      the category of the Ingredient
   * @param ingredientBaseWeight    the base weight of the Ingredient
   * @param ingredientMeasuringUnit the measuring unit of the Ingredient
   * @param ingredientCost          the cost of the Ingredient
   * @return a message indicating the result of the operation
   */
  public String createAndAddIngredient(String ingredientName, String ingredientCategory,
      double ingredientBaseWeight, String ingredientMeasuringUnit, double ingredientCost) {

    if (ingredientName == null || ingredientName.trim().isEmpty()) {
      return "Error: Ingredient name cannot be empty or null.";
    }

    if (foodList.containsKey(ingredientName)) {
      return "Error: Ingredient with the name '" + ingredientName
          + "' already exists in the food list.";
    }

    if (ingredientCategory == null || ingredientCategory.trim().isEmpty()) {
      return "Error: Ingredient category cannot be empty or null.";
    }

    if (ingredientBaseWeight <= 0) {
      return "Error: Base weight must be greater than 0.";
    }

    if (ingredientMeasuringUnit == null || ingredientMeasuringUnit.trim().isEmpty()) {
      return "Error: Measuring unit cannot be empty or null.";
    }

    if (ingredientCost < 0) {
      return "Error: Cost cannot be negative.";
    }

    Ingredient ingredient = new Ingredient(ingredientName, ingredientCategory, ingredientBaseWeight,
        ingredientMeasuringUnit, ingredientCost);
    foodList.put(ingredientName, ingredient);
    return "Ingredient '" + ingredientName + "' added successfully.";
  }

  /**
   * Removes an Ingredient from the FoodList.
   *
   * @param ingredientName the name of the Ingredient to remove
   * @return a message indicating the result of the operation
   */
  public String removeIngredient(String ingredientName) {
    if (ingredientName == null || ingredientName.trim().isEmpty()) {
      return "Error: Ingredient name cannot be null or empty.";
    }

    if (!foodList.containsKey(ingredientName)) {
      return "Error: Ingredient '" + ingredientName + "' does not exist in the food list.";
    }

    foodList.remove(ingredientName);
    return "Ingredient '" + ingredientName + "' removed successfully.";
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
   * Prints the FoodList to the console.
   * With the name, weight, measuring unit and cost of each Ingredient.
   */
  public void printFoodList() {
    System.out.println("Food list:");
    for (String ingredientName : foodList.keySet()) {
      Ingredient ingredient = foodList.get(ingredientName);
      if (ingredient != null) {
        System.out.println(
            ingredientName
                + ": "
                + ingredient.getIngredientBaseWeight() + " "
                + ingredient.getIngredientMeasuringUnit() + " - "
                + ingredient.getIngredientCost() + " NOK.");
      }
    }
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

    String ingredientInfo = "Ingredient info:\n\r"
              + "Name: " + ingredient.getIngredientName() + ".\n"
              + "Category: " + ingredient.getIngredientCategory()
              + ".\n"
              + "Weight: " + ingredient.getIngredientBaseWeight()
              + " "
              + ingredient.getIngredientMeasuringUnit()
              + ".\n"
              + "Cost per Ingredient: " + ingredient.getIngredientCost()
              + " NOK.";

    return ingredientInfo;
  }
}
