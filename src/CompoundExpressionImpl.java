import java.util.ArrayList;
import java.util.List;

public class CompoundExpressionImpl extends ExpressionImpl implements CompoundExpression{

    protected List<Expression> mChildren;

    //!!! NO subclasses take a string as a parameter
    protected CompoundExpressionImpl(String representation) {
        super(representation);
        mChildren = new ArrayList<Expression>();
    }

    public void addSubexpression(Expression subexpression) {
        mChildren.add(subexpression);
    }

    @Override
    //!!! to implement
    public Expression deepCopy() {
        return null;
    }

    @Override
    //!!! to implement
    public void flatten () {
    }

    @Override
    public String convertToString (int indentLevel) {
        StringBuffer sb = new StringBuffer();
        indent(sb, indentLevel);
        sb.append(mRep);
        for (int i = 0; i < mChildren.size(); i++) {
            sb.append("/n" + mChildren.get(i).convertToString(indentLevel + 1));
        }
        return sb.toString();
    }

    public static void indent (StringBuffer sb, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            sb.append('\t');
        }
    }
}