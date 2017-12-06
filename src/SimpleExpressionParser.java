/**
 * Parser following the grammar :
 * E := E+M | M
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
        Expression expression = parseE(str);
        if (expression == null) {
            // If we couldn't parse the string, then raise an error
            throw new ExpressionParseException("Cannot parse expression: " + str);
        }

        // Flatten the expression before returning
        expression.flatten();
        return expression;
    }

    /**
     * E := E+M | M
     * Attempts to parse string into an expression tree
     * with a root node that is the first instance of "+" which yields two valid subexpressions.
     * If unable to, calls parseM on str
     * @param str the string to parse into an expression tree
     * @return the Expression object representing the parsed but unflattened expression tree
     */
    private Expression parseE(String str) {
        int indexPlus = str.indexOf("+");
        while (indexPlus > -1) {

            // divides string based on location of "+"
            final String subString1 = str.substring(0, indexPlus);
            final String subString2 = str.substring(indexPlus + 1, str.length());

            // parses first subexpression
            final Expression subExpression1 = parseM(subString1);
            // if first subexpression was able to be parsed, continue
            if (subExpression1 != null) {
                // parses second subexpression
                final Expression subExpression2 = parseE(subString2);
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
        return parseM(str);
    }

    /**
     * M := M*M | X
     * Attempts to parse string into an expression tree
     * with a root node that is the first instance of "*" which yields two valid subexpressions.
     * If unable to, calls parseX on str
     * @param str the string to parse into an expression tree
     * @return the Expression object representing the parsed but unflattened expression tree
     */
    private Expression parseM(String str) {
        int indexTimes = str.indexOf("*");
        while (indexTimes > -1) {

            // divides string based on location of "*"
            final String subString1 = str.substring(0, indexTimes);
            final String subString2 = str.substring(indexTimes + 1, str.length());

            // parses first subexpression
            final Expression subExpression1 = parseX(subString1);
            // if first subexpression was able to be parsed, continue
            if (subExpression1 != null) {
                // parses second subexpression
                final Expression subExpression2 = parseM(subString2);
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
        return parseX(str);
    }

    /**
     * X := (E) | L
     * Attempts to parse string into an expression tree
     * with a root node that is the first occurrence of closed parentheses which yields a valid subexpressions.
     * If unable to, calls parseL on str
     * @param str the string to parse into an expression tree
     * @return the Expression object representing the parsed but unflattened expression tree
     */
    private Expression parseX(String str) {
        int indexLeftParen = str.indexOf("(");
        int indexRightParen = str.lastIndexOf(")");
        if (indexLeftParen == 0) {
            if (indexRightParen == str.length() - 1) {
                final String subString = str.substring(indexLeftParen + 1, indexRightParen);

                // parses subexpression within parentheses
                final Expression subExpression = parseE(subString);
                //if subexpression is able to be parsed, create new compound expression and add the subexpression to it
                if (subExpression != null) {
                    final CompoundExpression parenExpression = new ParentheticalExpression();
                    parenExpression.addSubexpression(subExpression);
                    return parenExpression;
                }
            }
        }

        // if there are no valid instances of parentheses check numbers and letters
        return parseL(str);
    }

    /**
     * L := [0-9]+ | [a-z]
     * Attempts to parse string into an expression tree
     * with a root node that is a number or letter
     * If unable to, returns null.
     * @param str the string to parse into an expression tree
     * @return the Expression object representing the parsed but unflattened expression tree
     */
    private Expression parseL(String str) {
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

