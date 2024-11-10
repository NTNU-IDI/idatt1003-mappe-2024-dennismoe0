package applicationfolder.utilities;

/**
 * Class providing methods to convert between subdivisions of the same unit.
 * I.e. ml to l or back, grams to kg or back, etc., and to check compatibility
 * between units.
 *
 * @author Dennis Moe
 */
public class UnitConverter {

  // Conversion factors
  public static final double GRAMS_TO_KG = 0.001;
  public static final double KG_TO_GRAMS = 1000;
  public static final double MILLILITERS_TO_LITERS = 0.001;
  public static final double CENTILITERS_TO_LITERS = 0.01;
  public static final double MILLILITERS_TO_CENTILITERS = 0.1;
  public static final double LITERS_TO_CENTILITERS = 100;
  public static final double LITERS_TO_MILLILITERS = 1000;
  public static final double CENTILITERS_TO_MILLILITERS = 100;

  // Unit categories for compatibility checks
  private static final String[] WEIGHT_UNITS = { "g", "kg" };
  private static final String[] VOLUME_UNITS = { "ml", "cl", "L" };

  /**
   * Converts a quantity from one unit to another compatible unit.
   */
  public static double convertUnit(double quantity, String fromUnit, String toUnit) {
    if (fromUnit.equals("g") && toUnit.equals("kg")) {
      return quantity * GRAMS_TO_KG;
    } else if (fromUnit.equals("kg") && toUnit.equals("g")) {
      return quantity * KG_TO_GRAMS;
    } else if (fromUnit.equals("ml") && toUnit.equals("L")) {
      return quantity * MILLILITERS_TO_LITERS;
    } else if (fromUnit.equals("L") && toUnit.equals("ml")) {
      return quantity * LITERS_TO_MILLILITERS;
    } else if (fromUnit.equals("cl") && toUnit.equals("L")) {
      return quantity * CENTILITERS_TO_LITERS;
    } else if (fromUnit.equals("L") && toUnit.equals("cl")) {
      return quantity * LITERS_TO_CENTILITERS;
    } else if (fromUnit.equals("ml") && toUnit.equals("cl")) {
      return quantity * MILLILITERS_TO_CENTILITERS;
    } else if (fromUnit.equals("cl") && toUnit.equals("ml")) {
      return quantity * CENTILITERS_TO_MILLILITERS;
    } else {
      return quantity; // Return unchanged if units are the same or unsupported
    }
  }

  /**
   * Checks if two units are compatible (e.g., both are weight units or both are
   * volume units).
   *
   * @param unit1 The first unit.
   * @param unit2 The second unit.
   * @return true if both units are of the same type, false otherwise.
   */
  public static boolean areUnitsCompatible(String unit1, String unit2) {
    return (isWeightUnit(unit1) && isWeightUnit(unit2))
        || (isVolumeUnit(unit1) && isVolumeUnit(unit2));
  }

  /**
   * Checks if a unit is a weight unit.
   *
   * @param unit The unit to check.
   * @return true if the unit is a weight unit, false otherwise.
   */
  public static boolean isWeightUnit(String unit) {
    for (String weightUnit : WEIGHT_UNITS) {
      if (weightUnit.equalsIgnoreCase(unit)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if a unit is a volume unit.
   *
   * @param unit The unit to check.
   * @return true if the unit is a volume unit, false otherwise.
   */
  public static boolean isVolumeUnit(String unit) {
    for (String volumeUnit : VOLUME_UNITS) {
      if (volumeUnit.equalsIgnoreCase(unit)) {
        return true;
      }
    }
    return false;
  }
}
