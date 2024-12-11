package client;

import java.util.Scanner;
import models.FoodList;

/**
 * Menu for managing the food list and displaying different information.
 *
 * @author Dennis Moe
 */
public class FoodListMenu {

  private final FoodList foodList;
  private final Scanner scanner;

  /**
   * Constructs a new FoodListMenu.
   *
   * @param foodList the food list to manage
   * @param scanner  the Scanner instance for user input
   */
  public FoodListMenu(FoodList foodList, Scanner scanner) {
    this.foodList = foodList;
    this.scanner = scanner;
  }

  /**
   * Displays the food list menu.
   */
  public void display() {
    while (true) {
      System.out.println("""
          \nFood List Menu:
          Handles the list of all registered ingredients.
          You can treat this as your store/previously bought items.""");
      System.out.println("1. Show all ingredients.");
      System.out.println("2. Create a new ingredient.");
      System.out.println("3. Remove an ingredient.");
      System.out.println("4. Go back to main menu.");

      int choice = scanner.nextInt();
      scanner.nextLine();

      switch (choice) {
        case 1 -> {
          System.out.println("Generating a list of all ingredients...");

          foodList.printFoodList();
        }
        case 2 -> {
          System.out.println("Enter ingredient name: ");
          String name = scanner.nextLine();

          System.out.println("Enter category: ");
          String category = scanner.nextLine();

          System.out.println("Enter base weight/volume (i.e. 400): ");
          double baseWeight = scanner.nextDouble();
          scanner.nextLine();

          System.out.println("Enter unit ('g', 'mL', 'L' etc.): ");
          String unit = scanner.nextLine();

          System.out.println("Enter cost in NOK (i.e. '79'): ");
          double cost = scanner.nextDouble();
          scanner.nextLine();

          String result = foodList.createAndAddIngredient(name, category, baseWeight, unit, cost);
          System.out.println(result);
        }
        case 3 -> {
          System.out.println("Generating a list of all ingredients...");

          foodList.printFoodList();

          System.out.print("Enter ingredient name to remove: ");
          String name = scanner.nextLine();

          String result = foodList.removeIngredient(name);
          System.out.println(result);
        }
        case 4 -> {
          return;
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }
}