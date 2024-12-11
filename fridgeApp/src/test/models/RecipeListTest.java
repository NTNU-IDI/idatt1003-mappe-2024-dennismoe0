package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RecipeListTest {

    private RecipeList recipeList;
    private Recipe pancakeRecipe;

    @BeforeEach
    void setUp() {
        recipeList = new RecipeList();
        pancakeRecipe = new Recipe("Pancakes", "Fluffy breakfast pancakes",
                "Mix ingredients and fry on a pan.", "Breakfast");
        recipeList.addRecipe(pancakeRecipe);
    }

    @Test
    void addRecipe() {
        Map<String, Recipe> recipes = recipeList.getAllRecipes();
        assertEquals(1, recipes.size(), "Recipe list should contain one recipe.");
        assertTrue(recipes.containsKey("pancakes"), "Recipe list should contain the recipe 'Pancakes'.");
        assertEquals(pancakeRecipe, recipes.get("pancakes"), "The recipe should match the added recipe.");
    }

    @Test
    void removeRecipe() {
        recipeList.removeRecipe("Pancakes");
        Map<String, Recipe> recipes = recipeList.getAllRecipes();
        assertTrue(recipes.isEmpty(), "Recipe list should be empty after removing the recipe.");
    }

    @Test
    void getAllRecipes() {
        Recipe waffleRecipe = new Recipe("Waffles", "Crispy waffles",
                "Mix ingredients and cook in a waffle iron.", "Breakfast");
        recipeList.addRecipe(waffleRecipe);

        Map<String, Recipe> recipes = recipeList.getAllRecipes();
        assertEquals(2, recipes.size(), "Recipe list should contain two recipes.");
        assertTrue(recipes.containsKey("pancakes"), "Recipe list should contain 'Pancakes'.");
        assertTrue(recipes.containsKey("waffles"), "Recipe list should contain 'Waffles'.");
    }
}
