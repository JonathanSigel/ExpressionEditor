public class ExpressionImpl implements Expression {

    private CompoundExpression mParent;

    //!!! Jonathan, do you want this to take a parent? It is a very easy fix, just ask
    public ExpressionImpl() {
        mParent = null;
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
