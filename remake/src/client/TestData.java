package client;

import models.*;
import services.*;
import utilities.DateValidation;
import java.util.HashMap;

public class TestData {

  public static void main(String[] args) {
    // Setup FoodList with 30 ingredients
    FoodList foodList = new FoodList();
    foodList.addIngredient(new Ingredient("Oats", "Grains", 500, "g", 30));
    foodList.addIngredient(new Ingredient("Yoghurt", "Dairy", 150, "g", 20));
    foodList.addIngredient(new Ingredient("Ground Beef", "Meat", 400, "g", 50));
    foodList.addIngredient(new Ingredient("Tomato Sauce", "Sauce", 250, "g", 10));
    foodList.addIngredient(new Ingredient("Onion", "Vegetable", 150, "g", 5));
    foodList.addIngredient(new Ingredient("Cheese", "Dairy", 200, "g", 25));
    foodList.addIngredient(new Ingredient("Egg", "Dairy", 60, "g", 3));
    foodList.addIngredient(new Ingredient("Flour", "Grains", 500, "g", 10));
    foodList.addIngredient(new Ingredient("Sugar", "Sweetener", 300, "g", 12));
    foodList.addIngredient(new Ingredient("Carrot", "Vegetable", 100, "g", 8));
    foodList.addIngredient(new Ingredient("Butter", "Dairy", 250, "g", 15));
    foodList.addIngredient(new Ingredient("Milk", "Dairy", 500, "ml", 20));
    foodList.addIngredient(new Ingredient("Vanilla Extract", "Spices", 30, "ml", 5));
    foodList.addIngredient(new Ingredient("Cinnamon", "Spices", 20, "g", 6));
    foodList.addIngredient(new Ingredient("Salt", "Seasoning", 50, "g", 2));
    foodList.addIngredient(new Ingredient("Baking Powder", "Baking", 50, "g", 4));
    foodList.addIngredient(new Ingredient("Chocolate Chips", "Dessert", 200, "g", 40));
    foodList.addIngredient(new Ingredient("Carrot Juice", "Beverage", 250, "ml", 8));
    foodList.addIngredient(new Ingredient("Garlic", "Vegetable", 50, "g", 3));
    foodList.addIngredient(new Ingredient("Cabbage", "Vegetable", 200, "g", 6));
    foodList.addIngredient(new Ingredient("Lemon", "Fruit", 100, "g", 5));
    foodList.addIngredient(new Ingredient("Cucumber", "Vegetable", 100, "g", 4));
    foodList.addIngredient(new Ingredient("Tomato", "Fruit", 200, "g", 4));
    foodList.addIngredient(new Ingredient("Mushroom", "Vegetable", 200, "g", 7));
    foodList.addIngredient(new Ingredient("Parmesan", "Dairy", 100, "g", 15));
    foodList.addIngredient(new Ingredient("Basil", "Herb", 20, "g", 3));
    foodList.addIngredient(new Ingredient("Olive Oil", "Oils", 100, "ml", 10));

    // Setup Fridge with 10 items
    Fridge fridge = new Fridge();
    FridgeManager fridgeManager = new FridgeManager(fridge, foodList);

    // Adding ingredients to fridge (quantity based on item weight)
    fridgeManager.addToFridge("Oats", DateValidation.getTodayAsLong() + (7 * 24 * 60 * 60 * 1000));
    fridgeManager.addToFridge("Yoghurt", DateValidation.getTodayAsLong() + (7 * 24 * 60 * 60 * 1000));
    fridgeManager.addToFridge("Ground Beef", DateValidation.getTodayAsLong() + (14 * 24 * 60 * 60 * 1000));
    fridgeManager.addToFridge("Tomato Sauce", DateValidation.getTodayAsLong() + (30 * 24 * 60 * 60 * 1000));
    fridgeManager.addToFridge("Onion", DateValidation.getTodayAsLong() + (7 * 24 * 60 * 60 * 1000));
    fridgeManager.addToFridge("Cheese", DateValidation.getTodayAsLong() + (30 * 24 * 60 * 60 * 1000));
    fridgeManager.addToFridge("Egg", DateValidation.getTodayAsLong() + (7 * 24 * 60 * 60 * 1000));
    fridgeManager.addToFridge("Flour", DateValidation.getTodayAsLong() + (60 * 24 * 60 * 60 * 1000));
    fridgeManager.addToFridge("Butter", DateValidation.getTodayAsLong() + (60 * 24 * 60 * 60 * 1000));
    fridgeManager.addToFridge("Milk", DateValidation.getTodayAsLong() + (7 * 24 * 60 * 60));

    // Setup RecipeList with 3 recipes
    RecipeList recipeList = new RecipeList();
    RecipeManager recipeManager = new RecipeManager(recipeList, fridge);

    // Create recipes
    Recipe oatmealRecipe = new Recipe("Oatmeal with Yoghurt", "Breakfast", "A simple breakfast with oats and yoghurt.",
        "Cook oats and mix with yoghurt.", new HashMap<String, Double>() {
          {
            put("Oats", 200.0); // 200g oats
            put("Yoghurt", 150.0); // 150g yoghurt
          }
        });
    recipeManager.addRecipe(oatmealRecipe);

    Recipe lasagnaRecipe = new Recipe("Lasagna with Ground Beef", "Dinner",
        "Classic lasagna made with ground beef and cheese.",
        "Layer ground beef, tomato sauce, pasta, and cheese to make lasagna.", new HashMap<String, Double>() {
          {
            put("Ground Beef", 400.0); // 400g ground beef
            put("Tomato Sauce", 250.0); // 250g tomato sauce
            put("Onion", 100.0); // 100g onion
            put("Cheese", 200.0); // 200g cheese
          }
        });
    recipeManager.addRecipe(lasagnaRecipe);

    Recipe carrotCakeRecipe = new Recipe("Carrot Cake", "Dessert", "Delicious carrot cake for dessert.",
        "Mix grated carrots with flour and spices, then bake.", new HashMap<String, Double>() {
          {
            put("Carrot", 200.0); // 200g carrots
            put("Flour", 250.0); // 250g flour
            put("Sugar", 150.0); // 150g sugar
            put("Egg", 100.0); // 100g egg
            put("Butter", 100.0); // 100g butter
          }
        });
    recipeManager.addRecipe(carrotCakeRecipe);

    // Test the client functionality
    System.out.println("Test Ingredients Availability for Lasagna with Ground Beef:");
    System.out.println(recipeManager.checkIngredientsAvailability(lasagnaRecipe));

    System.out.println("\nTest Ingredients Availability for Oatmeal with Yoghurt:");
    System.out.println(recipeManager.checkIngredientsAvailability(oatmealRecipe));

    System.out.println("\nTest Ingredients Availability for Carrot Cake:");
    System.out.println(recipeManager.checkIngredientsAvailability(carrotCakeRecipe));

    System.out.println("\nTotal Value of Recipes:");
    System.out.println("Oatmeal with Yoghurt: " + recipeManager.calculateRecipeCost(oatmealRecipe));
    System.out.println("Lasagna with Ground Beef: " + recipeManager.calculateRecipeCost(lasagnaRecipe));
    System.out.println("Carrot Cake: " + recipeManager.calculateRecipeCost(carrotCakeRecipe));
  }
}
