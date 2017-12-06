/**
 * Parser following the grammar :
 * E := A | X
 * A := A+M | M
 * M := M*M | X
 * X := (E) | L
 * L := [0-9]+ | [a-z]
 */
public class SimpleExpressionParser implements ExpressionParser {

    /*
     * Attempts to create an expression tree -- flattened as much as possible -- from the specified String.
     * Throws a ExpressionParseException if the specified string cannot be parsed.
     * @param str the string to parse into an expression tree
     * @param withJavaFXControls you can just ignore this variable for R1
     * @return the Expression object representing the parsed expression tree
     */
    public Expression parse (String str, boolean withJavaFXControls) throws ExpressionParseException {
        // Remove spaces -- this simplifies the parsing logic
        str = str.replaceAll(" ", "");
        Expression expression = parseExpression(str);
        if (expression == null) {
            // If we couldn't parse the string, then raise an error
            throw new ExpressionParseException("Cannot parse expression: " + str);
        }

        // Flatten the expression before returning
        expression.flatten();
        return expression;
    }

    /**
     * Starts sequence of recursively parsing a string into an expression tree
     * @param str the string to parse into an expression tree
     * @return the Expression object representing the parsed but unflattened expression tree
     */
    private Expression parseExpression (String str) {
        return parseAddition(str);
    }

    /**
     * Parses string based on first valid instance of "+"
     * @param str the string to parse into an expression tree
     * @return the Expression object representing the parsed but unflattened expression tree
     */
    private Expression parseAddition(String str) {
        int indexPlus = str.indexOf("+");
        while (indexPlus > -1) {

            // divides string based on location of "+"
            final String subString1 = str.substring(0, indexPlus);
            final String subString2 = str.substring(indexPlus + 1, str.length());

            // parses first subexpression
            final Expression subExpression1 = parseMultiplication(subString1);
            // if first subexpression was able to be parsed, continue
            if (subExpression1 != null) {
                // parses second subexpression
                final Expression subExpression2 = parseAddition(subString2);
                //if both subexpressions are able to be parsed, create new compound expression and add the two subexpressions to it
                if (subExpression2 != null) {
                    final CompoundExpression addExpression = new AdditiveExpression();
                    addExpression.addSubexpression(subExpression1);
                    addExpression.addSubexpression(subExpression2);
                    return addExpression;
                }
            }

            // if previous instance of "+" did not yield valid subexpressions, check the next one
            indexPlus = str.indexOf("+", (indexPlus + 1));
        }

        // if there are no valid instances of "+", check multiplication
        return parseMultiplication(str);
    }

    /**
     * Parses string based on first valid instance of "*"
     * @param str the string to parse into an expression tree
     * @return the Expression object representing the parsed but unflattened expression tree
     */
    private Expression parseMultiplication(String str) {
        int indexTimes = str.indexOf("*");
        while (indexTimes > -1) {

            // divides string based on location of "*"
            final String subString1 = str.substring(0, indexTimes);
            final String subString2 = str.substring(indexTimes + 1, str.length());

            // parses first subexpression
            final Expression subExpression1 = parseParentheses(subString1);
            // if first subexpression was able to be parsed, continue
            if (subExpression1 != null) {
                // parses second subexpression
                final Expression subExpression2 = parseMultiplication(subString2);
                //if both subexpressions are able to be parsed, create new compound expression and add the two subexpressions to it
                if (subExpression2 != null) {
                    final CompoundExpression multExpression = new MultiplicativeExpression();
                    multExpression.addSubexpression(subExpression1);
                    multExpression.addSubexpression(subExpression2);
                    return multExpression;
                }
            }

            // if previous instance of "*" did not yield valid subexpressions, check the next one
            indexTimes = str.indexOf("*", (indexTimes + 1));
        }

        // if there are no valid instances of "*", check parentheses
        return parseParentheses(str);
    }

    /**
     * Parses string based on first valid instance of properly used parentheses
     * @param str the string to parse into an expression tree
     * @return the Expression object representing the parsed but unflattened expression tree
     */
    private Expression parseParentheses(String str) {
        int indexLeftParen = str.indexOf("(");
        int indexRightParen = str.lastIndexOf(")");
        if (indexLeftParen == 0) {
            if (indexRightParen == str.length() - 1) {
                final String subString = str.substring(indexLeftParen + 1, indexRightParen);

                // parses subexpression within parentheses
                final Expression subExpression = parseExpression(subString);
                //if subexpression is able to be parsed, create new compound expression and add the subexpression to it
                if (subExpression != null) {
                    final CompoundExpression parenExpression = new ParentheticalExpression();
                    parenExpression.addSubexpression(subExpression);
                    return parenExpression;
                }
            }
        }

        // if there are no valid instances of parentheses check numbers and letters
        return parseNumbersAndLetters(str);
    }

    /**
     * Parses string based on valid occurrence of number and letter characters
     * @param str the string to parse into an expression tree
     * @return the Expression object representing the parsed but unflattened expression tree
     */
    private Expression parseNumbersAndLetters(String str) {
        // checks numbers
        if (str.matches("[0-9]+")) {
            return new LiteralExpression(str);
        }

        // checks letters
        if (str.matches("[a-z]")) {
            return new LiteralExpression(str);
        }

        // returns null if string could not be parsed
        return null;
    }
}

