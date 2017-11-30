import java.util.ArrayList;
import java.util.List;

public class CompoundExpressionImpl extends ExpressionImpl implements CompoundExpression{

    private List<Expression> mChildren;

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
}