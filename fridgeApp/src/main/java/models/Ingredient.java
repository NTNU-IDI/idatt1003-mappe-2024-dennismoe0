package models;

/**
 * Represents an ingredient with its details such as name, category, base
 * weight, measuring unit, and cost.
 *
 */
public class Ingredient {
  private final String ingredientName;
  private final String ingredientCategory;
  private final double ingredientBaseWeight;
  private final String ingredientMeasuringUnit;
  private final double ingredientCost; 

  /**
   * Constructs a new Ingredient.
   *
   * @param ingredientName          the name of the ingredient
   * @param ingredientCategory      the category of the ingredient
   * @param ingredientBaseWeight    the base weight of the ingredient
   * @param ingredientMeasuringUnit the measuring unit of the ingredient. "g",
   *                                "kg" etc. or specific units like "pieces" for
   *                                eggs etc.
   * @param ingredientCost          the cost of the ingredient
   */
  public Ingredient(String ingredientName, String ingredientCategory, double ingredientBaseWeight,
      String ingredientMeasuringUnit, double ingredientCost) {
    this.ingredientName = ingredientName;
    this.ingredientCategory = ingredientCategory;
    this.ingredientBaseWeight = ingredientBaseWeight;
    this.ingredientMeasuringUnit = ingredientMeasuringUnit;
    this.ingredientCost = ingredientCost;
  }

  

  public String getIngredientName() {
    return ingredientName;
  }

  public String getIngredientCategory() {
    return ingredientCategory;
  }

  public double getIngredientBaseWeight() {
    return ingredientBaseWeight;
  }

  public String getIngredientMeasuringUnit() {
    return ingredientMeasuringUnit;
  }

  public double getIngredientCost() {
    return ingredientCost;
  }

}