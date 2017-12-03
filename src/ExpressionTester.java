public class ExpressionTester {
    public static void main(String arg[]) {
        Expression n1 = new LiteralExpression("9");
        Expression n2 = new LiteralExpression("8");
        Expression n3 = new LiteralExpression("4");
        Expression n4 = new LiteralExpression("5");
        Expression n5 = new LiteralExpression("3");
        Expression n6 = new LiteralExpression("2");

        CompoundExpression m1 = new MultiplicativeExpression();
        m1.addSubexpression(n1);
        m1.addSubexpression(n2);

        CompoundExpression a1 = new AdditiveExpression();
        a1.addSubexpression(n5);
        a1.addSubexpression(n6);

        CompoundExpression p1 = new ParentheticalExpression();
        p1.addSubexpression(a1);

        CompoundExpression m2 = new MultiplicativeExpression();
        m2.addSubexpression(n4);
        m2.addSubexpression(p1);

        CompoundExpression a2 = new AdditiveExpression();
        a2.addSubexpression(n3);
        a2.addSubexpression(m2);

        CompoundExpression a3 = new AdditiveExpression();
        a3.addSubexpression(m1);
        a3.addSubexpression(a2);

        a3.flatten();
        //System.out.println(a3.convertToString(0));

        Expression copy = a2.deepCopy();

        KiraParser parse1 = new KiraParser();

       try {
          // Expression parsed1 = parse1.parse("9*8+4+5*(3+2)", false);
           Expression parsed1 = parse1.parse("(7+(7*5))+(6*7*9+10+10+10)", false);
           System.out.println(parsed1.convertToString(0));
           int i = 0;
       }
       catch(ExpressionParseException e) {
           System.out.println((e.getMessage()));

       }


    }
}
