package services;

import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FridgeManagerTest {

    private Fridge fridge;
    private FridgeManager fridgeManager;

    @BeforeEach
    void setUp() {
        fridge = new Fridge();
        FoodList foodList = new FoodList();
        fridgeManager = new FridgeManager(fridge, foodList);

        Ingredient milk = new Ingredient("Milk", "Dairy", 1.0, "Liter", 2.5);
        Ingredient eggs = new Ingredient("Eggs", "Protein", 12, "Pieces", 3.0);

        foodList.addIngredient(milk);
        foodList.addIngredient(eggs);
    }

    @Test
    void addToFridge() {
        // Removed leading zero and 'L' suffix: 1012024 represents 01/01/2024
        String result = fridgeManager.addToFridge("Milk", 1012024);
        assertEquals("Ingredient added to fridge successfully.", result, "Milk should be added to the fridge.");

        String invalidIngredientResult = fridgeManager.addToFridge("Bread", 1012024);
        assertEquals("Ingredient not found in FoodList.", invalidIngredientResult, "Should handle non-existent ingredients.");

        // Invalid date format (too short)
        String invalidDateResult = fridgeManager.addToFridge("Milk", 12345);
        assertEquals("Invalid expiration date, the date is too short.", invalidDateResult, "Should handle invalid expiration dates.");
    }

    @Test
    void getFoodList() {
        assertNotNull(fridgeManager.getFoodList(), "FoodList should not be null.");
        assertEquals(2, fridgeManager.getFoodList().getFoodList().size(), "FoodList should contain two ingredients.");
    }

    @Test
    void getIngredientCost() {
        assertEquals(2.5, fridgeManager.getIngredientCost("Milk"), "Cost of Milk should be 2.5.");
        assertEquals(-1, fridgeManager.getIngredientCost("Bread"), "Should return -1 for non-existent ingredients.");
    }

    @Test
    void createAndAddToFridge() {
        // Removed leading zero and 'L' suffix: 5062024 represents 05/06/2024
        String result = fridgeManager.createAndAddToFridge("Cheese", "Dairy", 0.5, "Kg", 5.0, 5062024);
        assertEquals("Ingredient created and added to fridge successfully.", result, "Should create and add Cheese to the fridge.");

        assertTrue(fridgeManager.getFoodList().getFoodList().containsKey("Cheese"), "FoodList should contain Cheese.");
    }

    @Test
    void removeFromFridgeById() {
        // Removed leading zero and 'L' suffix: 1012024 represents 01/01/2024
        fridgeManager.addToFridge("Milk", 1012024);
        List<FridgeItem> items = fridge.getAllFridgeItems();
        assertFalse(items.isEmpty(), "Fridge should contain at least one item.");
        FridgeItem milkItem = items.get(0); // Replaced getFirst() with get(0)

        String result = fridgeManager.removeFromFridgeById(milkItem.getId());
        assertEquals("Fridge item removed successfully.", result, "Should remove Milk from the fridge.");

        String invalidResult = fridgeManager.removeFromFridgeById(9999);
        assertEquals("Fridge item not found.", invalidResult, "Should handle non-existent item IDs.");
    }

    @Test
    void getQuantityInfoById() {
        // Removed leading zero and 'L' suffix: 1012024 represents 01/01/2024
        fridgeManager.addToFridge("Milk", 1012024);
        List<FridgeItem> items = fridge.getAllFridgeItems();
        assertFalse(items.isEmpty(), "Fridge should contain at least one item.");
        FridgeItem milkItem = items.get(0); // Replaced getFirst() with get(0)

        String info = fridgeManager.getQuantityInfoById(milkItem.getId());
        assertEquals("ID: " + milkItem.getId() + ". Current quantity: 1.0 Liter", info, "Should return the correct quantity info for Milk.");

        String notFoundInfo = fridgeManager.getQuantityInfoById(9999);
        assertEquals("Fridge item not found.", notFoundInfo, "Should handle non-existent item IDs.");
    }

    @Test
    void updateFridgeItemQuantityById() {
        // Removed leading zero and 'L' suffix: 1012024 represents 01/01/2024
        fridgeManager.addToFridge("Milk", 1012024);
        List<FridgeItem> items = fridge.getAllFridgeItems();
        assertFalse(items.isEmpty(), "Fridge should contain at least one item.");
        FridgeItem milkItem = items.get(0); // Replaced getFirst() with get(0)

        String result = fridgeManager.updateFridgeItemQuantityById(milkItem.getId(), 0.5);
        assertEquals("Fridge item quantity updated successfully.", result, "Should update quantity successfully.");
        assertEquals(1.5, milkItem.getQuantity(), 0.001, "Milk quantity should be updated to 1.5.");

        String removeResult = fridgeManager.updateFridgeItemQuantityById(milkItem.getId(), -1.5);
        assertEquals("Fridge item removed due to zero or negative quantity.", removeResult, "Should remove Milk due to zero quantity.");
    }

    @Test
    void removeIngredient() {
        // Removed leading zeros and 'L' suffix
        fridgeManager.addToFridge("Milk", 1012024); // 01/01/2024
        fridgeManager.addToFridge("Milk", 2012024); // 02/01/2024

        String result = fridgeManager.removeIngredient("Milk", 1.0, "Liter");
        assertEquals("Ingredient removed successfully.", result, "Should remove 1.0 Liter of Milk.");

        String insufficientResult = fridgeManager.removeIngredient("Milk", 10.0, "Liter");
        assertEquals("Insufficient quantity to fulfill request.", insufficientResult, "Should handle insufficient quantities.");
    }

    @Test
    void getAllExpiredItems() {
        // Assuming today's date is 11/12/2024 (11th Dec 2024)
        // Adding an expired item: 01/01/2024
        fridgeManager.addToFridge("Milk", 1012024); // 01/01/2024
        // Adding a non-expired item: 01/01/2025
        fridgeManager.addToFridge("Eggs", 1012025); // 01/01/2025

        List<FridgeItem> expiredItems = fridgeManager.getAllExpiredItems();
        assertEquals(1, expiredItems.size(), "Should find one expired item.");
        assertEquals("Milk", expiredItems.get(0).getIngredient().getIngredientName(), "Expired item should be Milk.");
    }

    @Test
    void getExpiredItemsValue() {
        // Adding an expired item: 01/01/2024
        fridgeManager.addToFridge("Milk", 1012024); // 01/01/2024
        // Adding a non-expired item: 01/01/2025
        fridgeManager.addToFridge("Eggs", 1012025); // 01/01/2025

        String value = fridgeManager.getExpiredItemsValue();
        assertEquals("Total value of expired items: 2.5", value, "Total value of expired items should be correct.");
    }

    @Test
    void getTotalValueOfFridge() {
        // Adding non-expired items
        fridgeManager.addToFridge("Milk", 1012025); // 01/01/2025
        fridgeManager.addToFridge("Eggs", 1012025); // 01/01/2025

        String value = fridgeManager.getTotalValueOfFridge();
        assertEquals("Total value of items in fridge: 5.5", value, "Total value of items should be correct.");
    }
}
