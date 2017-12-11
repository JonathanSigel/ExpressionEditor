import javafx.collections.FXCollections;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;

public class CompoundExpressionImpl extends ExpressionImpl implements CompoundExpression {

    protected List<Expression> mChildren;

    /**
     * Implementation of the CompoundExpression Interface. Superclass for all types of expressions with children.
     *
     * @param representation a string representing the type of expression (as in "()", "+", or "·")
     */
    protected CompoundExpressionImpl(String representation) {
        super(representation);
        mChildren = new ArrayList<Expression>();
    }

    protected CompoundExpressionImpl(String representation, Node nodeRepresentation) {
        super(representation, nodeRepresentation);
        mChildren = new ArrayList<Expression>();
    }

    /**
     * Adds the specified expression as a child and sets the child's parent to be this CompoundExpression.
     *
     * @param subexpression the child expression to add
     */
    public void addSubexpression(Expression subexpression) {
        mChildren.add(subexpression);
        // sets this CompoundExpression as the parent since Expressions only have one parent and so the child's parent must be this CompoundExpression
        subexpression.setParent(this);
    }

    /**
     * Returns the list of the CompoundExpression's children.
     *
     * @return list of children
     */
    public List<Expression> getSubexpressions() {
        return mChildren;
    }

    /**
     * Creates and returns a deep copy of the expression.
     * The entire tree rooted at the target node is copied, i.e.,
     * the copied Expression is as deep as possible.
     *
     * @return the deep copy
     */
    @Override
    public Expression deepCopy() {
        CompoundExpressionImpl copy = new CompoundExpressionImpl(new String(mRep));

        if (mNode != null) {
            copy = new CompoundExpressionImpl(new String(mRep), copyNode());
        }

        for (Expression child : mChildren) {
            copy.addSubexpression(child.deepCopy());
        }

        return copy;
    }

    @Override
    public Node copyNode() {
        List<Node> copyChildren = FXCollections.observableArrayList(((HBox)mNode).getChildren());
        List<Node> newChildren = new ArrayList<Node>();

        int index = 0;
        for(Node child : copyChildren) {
            if (child instanceof Label) {
                newChildren.add(new Label(new String(((Label) child).getText())));
            } else {
                newChildren.add(((ExpressionImpl)mChildren.get(index)).copyNode());
                index++;
            }
        }

        HBox copyNode = new HBox();
        copyNode.getChildren().addAll(newChildren);

        return copyNode;
    }

    /**
     * Recursively flattens the expression as much as possible throughout the entire tree.
     * Should be overridden for operation expressions so that in every multiplicative
     * or additive expression x whose first or last
     * child c is of the same type as x, the children of c will be added to x, and
     * c itself will be removed. This method modifies the expression itself.
     */
    @Override
    public void flatten() {
        flattenChildren();
    }

    @Override
    public void flattenNodes() {
        flattenChildrenNodes();
    }



    /**
     * Creates a String representation by recursively printing out (using indentation) the
     * tree represented by this expression, starting at the specified indentation level.
     *
     * @param indentLevel the indentation level (number of tabs from the left margin) at which to start
     * @return a String representation of the expression tree.
     */
    @Override
    public String convertToString(int indentLevel) {
        final StringBuffer sb = new StringBuffer();
        Expression.indent(sb, indentLevel);
        sb.append(mRep + "\n");

        for (Expression child : mChildren) {
            sb.append(child.convertToString(indentLevel + 1));
        }

        return sb.toString();
    }

    /**
     * Helper method for applying the flatten method to all children of this CompoundExpression
     */
    protected void flattenChildren() {
        for (Expression child : mChildren) {
            child.flatten();
        }
    }

    protected void flattenChildrenNodes() {
        for (Expression child : mChildren) {
            ((ExpressionImpl) child).flattenNodes();
        }
    }

    @Override
    public Expression focus(double x, double y) {

        for(Expression child : mChildren) {

            Bounds boundsInScene = child.getNode().localToScene(child.getNode().getBoundsInLocal());

            final double xMin = boundsInScene.getMinX();
            final double xMax = boundsInScene.getMaxX();
            final double yMin = boundsInScene.getMinY();
            final double yMax = boundsInScene.getMaxY();

            if (((x <= xMax) && (x >= xMin)) && ((y <= yMax) && (y >= yMin))) {
                ((HBox)child.getNode()).setBorder(RED_BORDER);
                return child;
            }
        }
        return null;
    }
}
