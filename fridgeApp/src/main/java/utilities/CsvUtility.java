package utilities;

import java.io.*;
import java.util.*;
import models.*;

/**
 * Utility class for reading and writing CSV files.
 * These files will be used to store and retrieve data that
 * the user inputs, changes or deletes.
 * Will be used in FridgeManager, FoodList, RecipeManager and CookBookManager.
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
        writer.write(String.join(",", row));
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
      while ((line = reader.readLine()) != null) {
        data.add(line.split(",")); // Split by comma
      }
    } catch (IOException e) {
      System.err.println("Error reading CSV file: " + e.getMessage());
    }
    return data;
  }

  // Helper methods for Ingredients, FoodList, RecipeManager and CookBookManager.

  // Ingredients / FoodList

  /**
   * Writes a list of ingredients to a CSV file.
   *
   * @param filePath the path of the CSV file to write to
   * @param foodList the hashmap of ingredients to write
   */
  public static void writeIngredientsToCsv(String filePath, HashMap<String, Ingredient> foodList) {
    try (PrintWriter writer = new PrintWriter(new File(filePath))) {
      for (Ingredient ingredient : foodList.values()) { // Loop through all ingredients
        writer.printf("%s,%s,%.2f,%s,%.2f%n", // Format for each row
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
   * @return A list of Ingredient objects.
   */
  public static Map<String, Ingredient> readIngredientsFromCsv(String filePath) {
    Map<String, Ingredient> foodList = new HashMap<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
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
      for (FridgeItem item : fridgeItems) {
        writer.printf("%s,%.2f,%d%n",
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
   * @param filePath path of the CSV file to read from.
   * @param foodList the FoodList object to get ingredients from.
   * @return A list of FridgeItem objects.
   */
  public static List<FridgeItem> readFridgeItemsFromCsv(String filePath, FoodList foodList) {
    List<FridgeItem> fridgeItems = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length == 3) {
          Ingredient ingredient = foodList.getIngredientFromFoodList(parts[0]);
          if (ingredient != null) {
            fridgeItems.add(new FridgeItem(
                ingredient, Double.parseDouble(parts[1]), Long.parseLong(parts[2])));
          }
        }
      }
    } catch (IOException e) {
      System.err.println("Error reading fridge items from CSV: " + e.getMessage());
    }
    return fridgeItems;
  }

  // Recipes / RecipeManager

  /**
   * Writes a list of recipes to a CSV file.
   *
   * @param filePath the path of the CSV file to write to
   * @param recipes  the list of recipes to write
   */
  public static void writeRecipesToCsv(String filePath, List<Recipe> recipes) {
    try (PrintWriter writer = new PrintWriter(new File(filePath))) {
      for (Recipe recipe : recipes) {
        writer.printf("%s,%s,%s,%s,%s%n",
            recipe.getRecipeName(),
            recipe.getRecipeDescription(),
            recipe.getInstructions(),
            recipe.getRecipeType(),
            recipe.getIngredients().toString());
      }
    } catch (IOException e) {
      System.err.println("Error writing recipes to CSV: " + e.getMessage());
    }
  }

  /**
   * Reads recipes data from a CSV file.
   *
   * @param filePath path of the CSV file to read from.
   * @param foodList the FoodList object to get ingredients from.
   * @return A list of Recipe objects.
   */
  public static List<Recipe> readRecipesFromCsv(String filePath, FoodList foodList) {
    List<Recipe> recipes = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 5) {
          Recipe recipe = new Recipe(parts[0], parts[1], parts[2], parts[3]);
          String ingredientsData = parts[4].replaceAll("[\\{\\}]", "");
          String[] ingredientsArray = ingredientsData.split(", ");
          for (String ingredientPair : ingredientsArray) {
            String[] ingredientParts = ingredientPair.split("=");
            if (ingredientParts.length == 2) {
              Ingredient ingredient = foodList.getIngredientFromFoodList(ingredientParts[0]);
              if (ingredient != null) {
                recipe.addIngredient(ingredientParts[0], Double.parseDouble(ingredientParts[1]));
              }
            }
          }
          recipes.add(recipe);
        }
      }
    } catch (IOException e) {
      System.err.println("Error reading recipes from CSV: " + e.getMessage());
    }
    return recipes;
  }

  // CookBooks / CookBookManager

  /**
   * Writes a map of cookbooks to a CSV file.
   *
   * @param filePath  the path of the CSV file to write to
   * @param cookBooks the map of cookbooks to write
   */
  public static void writeCookBooksToCsv(String filePath, Map<String, CookBook> cookBooks) {
    try (PrintWriter writer = new PrintWriter(new File(filePath))) {
      for (Map.Entry<String, CookBook> entry : cookBooks.entrySet()) {

        String cookBookName = entry.getKey();
        CookBook cookBook = entry.getValue();

        List<Recipe> recipes = new ArrayList<>(cookBook.getRecipesInCookBook().values());

        String recipeNames = recipes.stream()
            .map(Recipe::getRecipeName)
            .reduce((a, b) -> a + "," + b)
            .orElse("");

        writer.printf("%s,%s%n",
            cookBookName,
            recipeNames);
      }
    } catch (IOException e) {
      System.err.println("Error writing cookbooks to CSV: " + e.getMessage());
    }
  }

  /**
   * Reads cookbooks data from a CSV file.
   *
   * @param filePath the path of the CSV file to read from.
   * @param recipes  the list of recipes to match with cookbook entries.
   * @return A map of cookbook names to lists of Recipe objects.
   */
  public static Map<String, List<Recipe>> readCookBooksFromCsv(String filePath,
      List<Recipe> recipes) {
    Map<String, List<Recipe>> cookBooks = new HashMap<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length == 2) {
          String bookName = parts[0];
          String[] recipeNames = parts[1].replaceAll("[\\[\\]]", "").split(", ");
          List<Recipe> bookRecipes = new ArrayList<>();
          for (String recipeName : recipeNames) {
            for (Recipe recipe : recipes) {
              if (recipe.getRecipeName().equals(recipeName.trim())) {
                bookRecipes.add(recipe);
                break;
              }
            }
          }
          cookBooks.put(bookName, bookRecipes);
        }
      }
    } catch (IOException e) {
      System.err.println("Error reading cookbooks from CSV: " + e.getMessage());
    }
    return cookBooks;
  }
}
