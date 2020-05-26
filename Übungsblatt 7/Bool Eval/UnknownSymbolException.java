/** Represents an error caused by an unknown symbol.
 * Extends upon the {@link SyntaxErrorException} class.
 */
 public class UnknownSymbolException extends Exception {

 	/** Initializes the {@link SyntaxErrorException} object with a standard message and the unknown symbol.
	 * @param c the unknown symbol
 	 */
 	public UnknownSymbolException(char c) {
 		super("An unknown symbol was detected: " + c);
 	}
 }
