/**
 * Starter code to implement an ExpressionParser. Your parser methods should use the following grammar:
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

    private Expression parseExpression (String str) {
        return parseAddition(str);
    }

    private Expression parseAddition(String str) {
        int indexPlus = str.indexOf("+");
        while (indexPlus > -1) {
            final String subString1 = str.substring(0, indexPlus);
            final String subString2 = str.substring(indexPlus + 1, str.length());
            final Expression subExpression1 = parseMultiplication(subString1);

            if (subExpression1 != null) {
                final Expression subExpression2 = parseAddition(subString2);
                if (subExpression2 != null) {
                    final CompoundExpression addExpression = new AdditiveExpression();
                    addExpression.addSubexpression(subExpression1);
                    addExpression.addSubexpression(subExpression2);
                    return addExpression;
                }
            }

            indexPlus = str.indexOf("+", (indexPlus + 1));
        }

        return parseMultiplication(str);
    }

    private Expression parseMultiplication(String str) {
        int indexTimes = str.indexOf("*");
        while (indexTimes > -1) {
            final String subString1 = str.substring(0, indexTimes);
            final String subString2 = str.substring(indexTimes + 1, str.length());
            final Expression subExpression1 = parseParentheses(subString1);

            if (subExpression1 != null) {
                final Expression subExpression2 = parseMultiplication(subString2);
                if (subExpression2 != null) {
                    final CompoundExpression multExpression = new MultiplicativeExpression();
                    multExpression.addSubexpression(subExpression1);
                    multExpression.addSubexpression(subExpression2);
                    return multExpression;
                }
            }
            indexTimes = str.indexOf("*", (indexTimes + 1));
        }

        return parseParentheses(str);
    }

    private Expression parseParentheses(String str) {
        int indexLeftParen = str.indexOf("(");
        int indexRightParen = str.lastIndexOf(")");
        if (indexLeftParen == 0) {
            if (indexRightParen == str.length() - 1) {
                final String subString = str.substring(indexLeftParen + 1, indexRightParen);
                final Expression subExpression = parseExpression(subString);

                if (subExpression != null) {
                    final CompoundExpression parenExpression = new ParentheticalExpression();
                    parenExpression.addSubexpression(subExpression);
                    return parenExpression;
                }
            }
        }

        return parseNumbersAndLetters(str);
    }

    private Expression parseNumbersAndLetters(String str) {
        // [0-9]+
        if (str.matches("[0-9]+")) {
            return new LiteralExpression(str);
        }

        // [a-z]
        if (str.matches("[a-z]")) {
            return new LiteralExpression(str);
        }

        return null;
    }
}

