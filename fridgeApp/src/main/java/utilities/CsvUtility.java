package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import models.CookBook;
import models.FridgeItem;
import models.Ingredient;
import models.Recipe;
import services.CookBookManager;
import services.RecipeManager;
import services.FridgeManager;

/**
 * Utility class for reading and writing CSV files.
 * These files will be used to store and retrieve data that
 * the user inputs, changes, or deletes.
 * Will be used in FridgeManager, FoodList, RecipeManager, and CookBookManager.
 *
 * @author Dennis Moe
 */
public class CsvUtility {

  /**
   * Writes data to a CSV file.
   *
   * @param filePath path of the CSV file to write to.
   * @param data     The data to write, String[] represents a row.
   */
  public static void writeToCsv(String filePath, List<String[]> data) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      for (String[] row : data) {
        StringBuilder sb = new StringBuilder();
        for (String field : row) {
          if (field.contains(",") || field.contains("\"")) {
            sb.append("\"").append(field.replace("\"", "\"\"")).append("\"");
          } else {
            sb.append(field);
          }
          sb.append(",");
        }
        writer.write(sb.substring(0, sb.length() - 1)); // Remove last comma
        writer.newLine();
      }
    } catch (IOException e) {
      System.err.println("Error writing to CSV file: " + e.getMessage());
    }
  }

  /**
   * Reads data from a CSV file.
   *
   * @param filePath path of the CSV file to read from.
   * @return A list of String[] where each array represents a row.
   */
  public static List<String[]> readFromCsv(String filePath) {
    List<String[]> data = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      boolean isFirstLine = true; // Flag to skip the header
      while ((line = reader.readLine()) != null) {
        if (isFirstLine) {
          isFirstLine = false;
          continue; // Skip the header
        }

        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
          if (c == '\"') {
            inQuotes = !inQuotes; // Toggle inQuotes
          } else if (c == ',' && !inQuotes) {
            fields.add(currentField.toString().trim());
            currentField.setLength(0);
          } else {
            currentField.append(c);
          }
        }
        fields.add(currentField.toString().trim());
        data.add(fields.toArray(new String[0]));
      }
    } catch (IOException e) {
      System.err.println("Error reading CSV file: " + e.getMessage());
    }
    return data;
  }

  // Helper methods for Ingredients, FoodList, RecipeManager, and CookBookManager.

  // Ingredients / FoodList

  /**
   * Writes a list of ingredients to a CSV file.
   *
   * @param filePath the path of the CSV file to write to
   * @param foodList the hashmap of ingredients to write
   */
  public static void writeIngredientsToCsv(String filePath, HashMap<String, Ingredient> foodList) {
    try (PrintWriter writer = new PrintWriter(new File(filePath))) {
      // Write header
      writer.println("IngredientName,Category,BaseWeight,MeasuringUnit,Cost");
      for (Ingredient ingredient : foodList.values()) {
        writer.printf(Locale.US, "%s,%s,%.2f,%s,%.2f%n",
            ingredient.getIngredientName(),
            ingredient.getIngredientCategory(),
            ingredient.getIngredientBaseWeight(),
            ingredient.getIngredientMeasuringUnit(),
            ingredient.getIngredientCost());
      }
    } catch (IOException e) {
      System.err.println("Error writing ingredients to CSV file: " + e.getMessage());
    }
  }

  /**
   * Reads ingredients data from a CSV file.
   *
   * @param filePath path of the CSV file to read from.
   * @return A map of Ingredient objects.
   */
  public static Map<String, Ingredient> readIngredientsFromCsv(String filePath) {
    Map<String, Ingredient> foodList = new HashMap<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      boolean isFirstLine = true; // Skip header
      while ((line = reader.readLine()) != null) {
        if (isFirstLine) {
          isFirstLine = false;
          continue;
        }
        String[] parts = line.split(",");
        if (parts.length == 5) {
          Ingredient ingredient = new Ingredient(
              parts[0], parts[1], Double.parseDouble(parts[2]),
              parts[3], Double.parseDouble(parts[4]));
          foodList.put(ingredient.getIngredientName(), ingredient);
        }
      }
    } catch (IOException e) {
      System.err.println("Error reading FoodList from CSV: " + e.getMessage());
    }
    return foodList;
  }

  // FridgeItems / FridgeManager

  /**
   * Writes a list of fridge items to a CSV file.
   *
   * @param filePath    the path of the CSV file to write to
   * @param fridgeItems the list of fridge items to write
   */
  public static void writeFridgeItemsToCsv(String filePath, List<FridgeItem> fridgeItems) {
    try (PrintWriter writer = new PrintWriter(new File(filePath))) {
      // Write header
      writer.println("IngredientName,Quantity,ExpirationDate");
      for (FridgeItem item : fridgeItems) {
        writer.printf(Locale.US, "%s,%.2f,%d%n",
            item.getIngredient().getIngredientName(),
            item.getQuantity(),
            item.getExpirationDate());
      }
    } catch (IOException e) {
      System.err.println("Error writing fridge items to CSV: " + e.getMessage());
    }
  }

  /**
   * Reads fridge items data from a CSV file.
   *
   * @param filePath      path of the CSV file to read from.
   * @param fridgeManager uses fridgeManager to add ingredients from foodlsit.
   */
  public static int[] readFridgeItemsFromCsv(String filePath,
      FridgeManager fridgeManager) {

    int itemsAdded = 0;
    int itemsFailed = 0;

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      boolean isFirstLine = true; // Skip header
      while ((line = reader.readLine()) != null) {
        if (isFirstLine) {
          isFirstLine = false;
          continue;
        }

        String[] parts = line.split(",");

        if (parts.length == 3) {
          String ingredientName = parts[0].trim();
          long expirationDate;
          try {
            expirationDate = Long.parseLong(parts[2].trim());
          } catch (NumberFormatException e) {
            System.err.println("Invalid expiration date in line: " + e.getMessage());
            itemsFailed++;
            continue;
          }

          String result = fridgeManager.addToFridge(ingredientName, expirationDate);
          if (result.trim().equalsIgnoreCase("Ingredient added to fridge successfully.")) {
            itemsAdded++;
          } else {
            System.err.println("Error adding ingredient to fridge: " + result);
            itemsFailed++;
          }
        } else {
          System.err.println("Invalid line in CSV file: " + line);
          itemsFailed++;
        }
      }
    } catch (IOException e) {
      System.err.println("Error reading fridge items from CSV: " + e.getMessage());
    }
    return new int[] { itemsAdded, itemsFailed };
  }

  // Recipes / RecipeManager

  /**
   * Writes a list of recipes to a CSV file.
   *
   * @param filePath the path of the CSV file to write to
   * @param recipes  the list of recipes to write
   */
  /**
   * Writes a list of recipes to a CSV file.
   *
   * @param filePath the path of the CSV file to write to
   * @param recipes  the list of recipes to write
   */
  public static void writeRecipesToCsv(String filePath, List<Recipe> recipes) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      // Write header
      writer.write("RecipeName,Description,Instructions,Type,Ingredients");
      writer.newLine();

      for (Recipe recipe : recipes) {
        // Format ingredients map into a single string with brackets
        String ingredients = recipe.getIngredients().entrySet().stream()
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .reduce((a, b) -> a + ", " + b)
            .map(result -> "{" + result + "}") // Enclose in brackets
            .orElse("{}"); // Handle empty ingredients map

        writer.write(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
            recipe.getRecipeName().replace("\"", "\"\""),
            recipe.getRecipeDescription().replace("\"", "\"\""),
            recipe.getInstructions().replace("\"", "\"\""),
            recipe.getRecipeType().replace("\"", "\"\""),
            ingredients));
      }
    } catch (IOException e) {
      System.err.println("Error writing recipes to CSV: " + e.getMessage());
    }
  }

  /**
   * Reads recipes data from a CSV file and adds them using the RecipeManager.
   *
   * @param filePath      path of the CSV file to read from.
   * @param recipeManager the RecipeManager to manage the recipes.
   * @return an array where the first element is the number of items added, and
   *         the second is the number of items failed.
   */
  public static int[] readRecipesFromCsv(String filePath, RecipeManager recipeManager) {
    int itemsAdded = 0;
    int itemsFailed = 0;

    try {
      List<String[]> rows = readFromCsv(filePath); // Header already skipped
      for (String[] row : rows) {
        if (row.length >= 5) { // Ensure valid data
          String recipeName = row[0];
          String recipeDescription = row[1];
          String instructions = row[2];
          String recipeType = row[3];

          // Remove enclosing brackets from the ingredients string
          String ingredientsData = row[4].replaceAll("[\\{\\}]", "");
          String[] ingredientsArray = ingredientsData.split(", ");

          Map<String, Double> ingredients = new HashMap<>();
          for (String ingredientPair : ingredientsArray) {
            String[] ingredientParts = ingredientPair.split("=");
            if (ingredientParts.length == 2) {
              String ingredientName = ingredientParts[0].trim();
              double ingredientAmount = Double.parseDouble(ingredientParts[1].trim());
              ingredients.put(ingredientName, ingredientAmount);
            }
          }

          // Use the RecipeManager to create and add the recipe
          String result = recipeManager.createNewRecipeWithIngredients(
              recipeName, recipeDescription, instructions, recipeType, ingredients);

          if (result.equals("Succesfully created the recipe.")) {
            itemsAdded++;
          } else {
            System.err.println("Error adding recipe '" + recipeName + "': " + result);
            itemsFailed++;
          }
        } else {
          System.err.println("Invalid line in CSV file: " + String.join(",", row));
          itemsFailed++;
        }
      }
    } catch (Exception e) {
      System.err.println("Error reading recipes from CSV: " + e.getMessage());
    }

    return new int[] { itemsAdded, itemsFailed };
  }

  // CookBooks / CookBookManager

  /**
   * Writes a map of cookbooks to a CSV file.
   *
   * @param filePath  the path of the CSV file to write to
   * @param cookBooks the map of cookbooks to write
   */
  public static void writeCookBooksToCsv(String filePath, Map<String, CookBook> cookBooks) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      // Write header
      writer.write("CookBookName,Recipes");
      writer.newLine();

      for (Map.Entry<String, CookBook> entry : cookBooks.entrySet()) {
        String cookBookName = entry.getKey();
        CookBook cookBook = entry.getValue();

        // Format recipes inside brackets, correctly listing recipe names
        String recipes = cookBook.getRecipesInCookBook().keySet().stream()
            .map(recipeName -> recipeName.replace("\"",
                "\"\"")) // Escape double quotes in recipe names
            .reduce((a, b) -> a + ", " + b) // Combine recipe names with commas
            .map(recipeList -> "{" + recipeList + "}") // Enclose recipes in curly braces
            .orElse("{}"); // Handle case where there are no recipes

        writer.write(String.format("\"%s\",\"%s\"%n",
            cookBookName.replace("\"", "\"\""), // Escape double quotes in cookbook name
            recipes));
      }
    } catch (IOException e) {
      System.err.println("Error writing cookbooks to CSV: " + e.getMessage());
    }
  }

  /**
   * Reads cookbook data from a CSV file and updates the CookBookManager.
   *
   * @param filePath        the path of the CSV file to read from
   * @param cookBookManager the CookBookManager instance to manage cookbooks
   *                        cookbooks
   * @return an array of ints where index 0 is the count of successfully added
   *         cookbooks,
   *         index 1 is the count of failed cookbooks,
   *         index 2 is the count of successfully added recipes,
   *         and index 3 is the count of failed recipes
   */
  public static int[] readCookBooksFromCsv(String filePath, CookBookManager cookBookManager) {
    int cookBooksAdded = 0;
    int cookBooksFailed = 0;
    int recipesAdded = 0;
    int recipesFailed = 0;

    try {
      List<String[]> rows = readFromCsv(filePath); // Reads data from CSV
      for (String[] row : rows) {
        if (row.length == 2) { // Ensure valid data format (cookbook name, recipes)
          String cookBookName = row[0];
          String recipeData = row[1];

          // Remove enclosing brackets and split recipes
          recipeData = recipeData.replaceAll("[\\{\\}]", "");
          String[] recipeNames = recipeData.split(",\\s*");

          // Create the cookbook
          String creationResult = cookBookManager.createCookBook(cookBookName, "Description", "Type");
          if (creationResult.contains("CookBook created successfully!")) {
            cookBooksAdded++;
            for (String recipeName : recipeNames) {
              String result = cookBookManager.addRecipeToCookBook(cookBookName, recipeName);
              if (result.contains("Recipe added to CookBook!")) {
                recipesAdded++;
              } else {
                System.out.println("Failed to add recipe to cookbook: " + recipeName + " -> " + result);
                recipesFailed++;
              }
            }
          } else {
            System.out.println("Failed to create cookbook: " + cookBookName + " -> " + creationResult);
            cookBooksFailed++;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return new int[] { cookBooksAdded, cookBooksFailed, recipesAdded, recipesFailed };
  }

}