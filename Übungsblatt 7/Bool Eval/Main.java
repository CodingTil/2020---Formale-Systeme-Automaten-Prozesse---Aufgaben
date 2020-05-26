import java.security.InvalidParameterException;

/** The class containing the algortihm to evaluate boolean expressions.
 */
public class Main {

	// Current boolean expression to be evaluated.
	private static String boolExp;
	// Current index at the boolean expression.
	private static int index;

	/** This method takes boolean expressions, evaluates them and prints their results to console.
	 * @param args Boolean expressions
	 * @exception InvalidParameterException if the parameter is empty
	 */
	public static void main(String[] args) throws InvalidParameterException, SyntaxErrorException, BracketMismatchException, UnknownSymbolException {
		if(args.length == 0) {
			throw new InvalidParameterException("No boolean expression was handed over.");
		}else {
			for(String be : args) {
				if(be == null || be.equalsIgnoreCase("")) continue;
				boolExp = be;
				index = 0;
				boolean result = S();
				System.out.println("Boolean Expression: " + be + "\nResult: " + result + "\n");
			}
		}
	}

	private static boolean S() throws SyntaxErrorException, BracketMismatchException, UnknownSymbolException {
		boolean result = P();
		if(index < boolExp.length() && boolExp.charAt(index) == '|') {
			index++;
			return result | S();
		}else {
			return result;
		}
	}

	private static boolean P() throws SyntaxErrorException, BracketMismatchException, UnknownSymbolException {
		boolean result = N();
		if(index < boolExp.length() && boolExp.charAt(index) == '&') {
			index++;
			return result & P();
		}else {
			return result;
		}
	}

	private static boolean N() throws SyntaxErrorException, BracketMismatchException, UnknownSymbolException {
		if(index < boolExp.length() && boolExp.charAt(index) == '~') {
			index++;
			return !A();
		}else {
			return A();
		}
	}

	private static boolean A() throws SyntaxErrorException, BracketMismatchException, UnknownSymbolException {
		if(index < boolExp.length() && boolExp.charAt(index) == '(') {
			index++;
			boolean result = S();
			if(index < boolExp.length() && boolExp.charAt(index) == ')') {
				index++;
				return result;
			}else {
				throw new BracketMismatchException();
			}
		}else if(index < boolExp.length() && boolExp.charAt(index) == '1') {
			index++;
			return true;
		}else if(index < boolExp.length() && boolExp.charAt(index) == '0') {
			index++;
			return false;
		}else if(index < boolExp.length() && boolExp.charAt(index) == '~' || boolExp.charAt(index) == '&' || boolExp.charAt(index) == '|') {
			throw new SyntaxErrorException("Syntax Error detected. '" + boolExp.charAt(index) + "' doesn't belong here.");
		}else {
			if(index < boolExp.length()) {
				throw new UnknownSymbolException(boolExp.charAt(index));
			}else {
				throw new SyntaxErrorException("There seems to have been an internal problem regarding the length of the boolean expression.");
			}
		}
	}

}
