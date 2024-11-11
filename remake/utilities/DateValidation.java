package utilities;

public class DateValidation {

  /**
   * Method to check if the date is valid for input.
   * Also checks if the date is valid for the month it is in.
   * 
   * Checks if the given day is valid for the given month and year.
   * This method ensures that the day is within the valid range for the specified month and year.
   *
   * @param expirationDay the day of the expiration date
   * @param expirationMonth the month of the expiration date
   * @param expirationYear the year of the expiration date
   * @return true if the day is valid for the given month and year, false otherwise
   */
  public static boolean isValidDay(int expirationDay, int expirationMonth, int expirationYear) {
    if (expirationDay < 1) {
        return false;
     }

    return switch (expirationMonth) {
      case 1, 3, 5, 7, 8, 10, 12 -> expirationDay <= 31; // 31 days
      case 4, 6, 9, 11 -> expirationDay <= 30; // 30 days
      case 2 -> expirationDay <= (isLeapYear(expirationYear) ? 29 : 28); // 28 or 29 days
      default -> false;
    }
  }

  public static boolean isLeapYear(int year) {
    return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
  }
}
