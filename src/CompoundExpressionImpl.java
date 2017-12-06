import java.util.ArrayList;
import java.util.List;

public class CompoundExpressionImpl extends ExpressionImpl implements CompoundExpression{

    protected List<Expression> mChildren;

    //!!! Dad read
    /**
     * Implementation of the CompoundExpression Interface. Superclass for all types of expressions with children.
     * @param representation a string representing the type of expression (as in "()", "+", or "·")
     */
    protected CompoundExpressionImpl(String representation) {
        super(representation);
        mChildren = new ArrayList<Expression>();
    }

    //!!! Dad should this actually set the child's parent since it was not specified in the interface
    /**
     * Adds the specified expression as a child and sets the child's parent to be this CompoundExpression.
     * @param subexpression the child expression to add
     */
    public void addSubexpression(Expression subexpression) {
        mChildren.add(subexpression);
        subexpression.setParent(this);
    }

    /**
     * Returns the list of the CompoundExpression's children.
     * @return list of children
     */
    public List<Expression> getSubexpressions() {
        return mChildren;
    }

    /**
     * Creates and returns a deep copy of the expression.
     * The entire tree rooted at the target node is copied, i.e.,
     * the copied Expression is as deep as possible.
     * @return the deep copy
     */
    @Override
    public Expression deepCopy() {
        CompoundExpressionImpl copy = new CompoundExpressionImpl(new String(mRep));
        for (int i = 0; i < mChildren.size(); i++) {
            copy.addSubexpression(mChildren.get(i).deepCopy());
        }

        return copy;
    }

    //!!! Dad read
    /**
     * Recursively flattens the expression as much as possible
     * throughout the entire tree. Specifically, in every multiplicative
     * or additive expression x whose first or last
     * child c is of the same type as x, the children of c will be added to x, and
     * c itself will be removed. This method modifies the expression itself.
     */
    @Override
    public void flatten() {
        flattenChildren();
    }

    /**
     * Creates a String representation by recursively printing out (using indentation) the
     *tree represented by this expression, starting at the specified indentation level.
     * @param indentLevel the indentation level (number of tabs from the left margin) at which to start
     * @return a String representation of the expression tree.
     */
    @Override
    public String convertToString (int indentLevel) {
        StringBuffer sb = new StringBuffer();
        indent(sb, indentLevel);
        sb.append(mRep + "\n");
        for (int i = 0; i < mChildren.size(); i++) {
            sb.append(mChildren.get(i).convertToString(indentLevel + 1));
        }
        return sb.toString();
    }

    /**
     * Static helper method to indent a specified number of times from the left margin, by
     * appending tab characters to teh specified StringBuffer.
     * @param sb the StringBuffer to which to append tab characters.
     * @param indentLevel the number of tabs to append.
     */
    public static void indent (StringBuffer sb, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            sb.append('\t');
        }
    }

    //!!! Dad, should this have a list of expressions as a parameter and just flattened all of them?
    /**
     * Helper method for applying the flatten method to all children of a CompoundExpression
     */
    protected void flattenChildren() {
        for (int i = 0; i < mChildren.size(); i++) {
            mChildren.get(i).flatten();
        }
    }
}