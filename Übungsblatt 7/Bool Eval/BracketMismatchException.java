/** Represents an error caused by a mismatched bracket.
 * Extends upon the {@link SyntaxErrorException} class.
 */
 public class BracketMismatchException extends Exception {

 	/** Initializes the {@link SyntaxErrorException} object with a standard message.
	 * @param boolExp the boolean expression where the error occured
 	 */
 	public BracketMismatchException(String boolExp) {
 		super("There is a mismatch in Brackets in '" + boolExp + "'.");
 	}
 }
