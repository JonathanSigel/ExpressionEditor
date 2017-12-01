public class OperationExpression extends CompoundExpressionImpl {

    protected OperationExpression(String representation) {
        super(representation);
    }

    @Override
    public void flatten() {
        flattenChildren();
        flattenSelf();
    }

    //!!! to implement
    protected void flattenSelf() {

    }
}
