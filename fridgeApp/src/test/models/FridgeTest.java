package models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class FridgeTest {

    private Fridge fridge;
    private FridgeItem fridgeItem1;
    private FridgeItem fridgeItem2;

    @BeforeEach
    void setUp() {
        fridge = new Fridge();

        Ingredient ingredient1 = new Ingredient("Milk", "Dairy", 1.0, "Liter", 2.5);
        Ingredient ingredient2 = new Ingredient("Eggs", "Protein", 12, "Pieces", 3.0);

        fridgeItem1 = new FridgeItem(ingredient1, ingredient1.getIngredientBaseWeight(), 25062025);
        fridgeItem2 = new FridgeItem(ingredient2, ingredient2.getIngredientBaseWeight(), 25062025);
    }

    @Test
    void addFridgeItem() {
        fridge.addFridgeItem(fridgeItem1);
        assertEquals(1, fridge.getAllFridgeItems().size(), "Fridge should contain one item.");
        assertTrue(fridge.getAllIngredientInstancesByName("Milk").contains(fridgeItem1), "Fridge should contain the added item.");
    }

    @Test
    void removeFridgeItemById() {
        fridge.addFridgeItem(fridgeItem1);
        fridge.addFridgeItem(fridgeItem2);

        assertTrue(fridge.removeFridgeItemById(fridgeItem1.getId()), "Should successfully remove the fridge item by ID.");
        assertFalse(fridge.getAllFridgeItems().contains(fridgeItem1), "Removed item should no longer be in the fridge.");
    }

    @Test
    void calculateTotalQuantity() {
        fridge.addFridgeItem(fridgeItem1);
        fridge.addFridgeItem(fridgeItem2);

        assertEquals(1.0, fridge.calculateTotalQuantity("Milk"), "Total quantity of Milk should be 1.0.");
        assertEquals(12.0, fridge.calculateTotalQuantity("Eggs"), "Total quantity of Eggs should be 12.0.");
    }

    @Test
    void getFridgeItemById() {
        fridge.addFridgeItem(fridgeItem1);
        fridge.addFridgeItem(fridgeItem2);

        assertEquals(fridgeItem1, fridge.getFridgeItemById(fridgeItem1.getId()), "Should retrieve the correct fridge item by ID.");
        assertNull(fridge.getFridgeItemById(9999), "Should return null for a non-existent ID.");
    }

    @Test
    void updateFridgeItemQuantityById() {
        fridge.addFridgeItem(fridgeItem1);

        assertTrue(fridge.updateFridgeItemQuantityById(fridgeItem1.getId(), 1.5), "Quantity update should be successful.");
        assertEquals(2.5, fridgeItem1.getQuantity(), "Updated quantity should be 2.5.");

        assertFalse(fridge.updateFridgeItemQuantityById(9999, 1.0), "Should fail to update non-existent item.");
    }

    @Test
    void getAllIngredientInstancesByName() {
        fridge.addFridgeItem(fridgeItem1);
        fridge.addFridgeItem(fridgeItem2);

        List<FridgeItem> milkItems = fridge.getAllIngredientInstancesByName("Milk");
        assertEquals(1, milkItems.size(), "Should retrieve one instance of Milk.");
        assertTrue(milkItems.contains(fridgeItem1), "Milk instance should match.");
    }

    @Test
    void getTotalQuantityOfIngredient() {
        fridge.addFridgeItem(fridgeItem1);
        fridge.addFridgeItem(fridgeItem2);

        assertEquals(1.0, fridge.getTotalQuantityOfIngredient("Milk"), "Total quantity of Milk should be 1.0.");
        assertEquals(12.0, fridge.getTotalQuantityOfIngredient("Eggs"), "Total quantity of Eggs should be 12.0.");
    }

    @Test
    void getAllFridgeItems() {
        fridge.addFridgeItem(fridgeItem1);
        fridge.addFridgeItem(fridgeItem2);

        List<FridgeItem> allItems = fridge.getAllFridgeItems();
        assertEquals(2, allItems.size(), "Fridge should contain two items.");
        assertTrue(allItems.contains(fridgeItem1), "Fridge should contain Milk.");
        assertTrue(allItems.contains(fridgeItem2), "Fridge should contain Eggs.");
    }
}
