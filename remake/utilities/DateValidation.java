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
    String date = String.format("%08d", expirationDate);
    return date.substring(0, 2) + "/" + date.substring(2, 4) + "/" + date.substring(4, 8);
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

  /**
   * Compares two dates by year, month and date.
   * Checks if the values are the same for year, month and day, if not they are
   * compared
   * and the result is returned as an int for easy comparison.
   *
   * @param date1 First date to compare with.
   * @param date2 Second date to compare with.
   * @return -1 if date1 is before date2, 0 if they are the same, and 1 if date1
   *         is after date2.
   */
  public static int compareDates(long date1, long date2) {
    // Converts to string + adds zeroes if required.
    String d1 = String.format("%08d", date1);
    String d2 = String.format("%08d", date2);

    // 0 is first digit, 7 is last. 0-2 is day, 2-4 is month, 4-8 is year.
    // (4, 8) means 4, 5, 6, 7 (8 is excluded).
    int year1 = Integer.parseInt(d1.substring(4, 8)); // Last 4 digits
    int month1 = Integer.parseInt(d1.substring(2, 4)); // Middle 2 digits
    int day1 = Integer.parseInt(d1.substring(0, 2)); // First 2 digits

    int year2 = Integer.parseInt(d2.substring(4, 8)); // Last 4 digits
    int month2 = Integer.parseInt(d2.substring(2, 4)); // Middle 2 digits
    int day2 = Integer.parseInt(d2.substring(0, 2)); // First 2 digits

    // Method basically first checks if the years are the same, then months, then
    // days.
    // IF they DONT match, it returns -1 or 1, depending on which is bigger.
    // compare year first
    if (year1 != year2) {
      return Integer.compare(year1, year2);
    }
    // If years are the same, compare month
    if (month1 != month2) {
      return Integer.compare(month1, month2);
    }
    // If months are the same, compare day
    return Integer.compare(day1, day2);
  }
}
