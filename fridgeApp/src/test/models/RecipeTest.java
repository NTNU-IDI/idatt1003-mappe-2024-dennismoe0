package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    private Recipe recipe;

    @BeforeEach
    void setUp() {
        recipe = new Recipe("Pasta", "A delicious pasta recipe", "Boil pasta and mix with sauce.", "Dinner");
    }

    @Test
    void addIngredient() {
        recipe.addIngredient("Tomato Sauce", 1.5);
        recipe.addIngredient("Pasta", 200.0);

        Map<String, Double> ingredients = recipe.getIngredients();

        assertEquals(2, ingredients.size(), "There should be 2 ingredients in the recipe.");
        assertEquals(1.5, ingredients.get("Tomato Sauce"), "Quantity of Tomato Sauce should be 1.5.");
        assertEquals(200.0, ingredients.get("Pasta"), "Quantity of Pasta should be 200.0.");
    }

    @Test
    void removeIngredient() {
        recipe.addIngredient("Cheese", 50.0);
        recipe.addIngredient("Basil", 5.0);

        recipe.removeIngredient("Cheese");

        Map<String, Double> ingredients = recipe.getIngredients();

        assertEquals(1, ingredients.size(), "There should be 1 ingredient left in the recipe after removal.");
        assertFalse(ingredients.containsKey("Cheese"), "Cheese should be removed.");
        assertTrue(ingredients.containsKey("Basil"), "Basil should still be in the recipe.");
    }
}
