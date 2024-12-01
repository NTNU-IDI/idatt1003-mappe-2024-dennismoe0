package client;

import java.util.Scanner;
import services.FridgeManager;

/**
 * Menu for managing the fridge.
 *
 * @author Dennis Moe
 */
public class FridgeMenu {

  private final FridgeManager fridgeManager;
  private final Scanner scanner;

  /**
   * Constructor for FridgeMenu.
   *
   * @param fridgeManager the FridgeManager instance
   * @param scanner       the Scanner instance for user input
   */
  public FridgeMenu(FridgeManager fridgeManager, Scanner scanner) {
    this.fridgeManager = fridgeManager;
    this.scanner = scanner;
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
      switch (choice) {
        case 1 -> {
          System.out.print("Enter ingredient name: ");
          String name = scanner.next();
          System.out.print("Enter expiration date (yyyymmdd): ");
          long expirationDate = scanner.nextLong();
          String result = fridgeManager.addToFridge(name, expirationDate);
          System.out.println(result);
        }
        case 2 -> {
          System.out.println("A list of all items in the fridge:");
          fridgeManager.printAllFridgeItemsSorted();
          System.out.print("\nEnter the ID of the item you want to remove: ");
          int id = scanner.nextInt();
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

      switch (choice) {
        case 1 -> {
          System.out.print("Enter the ID of the item you want to change: ");
          int id = scanner.nextInt();
          System.out.print("Enter the new quantity: ");
          double quantity = scanner.nextDouble();
          String result = fridgeManager.setFridgeItemQuantityById(id, quantity);
          System.out.println(result);
        }
        case 2 -> {
          System.out.print("Enter the ID of the item you want to add to: ");
          int id = scanner.nextInt();
          System.out.print("Enter the amount to add: ");
          double amount = scanner.nextDouble();
          String result = fridgeManager.updateFridgeItemQuantityById(id, amount);
          System.out.println(result);
        }
        case 3 -> {
          System.out.print("Enter the ID of the item you want to subtract from: ");
          int id = scanner.nextInt();
          System.out.print("Enter the amount to subtract: ");
          double amount = scanner.nextDouble();
          String result = fridgeManager.updateFridgeItemQuantityById(id, -amount);
          System.out.println(result);
        }
        case 4 -> {
          System.out.print("Enter the ID of the item you want to remove: ");
          int id = scanner.nextInt();
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