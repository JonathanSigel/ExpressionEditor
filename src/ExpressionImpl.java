public class ExpressionImpl implements Expression {

    private CompoundExpression mParent;
    private String mRep;

    //!!! Jonathan, do you want this to take a parent? It is a very easy fix, just ask
    //!!! also, note that subclasses do NOT take a string unless specified
    protected ExpressionImpl(String representation) {
        mParent = null;
        mRep = representation;
    }

    public CompoundExpression getParent() {
        return mParent;
    }

    public void setParent(CompoundExpression parent) {
        mParent = parent;
    }

    //!!! need to know how to copy
    ///!!! this should only be overridden by those which extend CompoundExpressionImpl
    public Expression deepCopy() {
        return null;
    }

    ///!!! this should only be overridden by those which extend CompoundExpressionImpl
    public void flatten () {

    }

    //!!! should be overridden
    public String convertToString (int indentLevel) {
        return null;
    }
}
