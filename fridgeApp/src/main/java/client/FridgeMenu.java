package client;

import java.util.Scanner;
import models.FoodList;
import services.FridgeManager;

/**
 * Menu for managing the fridge and displaying different information.
 *
 * @author Dennis Moe
 */
public class FridgeMenu {

  private final FridgeManager fridgeManager;
  private final Scanner scanner;
  private final FoodList foodList;

  /**
   * Constructor for FridgeMenu.
   *
   * @param fridgeManager the FridgeManager instance
   * @param scanner       the Scanner instance for user input
   */
  public FridgeMenu(FridgeManager fridgeManager, Scanner scanner, FoodList foodList) {
    this.fridgeManager = fridgeManager;
    this.scanner = scanner;
    this.foodList = foodList;
  }

  /**
   * Displays the fridge menu and handles user interaction.
   */
  public void display() {
    while (true) {
      System.out.println("Fridge Menu:");
      System.out.println("1. See all items in the fridge.");
      System.out.println("2. See all expired items.");
      System.out.println("3. Value menu.");
      System.out.println("4. Manage ingredients in the fridge.");
      System.out.println("5. Back to main menu.");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Clear newline
      switch (choice) {
        case 1 -> {
          System.out.println("All items in the fridge:");
          fridgeManager.printAllFridgeItemsSorted();
        }
        case 2 -> {
          System.out.println("All expired items in the fridge:");
          fridgeManager.printAllExpiredItems();
        }
        case 3 -> valueMenu();
        case 4 -> manageFridgeMenu();
        case 5 -> {
          return;
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  private void valueMenu() {
    while (true) {
      System.out.println("Value menu:");
      System.out.println("1. The total value of the fridge.");
      System.out.println("2. The total value of expired items.");
      System.out.println("3. Exit value menu.");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Clear newline

      switch (choice) {
        case 1 -> {
          System.out.println("Total value of fridge:");
          System.out.print(fridgeManager.getTotalValueOfFridge());
          System.out.print(" NOK.");
        }
        case 2 -> {
          System.out.println("Total value of expired items:");
          System.out.print(fridgeManager.getExpiredItemsValue());
          System.out.print(" NOK.");
        }
        case 3 -> {
          return;
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  private void manageFridgeMenu() {
    while (true) {
      System.out.println("Manage Fridge:");
      System.out.println("1. Add/create an ingredient to the fridge.");
      System.out.println("2. Remove an ingredient from the fridge.");
      System.out.println("3. Change the quantity of an ingredient in the fridge.");
      System.out.println("4. Go back to fridge menu.");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Clear newline
      switch (choice) {
        case 1 -> {
          System.out.println("Choose an action:");
          System.out.println("1. Add a new ingredient to the fridge.");
          System.out.println("2. Create a new ingredient and add to the fridge.");
          System.out.println("3. Go back to manage fridge menu.");

          int createChoice = scanner.nextInt();
          scanner.nextLine(); // Clear newline
          switch (createChoice) {
            case 1 -> {

              System.out.println("Generating a list of all registered ingredients...");
              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
              }
              foodList.printFoodList();

              System.out.print("Enter ingredient name to add to the fridge: ");
              String name = scanner.nextLine();
              System.out.print("Enter expiration date (ddmmyyyy): ");
              long expirationDate = scanner.nextLong();
              System.out.println(fridgeManager.addToFridge(name, expirationDate));
            }
            case 2 -> {
              System.out.println("Enter ingredient name: ");
              String name = scanner.nextLine();

              System.out.println("Enter category: ");
              String category = scanner.nextLine();

              System.out.println("Enter base weight/volume (i.e. 400): ");
              double baseWeight = scanner.nextDouble();
              scanner.nextLine(); // Clear newline

              System.out.println("Enter unit ('g', 'mL', 'L' etc.): ");
              String unit = scanner.nextLine();

              System.out.println("Enter cost in NOK (i.e. '79'): ");
              double cost = scanner.nextDouble();
              scanner.nextLine(); // Clear newline

              System.out.println("Enter expiration date (ddmmyyyy): ");
              long expirationDate = scanner.nextLong();

              String result = fridgeManager.createAndAddToFridge(name, category,
                  baseWeight, unit, cost, expirationDate);
              System.out.println(result);
            }
            case 3 -> {
              return;
            }
            default -> System.out.println("Invalid choice. Please try again.");
          }
        }
        case 2 -> {
          System.out.println("A list of all items in the fridge:");
          fridgeManager.printAllFridgeItemsSorted();
          System.out.print("\nEnter the ID of the item you want to remove: ");
          int id = scanner.nextInt();
          scanner.nextLine(); // Clear newline
          String result = fridgeManager.removeFromFridgeById(id);
          System.out.println(result);
        }
        case 3 -> quantityMenu();
        case 4 -> {
          return;
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  private void quantityMenu() {
    while (true) {
      System.out.println("Quantity Menu:");
      System.out.println("1. Set quantity of an ingredient in the fridge.");
      System.out.println("2. Add to the quantity of an ingredient in the fridge.");
      System.out.println("3. Subtract from the quantity of an ingredient in the fridge.");
      System.out.println("4. Delete/remove an ingredient from the fridge.");
      System.out.println("5. Go back to fridge menu.");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Clear newline

      switch (choice) {
        case 1 -> {
          System.out.print("Enter the ID of the item you want to change: ");
          int id = scanner.nextInt();
          scanner.nextLine(); // Clear newline
          System.out.print("Enter the new quantity: ");
          double quantity = scanner.nextDouble();
          scanner.nextLine(); // Clear newline
          String result = fridgeManager.setFridgeItemQuantityById(id, quantity);
          System.out.println(result);
        }
        case 2 -> {
          System.out.print("Enter the ID of the item you want to add to: ");
          int id = scanner.nextInt();
          scanner.nextLine(); // Clear newline
          System.out.print("Enter the amount to add: ");
          double amount = scanner.nextDouble();
          scanner.nextLine(); // Clear newline
          String result = fridgeManager.updateFridgeItemQuantityById(id, amount);
          System.out.println(result);
        }
        case 3 -> {
          System.out.print("Enter the ID of the item you want to subtract from: ");
          int id = scanner.nextInt();
          scanner.nextLine(); // Clear newline
          System.out.print("Enter the amount to subtract: ");
          double amount = scanner.nextDouble();
          scanner.nextLine(); // Clear newline
          String result = fridgeManager.updateFridgeItemQuantityById(id, -amount);
          System.out.println(result);
        }
        case 4 -> {
          System.out.print("Enter the ID of the item you want to remove: ");
          int id = scanner.nextInt();
          scanner.nextLine(); // Clear newline
          String result = fridgeManager.removeFromFridgeById(id);
          System.out.println(result);
        }
        case 5 -> {
          return;
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }
}
