package applicationfolder.client;

import applicationfolder.models.CentralFoodList;
import applicationfolder.models.Fridge;
import applicationfolder.models.Ingredient;
import applicationfolder.services.CentralFoodListManager;
import java.text.ParseException;

public class TestClient {
  public static void main(String[] args) {
    try {
      // Initialize CentralFoodList and Fridge
      CentralFoodList centralFoodList = new CentralFoodList();
      Fridge fridge = new Fridge();
      CentralFoodListManager manager = new CentralFoodListManager(centralFoodList);

      // Create 3 ingredients
      manager.createAndAddIngredient("Apple", "Fruit", 200, "g", "15-11-2024", 10);
      manager.createAndAddIngredient("Banana", "Fruit", 120, "g", "10-11-2024", 5);
      manager.createAndAddIngredient("Orange", "Fruit", 150, "g", "12-11-2024", 8);

      // Retrieve and display the ingredients from the central food list
      System.out.println("Ingredients in Central Food List:");
      System.out.println(manager.getIngredientByName("Apple"));
      System.out.println(manager.getIngredientByName("Banana"));
      System.out.println(manager.getIngredientByName("Orange"));

      // Add two ingredients to the fridge
      fridge.addIngredientToFridge(manager.getIngredientByName("Apple"), 3); // 3 Apples
      fridge.addIngredientToFridge(manager.getIngredientByName("Banana"), 5); // 5 Bananas

      // Display fridge contents
      System.out.println("\nFridge Contents:");
      fridge.getFridgeContents()
          .forEach((ingredient, amount) -> System.out.println(ingredient.getItemName() + ": " + amount + " units"));

      // Check ingredient amounts
      System.out.println("\nCheck specific ingredient amounts:");
      System.out
          .println("Amount of Apple in fridge: " + fridge.getIngredientAmount(manager.getIngredientByName("Apple")));
      System.out
          .println("Amount of Banana in fridge: " + fridge.getIngredientAmount(manager.getIngredientByName("Banana")));
      System.out.println("Amount of Orange in fridge (not added): "
          + fridge.getIngredientAmount(manager.getIngredientByName("Orange")));

    } catch (ParseException e) {
      System.out.println("Error creating ingredients: " + e.getMessage());
    }
  }
}
