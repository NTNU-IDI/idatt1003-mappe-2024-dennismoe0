package utilities;

/**
 * Utility class for unit conversion.
 * Checks type of unit and converts between them.
 * Will convert between same type of units: i.e. from "kg" to "g", or "L" to
 * "mL", but not from grams to liters.
 * Does this for grams, kilograms, liters, centiliters and milliliters.
 *
 * @author Dennis Moe
 */
public class UnitUtility {
  public static final double GRAMS_TO_KG = 0.001;
  public static final double KG_TO_GRAMS = 1000;

  public static final double MILLILITERS_TO_LITERS = 0.001;
  public static final double CENTILITERS_TO_LITERS = 0.01;

  public static final double MILLILITER_TO_CENTILITER = 0.1;
  public static final double LITER_TO_CENTILITER = 100;

  public static final double LITER_TO_MILLILITER = 1000;
  public static final double CENTILITER_TO_MILLILITER = 10;

  private static final String[] WEIGHT_UNITS = { "g", "kg" };
  private static final String[] VOLUME_UNITS = { "mL", "cL", "L" };

  public static double convertUnit(double quantity,
      String fromMeasuringUnit, String toMeasuringUnit) {
    if (fromMeasuringUnit.equals(toMeasuringUnit)) {
      return quantity;
    } else if (fromMeasuringUnit.equals("g") && toMeasuringUnit.equals("kg")) {
      return quantity * GRAMS_TO_KG;
    } else if (fromMeasuringUnit.equals("kg") && toMeasuringUnit.equals("g")) {
      return quantity * KG_TO_GRAMS;
    } else if (fromMeasuringUnit.equals("mL") && toMeasuringUnit.equals("L")) {
      return quantity * MILLILITERS_TO_LITERS;
    } else if (fromMeasuringUnit.equals("cL") && toMeasuringUnit.equals("L")) {
      return quantity * CENTILITERS_TO_LITERS;
    } else if (fromMeasuringUnit.equals("mL") && toMeasuringUnit.equals("cL")) {
      return quantity * MILLILITER_TO_CENTILITER;
    } else if (fromMeasuringUnit.equals("L") && toMeasuringUnit.equals("cL")) {
      return quantity * LITER_TO_CENTILITER;
    } else if (fromMeasuringUnit.equals("L") && toMeasuringUnit.equals("mL")) {
      return quantity * LITER_TO_MILLILITER;
    } else if (fromMeasuringUnit.equals("cL") && toMeasuringUnit.equals("mL")) {
      return quantity * CENTILITER_TO_MILLILITER;
    } else {
      return -1;
    }
  }

  /**
   * Checks if the conversion between two measuring units is valid.
   *
   * @param fromMeasuringUnit the unit to convert from
   * @param toMeasuringUnit   the unit to convert to
   * @return true if the conversion is valid, false otherwise
   */
  public static boolean isValidCompatability(String fromMeasuringUnit, String toMeasuringUnit) {
    if (fromMeasuringUnit.equals(toMeasuringUnit)) {
      return true;
    } else if (isWeightUnit(fromMeasuringUnit) && isWeightUnit(toMeasuringUnit)) {
      return true;
    } else if (isVolumeUnit(fromMeasuringUnit) && isVolumeUnit(toMeasuringUnit)) {
      return true; // Redundant but will keep for safety reasons.
    } else {
      return false;
    }
  }

  private static boolean isWeightUnit(String unit) {
    for (String weightUnit : WEIGHT_UNITS) {
      if (weightUnit.equals(unit)) {
        return true;
      }
    }
    return false;
  }

  private static boolean isVolumeUnit(String unit) {
    for (String volumeUnit : VOLUME_UNITS) {
      if (volumeUnit.equals(unit)) {
        return true;
      }
    }
    return false;
  }
}
