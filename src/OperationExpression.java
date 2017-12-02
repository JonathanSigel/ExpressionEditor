import java.util.ArrayList;
import java.util.List;

public class OperationExpression extends CompoundExpressionImpl {

    protected OperationExpression(String representation) {
        super(representation);
    }

    @Override
    public void flatten() {
        flattenChildren();
        flattenSelf();
    }

    protected void flattenSelf() {
        List<Expression> newChildren = new ArrayList<>();
        for (Expression existingChild: mChildren) {
            // first cast as an ExpressionImpl since it could be a literal
            if (((ExpressionImpl)existingChild).getType() == mRep) {
                // Since the child has the same type as this object
                // If we get here we know the child is an OperationExpression
                List<Expression> childrenToAdd = ((OperationExpression)existingChild).getSubexpressions();
                // update the new children to have this as their parent
                for (Expression child: childrenToAdd) {
                    child.setParent(this);
                }
                // add all the children to the newChildren list
                newChildren.addAll(childrenToAdd);
            }
            else{
                // if it's not the same type, keep the existing child
                // by building a new list we can preserve the order of children
                // we inherit from the children we replace
                newChildren.add(existingChild);
            }
        }
        // replace the children list with the new list
        mChildren = newChildren;
    }
}
