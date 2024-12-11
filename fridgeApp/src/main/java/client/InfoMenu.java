package client;

import java.util.Scanner;

/**
 * The InfoMenu class provides a menu interface for displaying information about
 * the application.
 * This is for explaining the purpose and structure of the application.
 *
 * @author Dennis Moe
 */
public class InfoMenu {

  private final Scanner scanner;

  /**
   * Constructs a new InfoMenu.
   *
   * @param scanner the Scanner instance for user input
   */
  public InfoMenu(Scanner scanner) {
    this.scanner = scanner;
  }

  /**
   * Displays the info menu.
   */
  public void display() {
    while (true) {
      System.out.println("""
          \nInfo Menu:
          Handles the information about the application.""");
      System.out.println("1. Show information about the application.");
      System.out.println("2. Go back to main menu.");

      int choice = scanner.nextInt();
      scanner.nextLine();

      switch (choice) {
        case 1 -> {
          System.out.println("""
              \n
              --------------------------------------------------------------------------------------

              This application is a console-based fridge management system.

              It's purpose is to act as a tracker-system for what you have in your fridge
              along with the prices, measuring units, quantity and category of those items.


              The structure of the application is divided into 4 main parts:

              1. Fridge menu: Where you can add, remove and view the items in your fridge.

              2. Food List menu: Where you can add, remove and view the ingredients registered.
                - This is basically a registry of previously bought items/the items in "the store".
                - If you want to add an item not on this list, you need to create it first.
                  - This can be done in both the food list menu and in the fridge menu.


              3. Recipe menu: Where you can add, remove and view recipes.
                - There are several methods to make "your life easier" (debatable).
                - You can get a list of recipes that you can make based on the contents
                  of your fridge. Either fully or partially.
                 - You can also check the price of a recipe.
                   - Both the full cost (regardless of how much you use) and the cost per serving.
                 - You're also able to "use" the recipe,
                   which will remove the ingredients from your fridge.
                   - This is done by removing the amount of each ingredient used in the recipe.
                    -> Uses the items closest to expiration first.

              4. CookBook menu: Where you can add, remove and view cookbooks.
                - A cookbook is a collection of recipes.
                - You can manage cookbooks and add/remove recipes
                  to a cookbook and view the recipes in a cookbook.

              --------------------------------------------------------------------------------------
              """);
        }
        case 2 -> {
          System.out.println("Returning to main menu...");
          return;
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }
}
