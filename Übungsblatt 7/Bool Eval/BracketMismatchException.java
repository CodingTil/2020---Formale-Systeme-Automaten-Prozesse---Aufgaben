/** Represents an error caused by a mismatched bracket.
 * Extends upon the {@link SyntaxErrorException} class.
 */
 public class BracketMismatchException extends Exception {

 	/** Initializes the {@link SyntaxErrorException} object with a standard message.
 	 */
 	public BracketMismatchException() {
 		super("There is a mismatch in Brackets.");
 	}
 }
