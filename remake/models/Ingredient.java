package models;

import remake.utilities.UnitConverter;
import remake.utilities.DateUtility;

public class Ingredient {
  private final String ingredientName;
  private final String ingredientCategory;
  private final double ingredientBaseWeight;
  private final String ingredientMeasuringUnit;
  private double ingredientCurrentWeight; // This will be updated by the client/methods.
  // New version stores expiration date as a long
  private final long ingredientExpirationDate;
  private final double ingredientCost; // Make getter for cost per gram/unit.

  public Ingredient(String ingredientName, String ingredientCategory, double ingredientBaseWeight,
      String ingredientMeasuringUnit, double ingredientCurrentWeight, long ingredientExpirationDate,
      double ingredientCost) {
    this.ingredientName = ingredientName;
    this.ingredientCategory = ingredientCategory;
    this.ingredientBaseWeight = ingredientBaseWeight;
    this.ingredientMeasuringUnit = ingredientMeasuringUnit;
    this.ingredientCurrentWeight = ingredientCurrentWeight;
    this.ingredientExpirationDate = ingredientExpirationDate;
    this.ingredientCost = ingredientCost;
  }
}