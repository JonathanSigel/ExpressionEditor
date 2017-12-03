/**
 * Starter code to implement an ExpressionParser. Your parser methods should use the following grammar:
 * E := A | X
 * A := A+M | M
 * M := M*M | X
 * X := (E) | L
 * L := [0-9]+ | [a-z]
 */

/*
public class SimpleExpressionParser implements ExpressionParser {
	/*
	 * Attempts to create an expression tree -- flattened as much as possible -- from the specified String.
         * Throws a ExpressionParseException if the specified string cannot be parsed.
	 * @param str the string to parse into an expression tree
	 * @param withJavaFXControls you can just ignore this variable for R1
	 * @return the Expression object representing the parsed expression tree

	public Expression parse (String str, boolean withJavaFXControls) throws ExpressionParseException {
		// Remove spaces -- this simplifies the parsing logic
		str = str.replaceAll(" ", "");
		Expression expression = parseExpression(str);
		if (expression == null) {
			// If we couldn't parse the string, then raise an error
			throw new ExpressionParseException("Cannot parse expression: " + str);

		// Flatten the expression before returning
		expression.flatten();
		return expression;
	}
	
	protected Expression parseExpression (String str) {
		Expression expression;
//PARSE E
		boolean parseE (String str) {
			if (parseA(str) || parseX(str)) {
				return true;
			}
			return false;
		}

//PARSE A
		boolean parseA (String str) {
			int idxOfPlus = str.indexOf('+');
			while (inxOfPlus >= 0) {
				if (parseA(str.substring(0, idxOfPlus)) && parseM(str.substring(idxOfPlus+1))) {
					return true;
				}
				idxOfPlus = str.indexOf('+', idxOfPlus+1)
			}

			if (parseM(str)) {
				return true;
			}
			return false;
		}
//PARSE M
		boolean parseM (String str) {
			int idxOfMult = str.indexOf('*');
			while (inxOfMult >= 0) {
				if (parseM(str.substring(0, idxOfMult)) && parseM(str.substring(idxOfMult+1))) {
					return true;
				}
				idxOfMult = str.indexOf('*', idxOfPlus+1)
			}

			if (parseX(str)) {
				return true;
			}
			return false;
		}

		boolean parseX (String str)

	}
		
		// TODO implement me
		return null;
	}
}
*/
