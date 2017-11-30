public class LiteralExpression extends ExpressionImpl {

    private String mRep;

    public LiteralExpression(String representation) {
        super(representation);
    }

    //!!! should implement
    @Override
    public String convertToString (int indentLevel) {
        return null;
    }
}
