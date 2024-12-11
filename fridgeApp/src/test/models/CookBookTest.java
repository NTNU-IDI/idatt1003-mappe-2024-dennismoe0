package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CookBookTest {

    private CookBook cookBook;

    @BeforeEach
    void setUp() {
        cookBook = new CookBook("Desserts", "A collection of dessert recipes", "Dessert");
    }

    @Test
    void addRecipe() {
        Recipe recipe = new Recipe("Chocolate Cake", "A rich chocolate cake", "Bake for 30 minutes", "Dessert");
        boolean result = cookBook.addRecipe(recipe);
        assertTrue(result, "Recipe should be added successfully.");
        assertEquals(1, cookBook.getRecipeCount(), "CookBook should contain 1 recipe.");

        boolean duplicateResult = cookBook.addRecipe(recipe);
        assertFalse(duplicateResult, "Duplicate recipe should not be added.");
    }

    @Test
    void removeRecipe() {
        Recipe recipe = new Recipe("Chocolate Cake", "A rich chocolate cake", "Bake for 30 minutes", "Dessert");
        cookBook.addRecipe(recipe);

        boolean result = cookBook.removeRecipe("Chocolate Cake");
        assertTrue(result, "Recipe should be removed successfully.");
        assertEquals(0, cookBook.getRecipeCount(), "CookBook should contain 0 recipes after removal.");

        boolean invalidResult = cookBook.removeRecipe("Vanilla Cake");
        assertFalse(invalidResult, "Non-existent recipe should not be removed.");
    }

    @Test
    void getRecipeFromCookBook() {
        Recipe recipe = new Recipe("Brownies", "Fudgy brownies", "Bake for 25 minutes", "Dessert");
        cookBook.addRecipe(recipe);

        Recipe retrievedRecipe = cookBook.getRecipeFromCookBook("Brownies");
        assertNotNull(retrievedRecipe, "Recipe should be retrieved successfully.");
        assertEquals("Brownies", retrievedRecipe.getRecipeName(), "Retrieved recipe name should match.");
    }

    @Test
    void containsRecipe() {
        Recipe recipe = new Recipe("Ice Cream", "Vanilla ice cream", "Freeze for 4 hours", "Dessert");
        cookBook.addRecipe(recipe);

        boolean result = cookBook.containsRecipe("Ice Cream");
        assertTrue(result, "CookBook should contain the recipe.");

        boolean invalidResult = cookBook.containsRecipe("Sorbet");
        assertFalse(invalidResult, "CookBook should not contain a non-existent recipe.");
    }

    @Test
    void getRecipesInCookBook() {
        Recipe recipe1 = new Recipe("Cupcakes", "Vanilla cupcakes", "Bake for 20 minutes", "Dessert");
        Recipe recipe2 = new Recipe("Cookies", "Chocolate chip cookies", "Bake for 15 minutes", "Dessert");

        cookBook.addRecipe(recipe1);
        cookBook.addRecipe(recipe2);

        Map<String, Recipe> recipes = cookBook.getRecipesInCookBook();
        assertEquals(2, recipes.size(), "CookBook should contain 2 recipes.");
        assertTrue(recipes.containsKey("Cupcakes"), "CookBook should contain 'Cupcakes'.");
        assertTrue(recipes.containsKey("Cookies"), "CookBook should contain 'Cookies'.");
    }

    @Test
    void getRecipeCount() {
        assertEquals(0, cookBook.getRecipeCount(), "CookBook should initially contain 0 recipes.");

        Recipe recipe = new Recipe("Muffins", "Blueberry muffins", "Bake for 20 minutes", "Dessert");
        cookBook.addRecipe(recipe);
        assertEquals(1, cookBook.getRecipeCount(), "CookBook should contain 1 recipe after adding.");
    }
}