package applicationfolder.services;

public class FridgeManager {
  
}

  /**
   * Adds an ingredient to the fridge along with a specified amount.
   * If the ingredient already exists, it updates the amount.
   *
   * @param ingredient the Ingredient object to be added to the fridge.
   * @param amount     the amount of the ingredient to be added.
   */
  public void addIngredientToFridge(Ingredient ingredient, int amount) {
    fridgeContents.put(ingredient, amount);
  }