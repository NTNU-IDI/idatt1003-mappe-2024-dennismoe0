package models;

import utilities.DateValidation;

/**
 * Represents an instance of an Ingredient in the Fridge.
 * The unique Ingredient can share name, but will be its own to seperate the
 * objects clearly.
 */
public class FridgeItem {
  private final Ingredient ingredient;
  private double quantity;
  private final long expirationDate;

  /**
   * Constructs a new FridgeItem.
   *
   * @param ingredient     the ingredient to be added to the Fridge/make a new
   *                       instance.
   * @param quantity       the quantity of the ingredient that will be added to
   *                       the Fridge, same as the base weight of the ingredient.
   * @param expirationDate the expiration date of the ingredient.
   */
  public FridgeItem(Ingredient ingredient, double quantity, long expirationDate) {
    this.ingredient = ingredient;
    this.quantity = ingredient.getIngredientBaseWeight();
    this.expirationDate = expirationDate;
  }

  // Getters

  public Ingredient getIngredient() {
    return ingredient;
  }

  public double getQuantity() {
    return quantity;
  }

  public long getExpirationDate() {
    return expirationDate;
  }

  public String getFormattedExpirationDate() {
    return DateValidation.formatDate(expirationDate);
  }

  // Setters

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }
}
