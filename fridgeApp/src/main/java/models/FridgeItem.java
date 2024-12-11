package models;

import utilities.DateValidation;

/**
 * Represents an instance of an Ingredient in the Fridge.
 * The unique Ingredient can share a name, but each instance has its own
 * properties, such as expiration date and ID.
 *
 * @author Dennis Moe
 */
public class FridgeItem {
  private static int idCounter = 1; 
  private final int id; 
  private final Ingredient ingredient;
  private double quantity;
  private final long expirationDate;

  /**
   * Constructs a new FridgeItem with a unique ID.
   *
   * @param ingredient     the ingredient to be added to the Fridge/make a new
   *                       instance.
   * @param quantity       the quantity of the ingredient that will be added to
   *                       the Fridge, same as the base weight of the ingredient.
   * @param expirationDate the expiration date of the ingredient.
   */
  public FridgeItem(Ingredient ingredient, double quantity, long expirationDate) {
    this.id = idCounter++;
    this.ingredient = ingredient;
    this.quantity = ingredient.getIngredientBaseWeight();
    this.expirationDate = expirationDate;
  }

  
  public int getId() {
    return id;
  }

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

  
  /**
   * Sets the quantity of the ingredient and prints a logging statement.
   *
   * @param quantity the new quantity of the ingredient.
   */
  public void setQuantity(double quantity) {
    System.out
        .println("Updating quantity for " + ingredient.getIngredientName()
            + ": " + this.quantity + " -> " + quantity);
    this.quantity = quantity;
  }

  /**
   * Adds a specified amount to the current quantity.
   *
   * @param amount the amount to add to the current quantity.
   */
  public void addQuantity(double amount) {
    this.quantity += amount;
  }

  /**
   * Deducts a specified amount from the current quantity.
   * Ensures that the quantity does not go below zero.
   *
   * @param amount the amount to deduct from the current quantity.
   */
  public void deductQuantity(double amount) {
    this.quantity = Math.max(0, this.quantity - amount);
  }

  @Override
  public String toString() {
    return "ID: " + id + ", Ingredient: " + ingredient.getIngredientName() + ", Quantity: "
        + quantity + " " + ingredient.getIngredientMeasuringUnit() + ", Expiration Date: "
        + getFormattedExpirationDate();
  }
}
