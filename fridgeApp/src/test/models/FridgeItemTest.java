package models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FridgeItemTest {

    private FridgeItem fridgeItem;
    private Ingredient ingredient;

    @BeforeEach
    void setUp() {
        ingredient = new Ingredient("Milk", "Dairy", 1.0, "Liter", 2.5);
        fridgeItem = new FridgeItem(ingredient, 1.0, 1699999999999L);
    }

    @Test
    void setQuantity() {
        fridgeItem.setQuantity(2.0);
        assertEquals(2.0, fridgeItem.getQuantity(), "Quantity should be updated to 2.0.");
    }

    @Test
    void addQuantity() {
        fridgeItem.addQuantity(1.5);
        assertEquals(2.5, fridgeItem.getQuantity(), "Quantity should be increased by 1.5.");
    }

    @Test
    void deductQuantity() {
        fridgeItem.deductQuantity(0.5);
        assertEquals(0.5, fridgeItem.getQuantity(), "Quantity should be reduced by 0.5.");

        fridgeItem.deductQuantity(1.0);
        assertEquals(0.0, fridgeItem.getQuantity(), "Quantity should not go below 0.");
    }

    @Test
    void testToString() {
        String expected = "ID: 1, Ingredient: Milk, Quantity: 1.0 Liter, Expiration Date: " + fridgeItem.getFormattedExpirationDate();
        assertEquals(expected, fridgeItem.toString(), "toString should return the correct string representation.");
    }
}
