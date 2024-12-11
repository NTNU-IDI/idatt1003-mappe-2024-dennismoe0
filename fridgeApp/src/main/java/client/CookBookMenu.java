package client;

import java.util.Scanner;
import services.CookBookManager;
import services.RecipeManager;

/**
 * This class represents the menu for managing cookbooks.
 * It provides options to list, create, delete, and manage recipes within
 * cookbooks.
 */
public class CookBookMenu {

  private final CookBookManager cookBookManager;
  private final RecipeManager recipeManager;
  private final Scanner scanner;

  /**
   * Constructs a new CookBookMenu.
   *
   * @param cookBookManager the manager for cookbooks.
   * @param recipeManager   the manager for recipes.
   * @param scanner         the scanner for user input.
   */
  public CookBookMenu(CookBookManager cookBookManager,
      RecipeManager recipeManager, Scanner scanner) {
    this.cookBookManager = cookBookManager;
    this.recipeManager = recipeManager;
    this.scanner = scanner;
  }

  /**
   * Displays the cookbook menu and handles user input.
   */
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
        case 1 -> System.out.println(cookBookManager.listAllCookBooks());
        case 2 -> System.out.println(cookBookManager.listAllCookBooksWithRecipes());
        case 3 -> manageCookBooksMenu();
        case 4 -> {
          return;
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  /**
   * Menu for managing cookbooks.
   * Provides options to create, delete, add, and remove recipes from cookbooks.
   */
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
          System.out.println(cookBookManager.listAllCookBooks());
          System.out.println("Enter the name of the cookbook to delete:");
          String name = scanner.nextLine();
          System.out.println(cookBookManager.deleteCookBook(name));
        }
        case 3 -> {
          System.out.println("Generating a list of all recipes...");

          recipeManager.printAllRecipes();
          System.out.println(cookBookManager.listAllCookBooks());
          System.out.println("Enter the name of the cookbook to add the recipe to:");
          String cookBookName = scanner.nextLine();
          System.out.println("Enter the name of the recipe to add to the cookbook:");
          String recipeName = scanner.nextLine();
          System.out.println(cookBookManager.addRecipeToCookBook(cookBookName, recipeName));
        }
        case 4 -> {

          System.out.println(cookBookManager.listAllCookBooksWithRecipes());

          System.out.println("Enter the name of the cookbook to remove the recipe from:");
          String cookBookName = scanner.nextLine();
          System.out.println(cookBookManager.listAllRecipesInCookBook(cookBookName));

          System.out.println("Enter the name of the recipe to remove from the cookbook:");
          String recipeName = scanner.nextLine();
          System.out.println(cookBookManager.removeRecipeFromCookBook(cookBookName, recipeName));
        }
        case 5 -> {
          return;
        }
        default -> System.out.println("Invalid choice. Please try again.");
      }
    }
  }
}
