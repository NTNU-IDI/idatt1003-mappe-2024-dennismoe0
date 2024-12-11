package services;

import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RecipeManagerTest {

    private RecipeManager recipeManager;
    private RecipeList recipeList;
    private Fridge fridge;
    private FoodList foodList;
    private FridgeManager fridgeManager;

    @BeforeEach
    void setUp() {
        recipeList = new RecipeList();
        fridge = new Fridge();
        foodList = new FoodList();
        fridgeManager = new FridgeManager(fridge, foodList);
        recipeManager = new RecipeManager(recipeList, fridgeManager);

        // Add ingredients to FoodList
        foodList.addIngredient(new Ingredient("Flour", "Baking", 1.0, "Kg", 10.0));
        foodList.addIngredient(new Ingredient("Sugar", "Baking", 1.0, "Kg", 8.0));
        foodList.addIngredient(new Ingredient("Milk", "Dairy", 1.0, "Liter", 2.5));
    }

    @Test
    void createNewRecipeWithIngredients() {
        Map<String, Double> ingredients = new HashMap<>();
        ingredients.put("Flour", 0.5);
        ingredients.put("Sugar", 0.2);

        String result = recipeManager.createNewRecipeWithIngredients(
                "Pancakes", "Fluffy pancakes", "Mix and fry.", "Breakfast", ingredients);
        assertEquals("Successfully created the recipe.", result);
        assertNotNull(recipeManager.getRecipeObjectByName("Pancakes"));
    }

    @Test
    void addRecipe() {
        Recipe recipe = new Recipe("Waffles", "Crispy waffles", "Mix and cook.", "Breakfast");
        String result = recipeManager.addRecipe(recipe);
        assertEquals("Recipe successfully created.", result);
        assertNotNull(recipeManager.getRecipeObjectByName("Waffles"));
    }

    @Test
    void removeRecipe() {
        Recipe recipe = new Recipe("Cake", "Chocolate cake", "Bake.", "Dessert");
        recipeList.addRecipe(recipe);
        String result = recipeManager.removeRecipe("Cake");
        assertEquals("Recipe successfully removed.", result);
        assertNull(recipeManager.getRecipeObjectByName("Cake"));
    }

    @Test
    void addIngredientToRecipe() {
        Recipe recipe = new Recipe("Cookies", "Tasty cookies", "Bake.", "Dessert");
        recipeList.addRecipe(recipe);
        String result = recipeManager.addIngredientToRecipe("Cookies", "Flour", 0.5);
        assertEquals("Ingredient Flour added to recipe.", result);
        assertTrue(recipe.getIngredients().containsKey("Flour"));
    }

    @Test
    void removeIngredientFromRecipe() {
        Recipe recipe = new Recipe("Cake", "Chocolate cake", "Bake.", "Dessert");
        recipe.addIngredient("Flour", 0.5);
        recipeList.addRecipe(recipe);
        String result = recipeManager.removeIngredientFromRecipe("Cake", "Flour");
        assertEquals("Flour removed from recipe.", result);
        assertFalse(recipe.getIngredients().containsKey("Flour"));
    }

    @Test
    void getRecipeObjectByName() {
        Recipe recipe = new Recipe("Pie", "Delicious pie", "Bake.", "Dessert");
        recipeList.addRecipe(recipe);
        Recipe foundRecipe = recipeManager.getRecipeObjectByName("Pie");
        assertEquals(recipe, foundRecipe);
    }

    @Test
    void checkIngredientsAvailability() {
        fridgeManager.addToFridge("Flour", 20250101);
        fridgeManager.addToFridge("Sugar", 20250101);

        Recipe recipe = new Recipe("Cookies", "Tasty cookies", "Bake.", "Dessert");
        recipe.addIngredient("Flour", 0.5);
        recipe.addIngredient("Sugar", 0.2);

        String result = recipeManager.checkIngredientsAvailability(recipe);
        assertEquals("All ingredients are available in the required quantities.", result);
    }

    @Test
    void calculateRecipeCost() {
        Recipe recipe = new Recipe("Muffins", "Delicious muffins", "Bake.", "Dessert");
        recipe.addIngredient("Flour", 0.5);
        recipe.addIngredient("Sugar", 0.2);
        double cost = recipeManager.calculateRecipeCost(recipe);
        assertEquals(18.0, cost, 0.01);
    }

    @Test
    void costOfQuantitiesInRecipe() {
        Recipe recipe = new Recipe("Cake", "Chocolate cake", "Bake.", "Dessert");
        recipe.addIngredient("Flour", 0.5);
        recipe.addIngredient("Sugar", 0.2);
        recipeList.addRecipe(recipe);
        double cost = recipeManager.costOfQuantitiesInRecipe("Cake");
        assertEquals(6.6, cost, 0.01);
    }

    @Test
    void updateRecipeIngredient() {
        Recipe recipe = new Recipe("Cookies", "Tasty cookies", "Bake.", "Dessert");
        recipe.addIngredient("Flour", 0.5);
        recipeList.addRecipe(recipe);
        String result = recipeManager.updateRecipeIngredient("Cookies", "Flour", 0.7);
        assertEquals("Ingredient quantity updated successfully.", result);
        assertEquals(0.7, recipe.getIngredients().get("Flour"));
    }

    @Test
    void getRecipe() {
        Recipe recipe = new Recipe("Pasta", "Delicious pasta", "Cook.", "Dinner");
        recipeList.addRecipe(recipe);
        String recipeInfo = recipeManager.getRecipe("Pasta");
        assertTrue(recipeInfo.contains("Pasta"));
        assertTrue(recipeInfo.contains("Delicious pasta"));
    }

    @Test
    void getIngredientsInRecipe() {
        Recipe recipe = new Recipe("Cake", "Chocolate cake", "Bake.", "Dessert");
        recipe.addIngredient("Flour", 0.5);
        recipe.addIngredient("Sugar", 0.2);
        recipeList.addRecipe(recipe);
        String ingredients = recipeManager.getIngredientsInRecipe("Cake");
        assertTrue(ingredients.contains("Flour"));
        assertTrue(ingredients.contains("Sugar"));
    }

    @Test
    void getRecipeObject() {
        Recipe recipe = new Recipe("Pie", "Delicious pie", "Bake.", "Dessert");
        recipeList.addRecipe(recipe);
        Recipe foundRecipe = recipeManager.getRecipeObject("Pie");
        assertEquals(recipe, foundRecipe);
    }

    @Test
    void getAllRecipes() {
        Recipe recipe1 = new Recipe("Pie", "Delicious pie", "Bake.", "Dessert");
        Recipe recipe2 = new Recipe("Soup", "Warm soup", "Cook.", "Dinner");
        recipeList.addRecipe(recipe1);
        recipeList.addRecipe(recipe2);
        String allRecipes = recipeManager.getAllRecipes();
        assertTrue(allRecipes.contains("Pie"));
        assertTrue(allRecipes.contains("Soup"));
    }

    @Test
    void suggestedRecipesBasedOnFridgeContents() {
        fridgeManager.addToFridge("Flour", 20250101);
        fridgeManager.addToFridge("Sugar", 20250101);

        Recipe recipe = new Recipe("Cake", "Chocolate cake", "Bake.", "Dessert");
        recipe.addIngredient("Flour", 0.5);
        recipe.addIngredient("Sugar", 0.2);
        recipe.addIngredient("Milk", 1.0);
        recipeList.addRecipe(recipe);

        String suggestions = recipeManager.suggestedRecipesBasedOnFridgeContents();
        assertTrue(suggestions.contains("Cake"));
        assertTrue(suggestions.contains("Milk"));
    }

    @Test
    void fullyFulfilledRecipes() {
        fridgeManager.addToFridge("Flour", 20250101);
        fridgeManager.addToFridge("Sugar", 20250101);

        Recipe recipe = new Recipe("Cookies", "Tasty cookies", "Bake.", "Dessert");
        recipe.addIngredient("Flour", 0.5);
        recipe.addIngredient("Sugar", 0.2);
        recipeList.addRecipe(recipe);

        String suggestions = recipeManager.fullyFulfilledRecipes();
        assertTrue(suggestions.contains("Cookies"));
    }

    @Test
    void removeMultipleQuantitiesByRecipe() {
        fridgeManager.addToFridge("Flour", 20250101);
        fridgeManager.addToFridge("Sugar", 20250101);

        Recipe recipe = new Recipe("Cake", "Chocolate cake", "Bake.", "Dessert");
        recipe.addIngredient("Flour", 0.5);
        recipe.addIngredient("Sugar", 0.2);
        recipeList.addRecipe(recipe);

        String result = recipeManager.removeMultipleQuantitiesByRecipe("Cake");
        assertEquals("Recipe ingredients removed from fridge, prioritizing items with earliest expiration.", result);
    }

    @Test
    void printAllRecipes() {
        Recipe recipe = new Recipe("Soup", "Warm soup", "Cook.", "Dinner");
        recipeList.addRecipe(recipe);
        recipeManager.printAllRecipes();
    }

    @Test
    void getFormattedRecipeDetails() {
        Recipe recipe = new Recipe("Cake", "Delicious cake", "Bake.", "Dessert");
        recipe.addIngredient("Flour", 0.5);
        recipe.addIngredient("Sugar", 0.2);
        recipeList.addRecipe(recipe);
        String details = recipeManager.getFormattedRecipeDetails("Cake");
        assertTrue(details.contains("Delicious cake"));
        assertTrue(details.contains("Flour"));
    }
}
