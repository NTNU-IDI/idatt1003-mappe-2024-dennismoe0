package client;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import models.CookBook;
import services.CookBookManager;
import services.RecipeManager;

public class CookBookMenu {

  private final CookBookManager cookBookManager;
  private final RecipeManager recipeManager;
  private final Scanner scanner;

  public CookBookMenu(CookBookManager cookBookManager, RecipeManager recipeManager, Scanner scanner) {
    this.cookBookManager = cookBookManager;
    this.recipeManager = recipeManager;
    this.scanner = scanner;
  }

  public void display() {
    while (true) {
      System.out.println("Cookbook menu:");
      System.out.println("1. List all cookbooks.");
      System.out.println("2. List all cookbooks and their recipes.");
      System.out.println("3. Manage cookbooks.");
      System.out.println("4. Exit to main menu.");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Clear newline

      switch (choice) {
        case 1 -> cookBookManager.listAllCookBooks();
        case 2 -> cookBookManager.listAllCookBooksWithRecipes();
        case 3 -> manageCookBooksMenu();
        case 4 -> {
          return;
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  private void manageCookBooksMenu() {
    while (true) {
      System.out.println("Manage cookbooks:");
      System.out.println("1. Create a new cookbook.");
      System.out.println("2. Delete a cookbook.");
      System.out.println("3. Add a recipe to a cookbook.");
      System.out.println("4. Remove a recipe from a cookbook.");
      System.out.println("5. Exit to cookbook menu.");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Clear newline

      switch (choice) {
        case 1 -> {
          System.out.println("Enter the name of the new cookbook:");
          String name = scanner.nextLine();
          System.out.println("Enter the description of the new cookbook:");
          String description = scanner.nextLine();
          System.out.println("Enter the type of the new cookbook:");
          String type = scanner.nextLine();
          System.out.println(cookBookManager.createCookBook(name, description, type));
        }
        case 2 -> {
          System.out.println("Enter the name of the cookbook to delete:");
          String name = scanner.nextLine();
          System.out.println(cookBookManager.deleteCookBook(name));
        }
        case 3 -> {
          System.out.println("Generating a list of all recipes...");

          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
          }

          recipeManager.printAllRecipes();

          System.out.println("Enter the name of the cookbook to add the recipe to:");
          String cookBookName = scanner.nextLine();
          System.out.println("Enter the name of the recipe to add to the cookbook:");
          String recipeName = scanner.nextLine();
          System.out.println(cookBookManager.addRecipeToCookBook(cookBookName, recipeName));

          while (addingRecipes) {

          }
        }
        case 4 -> {

        }
        case 5 -> {
          return;
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }
}
