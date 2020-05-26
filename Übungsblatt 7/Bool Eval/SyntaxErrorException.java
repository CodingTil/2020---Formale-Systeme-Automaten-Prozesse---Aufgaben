import java.lang.Exception;
/** Represents an error caused by false syntax.
 * Extends upon the {@link java.io.Exception} class.
 */
public class SyntaxErrorException extends Exception {

	/** Initializes the {@link java.io.Exception} object with a standard message.
	 */
	public SyntaxErrorException() {
		super("A Syntax Error was detected.");
	}

	/** Initializes the {@link java.io.Exception} object with a custom message.
	 * @param message custom exception message
	 */
	public SyntaxErrorException(String message) {
		super(message);
	}
}
