import java.util.ArrayList;
import java.util.List;

public class CompoundExpressionImpl extends ExpressionImpl implements CompoundExpression{

    private List<Expression> mChildren;

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
    //!!! to implement
    public String convertToString (int indentLevel) {
        return null;
    }
}