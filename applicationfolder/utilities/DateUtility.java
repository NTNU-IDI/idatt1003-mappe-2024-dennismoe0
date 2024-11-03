package applicationfolder.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for handling date formatting and parsing operations.
 */
public class DateUtility {

  // Define the format pattern
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

  /**
   * Formats a Date object into a string representation using the format "dd-MM-yyyy".
   *
   * @param date the Date object to be formatted
   * @return a formatted date string in the "dd-MM-yyyy" format
   */
  public static String formatDate(Date date) {
    return dateFormat.format(date);
  }

  /**
   * Parses a string into a Date object using the format "dd-MM-yyyy".
   *
   * @param dateString the string representation of the date to be parsed
   * @return the parsed Date object
   * @throws Exception if the date string is not in the correct format
   */
  public static Date parseDate(String dateString) throws Exception {
    return dateFormat.parse(dateString);
  }
}
