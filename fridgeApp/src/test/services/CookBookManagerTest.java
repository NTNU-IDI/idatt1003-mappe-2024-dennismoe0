package services;

import models.CookBook;
import models.Recipe;
import models.RecipeList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CookBookManagerTest {

    private RecipeList recipeList;
    private CookBookManager cookBookManager;

    @BeforeEach
    void setUp() {
        recipeList = new RecipeList();
        cookBookManager = new CookBookManager(recipeList);

        Recipe recipe1 = new Recipe("Pasta", "Delicious pasta recipe", "Boil and mix", "Main Dish");
        Recipe recipe2 = new Recipe("Salad", "Healthy green salad", "Mix ingredients", "Appetizer");

        recipeList.addRecipe(recipe1);
        recipeList.addRecipe(recipe2);
    }

    @Test
    void createCookBook() {
        String result = cookBookManager.createCookBook("Italian Cuisine", "Recipes from Italy", "Cuisine");
        assertEquals("CookBook created successfully!", result, "CookBook should be created successfully.");

        String duplicateResult = cookBookManager.createCookBook("Italian Cuisine", "Another description", "Cuisine");
        assertEquals("CookBook already exists! Delete it or use another name.", duplicateResult, "Duplicate CookBook should not be created.");
    }

    @Test
    void deleteCookBook() {
        cookBookManager.createCookBook("Desserts", "Sweet recipes", "Dessert");
        String result = cookBookManager.deleteCookBook("Desserts");
        assertEquals("CookBook deleted successfully!", result, "CookBook should be deleted successfully.");

        String invalidResult = cookBookManager.deleteCookBook("NonExistent");
        assertEquals("CookBook not found!", invalidResult, "Should handle non-existent CookBook deletion.");
    }

    @Test
    void addRecipeToCookBook() {
        cookBookManager.createCookBook("Healthy Recipes", "Nutritious meals", "Health");

        String result = cookBookManager.addRecipeToCookBook("Healthy Recipes", "Salad");
        assertEquals("Recipe added to CookBook!", result, "Recipe should be added to the CookBook.");

        String duplicateResult = cookBookManager.addRecipeToCookBook("Healthy Recipes", "Salad");
        assertEquals("Recipe already exists in CookBook!", duplicateResult, "Duplicate recipe should not be added.");

        String invalidCookBookResult = cookBookManager.addRecipeToCookBook("NonExistent", "Pasta");
        assertEquals("CookBook not found!", invalidCookBookResult, "Should handle non-existent CookBook.");

        String invalidRecipeResult = cookBookManager.addRecipeToCookBook("Healthy Recipes", "Pizza");
        assertEquals("Recipe not found! Create it first, or check your spelling.", invalidRecipeResult, "Should handle non-existent recipe.");
    }

    @Test
    void removeRecipeFromCookBook() {
        cookBookManager.createCookBook("Quick Meals", "Fast and easy recipes", "Quick");
        cookBookManager.addRecipeToCookBook("Quick Meals", "Pasta");

        String result = cookBookManager.removeRecipeFromCookBook("Quick Meals", "Pasta");
        assertEquals("Recipe removed from CookBook!", result, "Recipe should be removed successfully.");

        String invalidRecipeResult = cookBookManager.removeRecipeFromCookBook("Quick Meals", "NonExistent");
        assertEquals("Recipe not found in CookBook!", invalidRecipeResult, "Should handle non-existent recipe removal.");

        String invalidCookBookResult = cookBookManager.removeRecipeFromCookBook("NonExistent", "Pasta");
        assertEquals("CookBook not found!", invalidCookBookResult, "Should handle non-existent CookBook.");
    }

    @Test
    void listAllCookBooks() {
        cookBookManager.createCookBook("Mexican Cuisine", "Recipes from Mexico", "Cuisine");
        cookBookManager.createCookBook("Italian Cuisine", "Recipes from Italy", "Cuisine");

        StringBuilder result = cookBookManager.listAllCookBooks();
        String expected = "-----------\nMexican Cuisine\n-----------\nItalian Cuisine\n";
        assertEquals(expected.trim(), result.toString().trim(), "Should list all CookBooks correctly.");
    }

    @Test
    void listAllRecipesInCookBook() {
        cookBookManager.createCookBook("Dinner Recipes", "Delicious dinner ideas", "Dinner");
        cookBookManager.addRecipeToCookBook("Dinner Recipes", "Pasta");
        cookBookManager.addRecipeToCookBook("Dinner Recipes", "Salad");

        StringBuilder result = cookBookManager.listAllRecipesInCookBook("Dinner Recipes");
        String expected = "--------------\nPasta\n--------------\nSalad\n";
        assertEquals(expected.trim(), result.toString().trim(), "Should list all recipes in the CookBook.");

        String invalidCookBookResult = cookBookManager.listAllRecipesInCookBook("NonExistent").toString();
        assertEquals("CookBook not found!", invalidCookBookResult, "Should handle non-existent CookBook.");
    }

    @Test
    void listAllCookBooksWithRecipes() {
        cookBookManager.createCookBook("Brunch Recipes", "Brunch ideas", "Brunch");
        cookBookManager.addRecipeToCookBook("Brunch Recipes", "Salad");

        StringBuilder result = cookBookManager.listAllCookBooksWithRecipes();
        String expected = "-------------\nCookBook: Brunch Recipes\nRecipes:\n  - Salad\n";
        assertEquals(expected.trim(), result.toString().trim(), "Should list all CookBooks with their recipes.");

        cookBookManager.createCookBook("Empty CookBook", "No recipes yet", "Misc");
        result = cookBookManager.listAllCookBooksWithRecipes();
        String additionalExpected = "-------------\nCookBook: Empty CookBook\nRecipes:\n  No recipes in this CookBook.";
        assertTrue(result.toString().contains(additionalExpected.trim()), "Should include empty CookBooks in the list.");
    }

    @Test
    void getAllCookBooks() {
        cookBookManager.createCookBook("Breakfast Ideas", "Morning meals", "Breakfast");
        cookBookManager.createCookBook("Vegan Recipes", "Plant-based meals", "Vegan");

        assertEquals(2, cookBookManager.getAllCookBooks().size(), "CookBookManager should contain two CookBooks.");
        assertTrue(cookBookManager.getAllCookBooks().containsKey("Breakfast Ideas"), "Should contain 'Breakfast Ideas'.");
        assertTrue(cookBookManager.getAllCookBooks().containsKey("Vegan Recipes"), "Should contain 'Vegan Recipes'.");
    }
}
