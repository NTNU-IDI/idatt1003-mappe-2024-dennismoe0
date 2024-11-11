package utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for date validation.
 * User input will be Year, Month, then Day.
 * Checks if the given day is valid for the specified month and year and if the
 * entire date is valid.
 *
 * @author Dennis Moe
 */
public class DateValidation {

  /**
   * Checks if the given day is valid for the specified month and year.
   * Ensures the day falls within the valid range for the month,
   * considering the number of days in each month and accounting for leap years.
   *
   * @param expirationDay   the day part of the expiration date (1–31, depending
   *                        on the month)
   * @param expirationMonth the month part of the expiration date (1–12, where 1 =
   *                        January, 12 = December)
   * @param expirationYear  the year part of the expiration date (e.g., 2024),
   *                        used to determine leap years
   * @return true if the day is within the valid range for the specified month and
   *         year, false if invalid
   */
  public static boolean isValidDay(int expirationDay, int expirationMonth, int expirationYear) {
    if (expirationDay < 1) {
      return false;
    }

    return switch (expirationMonth) {
      case 1, 3, 5, 7, 8, 10, 12 -> expirationDay <= 31; // Months with 31 days
      case 4, 6, 9, 11 -> expirationDay <= 30; // Months with 30 days
      case 2 -> expirationDay <= (isLeapYear(expirationYear)
          ? 29
          : 28); // February, with leap year check
      default -> false; // Invalid month
    };
  }

  /**
   * Checks if the given year is a leap year.
   *
   * @param year the year to check (e.g., 2024)
   * @return true if the year is a leap year, false otherwise
   */
  public static boolean isLeapYear(int year) {
    return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
  }

  /**
   * Checks if the given month is within the valid range.
   *
   * @param expirationMonth the month part of the expiration date (1–12, where 1 =
   *                        January, 12 = December)
   * @return true if the month is within the valid range, false if invalid
   */
  public static boolean isValidMonth(int expirationMonth) {
    return expirationMonth >= 1 && expirationMonth <= 12;
  }

  /**
   * Checks if the given year is within the valid range.
   *
   * @param expirationYear the year part of the expiration date (e.g., 2024)
   * @return true if the year is within the valid range, false if invalid
   */
  public static boolean isValidYear(int expirationYear) {
    return expirationYear >= 1969 && expirationYear <= 2200;
  }

  /**
   * Checks if the given date is valid.
   * Ensures the year, month, and day are within valid ranges.
   *
   * @param expirationDay   the day part of the expiration date (1–31, depending
   *                        on the month)
   * @param expirationMonth the month part of the expiration date (1–12, where 1 =
   *                        January, 12 = December)
   * @param expirationYear  the year part of the expiration date (e.g., 2024)
   * @return true if the date is valid, false if invalid
   */
  public static boolean isValidDate(int expirationDay, int expirationMonth, int expirationYear) {
    return isValidYear(expirationYear) && isValidMonth(expirationMonth)
        && isValidDay(expirationDay, expirationMonth, expirationYear);
  }

  /**
   * Formats the expiration date.
   * Converts long '25062024' to String "25/06/2024".
   *
   * @param expirationDate the expiration date in milliseconds
   * @return the formatted expiration date
   */
  public static String formatDate(long expirationDate) {
    String date = String.valueOf(expirationDate);
    return date.substring(6, 8) + "/" + date.substring(4, 6) + "/" + date.substring(0, 4);
  }

  /**
   * Gets today's date as a long in the format ddMMyyyy.
   *
   * @return today's date as a long
   */
  public static long getTodayAsLong() {
    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
    return Long.parseLong(today.format(formatter));
  }
}
