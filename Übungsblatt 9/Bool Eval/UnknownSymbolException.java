/** Represents an error caused by an unknown symbol.
 * Extends upon the {@link SyntaxErrorException} class.
 */
 public class UnknownSymbolException extends Exception {

 	/** Initializes the {@link SyntaxErrorException} object with a standard message and the unknown symbol.
	 * @param c the unknown symbol
	 * @param boolExp the boolean expression where the error occured
 	 */
 	public UnknownSymbolException(char c, String boolExp) {
 		super("An unknown symbol '" + c + "' was detected in '" + boolExp + "'.");
 	}
 }
