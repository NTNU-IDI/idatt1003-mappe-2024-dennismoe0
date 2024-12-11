package utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitUtilityTest {

    @Test
    void convertUnit() {
        // Same unit conversions
        assertEquals(100.0, UnitUtility.convertUnit(100.0, "g", "g"), "Converting grams to grams should return the same value.");
        assertEquals(2.5, UnitUtility.convertUnit(2.5, "kg", "kg"), "Converting kilograms to kilograms should return the same value.");
        assertEquals(500.0, UnitUtility.convertUnit(500.0, "mL", "mL"), "Converting milliliters to milliliters should return the same value.");
        assertEquals(75.0, UnitUtility.convertUnit(75.0, "cL", "cL"), "Converting centiliters to centiliters should return the same value.");
        assertEquals(1.0, UnitUtility.convertUnit(1.0, "L", "L"), "Converting liters to liters should return the same value.");

        // Valid weight conversions
        assertEquals(0.5, UnitUtility.convertUnit(500.0, "g", "kg"), 0.0001, "500 grams should convert to 0.5 kilograms.");
        assertEquals(2000.0, UnitUtility.convertUnit(2.0, "kg", "g"), 0.0001, "2 kilograms should convert to 2000 grams.");

        // Valid volume conversions
        assertEquals(1.0, UnitUtility.convertUnit(1000.0, "mL", "L"), 0.0001, "1000 milliliters should convert to 1 liter.");
        assertEquals(0.5, UnitUtility.convertUnit(50.0, "cL", "L"), 0.0001, "50 centiliters should convert to 0.5 liters.");
        assertEquals(100.0, UnitUtility.convertUnit(1.0, "L", "cL"), 0.0001, "1 liter should convert to 100 centiliters.");
        assertEquals(1000.0, UnitUtility.convertUnit(1.0, "L", "mL"), 0.0001, "1 liter should convert to 1000 milliliters.");
        assertEquals(10.0, UnitUtility.convertUnit(1.0, "cL", "mL"), 0.0001, "1 centiliter should convert to 10 milliliters.");
        assertEquals(0.1, UnitUtility.convertUnit(1.0, "mL", "cL"), 0.0001, "1 milliliter should convert to 0.1 centiliters.");

        // Zero quantity conversions
        assertEquals(0.0, UnitUtility.convertUnit(0.0, "g", "kg"), 0.0001, "0 grams should convert to 0 kilograms.");
        assertEquals(0.0, UnitUtility.convertUnit(0.0, "L", "mL"), 0.0001, "0 liters should convert to 0 milliliters.");

        // Negative quantity conversions
        assertEquals(-0.1, UnitUtility.convertUnit(-100.0, "g", "kg"), 0.0001, "Negative 100 grams should convert to -0.1 kilograms.");
        assertEquals(-500.0, UnitUtility.convertUnit(-0.5, "kg", "g"), 0.0001, "Negative 0.5 kilograms should convert to -500 grams.");

        // Invalid conversions between different measurement types
        assertEquals(-1.0, UnitUtility.convertUnit(100.0, "g", "L"), "Converting grams to liters should return -1.");
        assertEquals(-1.0, UnitUtility.convertUnit(2.0, "kg", "mL"), "Converting kilograms to milliliters should return -1.");
        assertEquals(-1.0, UnitUtility.convertUnit(500.0, "mL", "g"), "Converting milliliters to grams should return -1.");
        assertEquals(-1.0, UnitUtility.convertUnit(75.0, "cL", "kg"), "Converting centiliters to kilograms should return -1.");
        assertEquals(-1.0, UnitUtility.convertUnit(1.0, "L", "g"), "Converting liters to grams should return -1.");

        // Invalid unit conversions with unknown units
        assertEquals(-1.0, UnitUtility.convertUnit(100.0, "oz", "g"), "Converting unknown unit 'oz' to grams should return -1.");
        assertEquals(-1.0, UnitUtility.convertUnit(1.0, "kg", "pound"), "Converting kilograms to unknown unit 'pound' should return -1.");
        assertEquals(-1.0, UnitUtility.convertUnit(50.0, "cL", "ml"), "Converting centiliters to unknown unit 'ml' (case-sensitive) should return -1.");
    }

    @Test
    void isValidCompatability() {
        // Same unit compatibility
        assertTrue(UnitUtility.isValidCompatability("g", "g"), "Grams should be compatible with grams.");
        assertTrue(UnitUtility.isValidCompatability("kg", "kg"), "Kilograms should be compatible with kilograms.");
        assertTrue(UnitUtility.isValidCompatability("mL", "mL"), "Milliliters should be compatible with milliliters.");
        assertTrue(UnitUtility.isValidCompatability("cL", "cL"), "Centiliters should be compatible with centiliters.");
        assertTrue(UnitUtility.isValidCompatability("L", "L"), "Liters should be compatible with liters.");

        // Valid weight unit compatibilities
        assertTrue(UnitUtility.isValidCompatability("g", "kg"), "Grams should be compatible with kilograms.");
        assertTrue(UnitUtility.isValidCompatability("kg", "g"), "Kilograms should be compatible with grams.");

        // Valid volume unit compatibilities
        assertTrue(UnitUtility.isValidCompatability("mL", "cL"), "Milliliters should be compatible with centiliters.");
        assertTrue(UnitUtility.isValidCompatability("cL", "mL"), "Centiliters should be compatible with milliliters.");
        assertTrue(UnitUtility.isValidCompatability("mL", "L"), "Milliliters should be compatible with liters.");
        assertTrue(UnitUtility.isValidCompatability("cL", "L"), "Centiliters should be compatible with liters.");
        assertTrue(UnitUtility.isValidCompatability("L", "mL"), "Liters should be compatible with milliliters.");
        assertTrue(UnitUtility.isValidCompatability("L", "cL"), "Liters should be compatible with centiliters.");

        // Invalid compatibilities between different measurement types
        assertFalse(UnitUtility.isValidCompatability("g", "mL"), "Grams should not be compatible with milliliters.");
        assertFalse(UnitUtility.isValidCompatability("kg", "cL"), "Kilograms should not be compatible with centiliters.");
        assertFalse(UnitUtility.isValidCompatability("mL", "g"), "Milliliters should not be compatible with grams.");
        assertFalse(UnitUtility.isValidCompatability("L", "kg"), "Liters should not be compatible with kilograms.");

        // Invalid compatibilities with unknown units
        assertFalse(UnitUtility.isValidCompatability("oz", "g"), "Unknown unit 'oz' should not be compatible with grams.");
        assertFalse(UnitUtility.isValidCompatability("kg", "pound"), "Kilograms should not be compatible with unknown unit 'pound'.");
        assertFalse(UnitUtility.isValidCompatability("cL", "ml"), "Centiliters should not be compatible with unknown unit 'ml' (case-sensitive).");
        assertFalse(UnitUtility.isValidCompatability("g", "unknown"), "Grams should not be compatible with unknown unit 'unknown'.");
        assertFalse(UnitUtility.isValidCompatability("unknown1", "unknown2"), "Unknown units should not be compatible with each other.");
    }
}
