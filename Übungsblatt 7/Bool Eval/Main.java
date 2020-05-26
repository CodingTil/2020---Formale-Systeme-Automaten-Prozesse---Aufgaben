import java.io.*;
import java.util.Scanner;

/** The class containing the algortihm to evaluate boolean expressions.
 */
public class Main {

	// Current boolean expression to be evaluated.
	private static String boolExp;
	// Current index at the boolean expression.
	private static int index;

	/** This method takes boolean expressions, evaluates them and prints their results to console.
	 * @param args Boolean expressions, leave empty to read from sample-file
	 * @exception SyntaxErrorException if a boolean expression has an sytax error
	 * @exception BracketMismatchException if a boolean exception has a mismatch in brackets
	 * @exception UnknownSymbolException if a boolean contains an unknown symbol
	 */
	public static void main(String[] args) throws SyntaxErrorException, BracketMismatchException, UnknownSymbolException {
		if(args.length == 0) {
			try{
				FileInputStream fis = new FileInputStream("boolsche-ausdruecke");
				Scanner sc = new Scanner(fis);
				String line = "";
				while(sc.hasNextLine()) {
					line = sc.nextLine();
					boolExp = line;
					index = 0;
					boolean result = S();
					System.out.println("Boolean Expression: " + line + "\nResult: " + result + "\n");
				}
			}catch(FileNotFoundException e) {
				e.printStackTrace();
			}
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

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	//										ALGORITHM														//
	//////////////////////////////////////////////////////////////////////////////////////////////////////////

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
			return !N();
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
				throw new BracketMismatchException(boolExp);
			}
		}else if(index < boolExp.length() && boolExp.charAt(index) == '1') {
			index++;
			return true;
		}else if(index < boolExp.length() && boolExp.charAt(index) == '0') {
			index++;
			return false;
		}else if(index < boolExp.length() && boolExp.charAt(index) == '~' || boolExp.charAt(index) == '&' || boolExp.charAt(index) == '|') {
			throw new SyntaxErrorException("Syntax Error detected. '" + boolExp.charAt(index) + "' doesn't belong in '" + boolExp + "'.");
		}else {
			if(index < boolExp.length()) {
				throw new UnknownSymbolException(boolExp.charAt(index), boolExp);
			}else {
				throw new SyntaxErrorException("There seems to have been an internal problem regarding the length of the boolean expression '" + boolExp + "'.");
			}
		}
	}

}
