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

    // Validate ingredient name
    if (ingredientName == null || ingredientName.trim().isEmpty()) {
      return "Error: Ingredient name cannot be empty or null.";
    }

    // Check if ingredient already exists
    if (foodList.containsKey(ingredientName)) {
      return "Error: Ingredient with the name '" + ingredientName
          + "' already exists in the food list.";
    }

    // Validate category
    if (ingredientCategory == null || ingredientCategory.trim().isEmpty()) {
      return "Error: Ingredient category cannot be empty or null.";
    }

    // Validate base weight
    if (ingredientBaseWeight <= 0) {
      return "Error: Base weight must be greater than 0.";
    }

    // Validate measuring unit
    if (ingredientMeasuringUnit == null || ingredientMeasuringUnit.trim().isEmpty()) {
      return "Error: Measuring unit cannot be empty or null.";
    }

    // Validate cost
    if (ingredientCost < 0) {
      return "Error: Cost cannot be negative.";
    }

    // Create and add the ingredient
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
    // Validate input
    if (ingredientName == null || ingredientName.trim().isEmpty()) {
      return "Error: Ingredient name cannot be null or empty.";
    }

    // Check if ingredient exists in the food list
    if (!foodList.containsKey(ingredientName)) {
      return "Error: Ingredient '" + ingredientName + "' does not exist in the food list.";
    }

    // Remove ingredient
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
