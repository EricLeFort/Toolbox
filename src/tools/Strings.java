package tools;
/**
 * @author Eric Le Fort
 * @version 01
 */

public class Strings{

	/**
	 * Returns true if the character passed in is a whitespace character such as tab, space or newline.
	 * @param a - The character to be checked.
	 * @return Whether the character is whitespace.
	 */
	public static boolean isWhitespace(char a){
		return a == ' ' ||
			a == '\t' ||
			a == System.getProperty("line.separator").charAt(0);
	}//isWhitespace()
	
}//Strings
