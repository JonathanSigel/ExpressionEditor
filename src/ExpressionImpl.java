public class ExpressionImpl implements Expression {

    protected CompoundExpression mParent;
    protected String mRep;

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

    ///!!! this should only be overridden by those which extend CompoundExpressionImpl
    public Expression deepCopy() {
        return new ExpressionImpl(new String(mRep));
    }

    ///!!! this should only be overridden by those which extend CompoundExpressionImpl
    public void flatten () {
    }

    public String convertToString (int indentLevel) {
        StringBuffer sb = new StringBuffer();
        indent(sb, indentLevel);
        sb.append(mRep);
        return sb.toString();
    }

    public static void indent (StringBuffer sb, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            sb.append('\t');
        }
    }
}
