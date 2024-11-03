package applicationfolder.services;

import applicationfolder.models.Fridge;
import applicationfolder.models.Ingredient;
import java.util.HashMap;

public class FridgeManager {
  
  private Fridge fridge;

  public FridgeManager(Fridge fridge) {
    this.fridge = fridge;
  }

  public void addIngredientToFridge(Ingredient ingredient, int amount) {
    // Checks if the ingredient already has an amount in the fridge.
    // getOrDefault gets amount, or 0 if nothing is found.
    int currentAmount = fridge.getFridgeContents().getOrDefault(ingredient, 0);

    // add current amount if there is any + newly added amount.
    fridge.getFridgeContents().put(ingredient, currentAmount + amount);
  }

  public HashMap<Ingredient, Integer> getFridgeContent() {
    return fridge.getFridgeContents();
  }

  public int findValueOfFridge() {
    int totalValue = 0;

    // Go through each ingredient in the fridge.
    // keySet provides all keys in a HashMap, in this case Ingredients.
    for (Ingredient ingredient : fridge.getFridgeContents().keySet()) {
      // Get amount from the fridge. (Finds the value of the paired key)
      int amount = fridge.getFridgeContents().get(ingredient);

      // Gets price of that instance of the Ingredient object, which is ingredient.
      int pricePerItem = ingredient.getCostPerItem();

      // Adds the value, amount times price per item, to the total value
      totalValue += amount * pricePerItem;
    }

    return totalValue;
  }

  // Need to add method to check for all expired items (using todays date etc), make a new hashmap from that and then
  // make basically the same findValueOfFridge function for that hashmap as well.
}