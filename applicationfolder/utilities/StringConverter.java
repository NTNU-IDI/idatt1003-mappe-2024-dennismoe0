package applicationfolder.utilities;

/**
 * Class for converting string inputs for Ingredients, Recipes and CookBooks
 * into object-names.
 * For example: User input "Ground Beef" would be -> 'groundbeef'.
 */
public class StringConverter {
  /**
   * Converts input to lowercase and removes whitespaces.
   *
   * @param input User inputted string.
   * @return formatted name.
   */
  public static String convertInputToObjectName(String input) {
    return input.toLowerCase().replaceAll("\\s+", ""); // Replaces whitespaces with nothing.
  }
}
