package models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class FoodListTest {

    private FoodList foodList;
    private Ingredient ingredient1;
    private Ingredient ingredient2;

    @BeforeEach
    void setUp() {
        foodList = new FoodList();
        ingredient1 = new Ingredient("Milk", "Dairy", 1.0, "Liter", 2.5);
        ingredient2 = new Ingredient("Eggs", "Protein", 12, "Pieces", 3.0);
    }

    @Test
    void addIngredient() {
        foodList.addIngredient(ingredient1);
        assertEquals(1, foodList.getFoodList().size(), "Food list should contain one ingredient.");
        assertTrue(foodList.getFoodList().containsKey("Milk"), "Food list should contain Milk.");
    }

    @Test
    void createAndAddIngredient() {
        String result = foodList.createAndAddIngredient("Cheese", "Dairy", 0.5, "Kg", 5.0);
        assertEquals("Ingredient 'Cheese' added successfully.", result, "Ingredient should be added successfully.");
        assertTrue(foodList.getFoodList().containsKey("Cheese"), "Food list should contain Cheese.");

        String duplicateResult = foodList.createAndAddIngredient("Cheese", "Dairy", 0.5, "Kg", 5.0);
        assertEquals("Error: Ingredient with the name 'Cheese' already exists in the food list.", duplicateResult, "Duplicate ingredient should not be added.");
    }

    @Test
    void removeIngredient() {
        foodList.addIngredient(ingredient1);
        String result = foodList.removeIngredient("Milk");
        assertEquals("Ingredient 'Milk' removed successfully.", result, "Ingredient should be removed successfully.");
        assertFalse(foodList.getFoodList().containsKey("Milk"), "Food list should no longer contain Milk.");

        String notFoundResult = foodList.removeIngredient("Bread");
        assertEquals("Error: Ingredient 'Bread' does not exist in the food list.", notFoundResult, "Non-existent ingredient should not be removed.");
    }

    @Test
    void getIngredientFromFoodList() {
        foodList.addIngredient(ingredient1);
        Ingredient retrieved = foodList.getIngredientFromFoodList("Milk");
        assertEquals(ingredient1, retrieved, "Should retrieve the correct ingredient.");

        Ingredient notFound = foodList.getIngredientFromFoodList("Bread");
        assertNull(notFound, "Should return null for a non-existent ingredient.");
    }

    @Test
    void getFoodList() {
        foodList.addIngredient(ingredient1);
        foodList.addIngredient(ingredient2);
        HashMap<String, Ingredient> retrievedList = foodList.getFoodList();
        assertEquals(2, retrievedList.size(), "Food list should contain two ingredients.");
        assertTrue(retrievedList.containsKey("Milk"), "Food list should contain Milk.");
        assertTrue(retrievedList.containsKey("Eggs"), "Food list should contain Eggs.");
    }

    @Test
    void printFoodList() {
        foodList.addIngredient(ingredient1);
        foodList.addIngredient(ingredient2);
        // Using a simple assert to verify no exceptions occur during execution
        assertDoesNotThrow(() -> foodList.printFoodList(), "Printing food list should not throw an exception.");
    }

    @Test
    void getIngredientInfo() {
        foodList.addIngredient(ingredient1);
        FridgeItem fridgeItem = new FridgeItem(ingredient1, ingredient1.getIngredientBaseWeight(), 1699999999999L);

        String info = FoodList.getIngredientInfo("Milk", fridgeItem, foodList);
        String expectedInfo = "Ingredient info:\n\r" +
                "Name: Milk.\n" +
                "Category: Dairy.\n" +
                "Weight: 1.0 Liter.\n" +
                "Cost per Ingredient: 2.5 NOK.";

        assertEquals(expectedInfo, info, "Ingredient info should match the expected format.");

        String notFoundInfo = FoodList.getIngredientInfo("Bread", fridgeItem, foodList);
        assertEquals("There is not ingredient with that name.", notFoundInfo, "Should return error message for non-existent ingredient.");
    }
}