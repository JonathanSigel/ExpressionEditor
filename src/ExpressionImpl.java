import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ExpressionImpl implements Expression {

    protected CompoundExpression mParent;
    protected String mRep;
    protected Node mNode;

    /**
     * Implementation of the Expression Interface. Superclass for all types of expressions.
     * @param representation a string representing the expression or its type
     */
    protected ExpressionImpl(String representation) {
        mParent = null;
        mRep = representation;
        mNode = new HBox();
    }

    /**
     * Returns the expression's parent.
     * @return the expression's parent
     */
    public CompoundExpression getParent() {
        return mParent;
    }

    /**
     * Sets the parent be the specified expression.
     * @param parent the CompoundExpression that should be the parent of the target object
     */
    public void setParent(CompoundExpression parent) {
        mParent = parent;
    }

    /**
     * Creates and returns a deep copy of the expression.
     * The entire tree rooted at the target node is copied, i.e.,
     * the copied Expression is as deep as possible.
     * Should be overridden for compound expressions so as to also deep copy children.
     * @return the deep copy
     */
    public Expression deepCopy() {
        return new ExpressionImpl(new String(mRep));
    }

    /**
     * Returns the JavaFX node associated with this expression.
     * Should be overridden for subclasses of compound expression so as to also include the nodes of the children.
     * @return the JavaFX node associated with this expression.
     */
    public Node getNode() {
        return mNode;
    }

    /**
     * Flattens the expression as much as possible
     * throughout the entire tree. This method modifies the expression itself.
     * Should be overridden for compound expressions so as to also recursively flatten children.
     */
    public void flatten () {
    }

    /**
     * Flattens the JavaFX nodes in the expression as much as possible
     * throughout the entire tree. This method modifies the nodes themselves.
     * Should be overridden for compound expressions so as to also recursively flatten children's nodes.
     */
    public void flattenNodes() {
    }

    /**
     * Creates a String representation by printing out the
     * tree represented by this expression, starting at the specified indentation level.
     * Should be overridden for compound expressions so as to also recursively convert children to string.
     * @param indentLevel the indentation level (number of tabs from the left margin) at which to start
     * @return a String representation of the expression tree.
     */
    public String convertToString (int indentLevel) {
        final StringBuffer sb = new StringBuffer();
        Expression.indent(sb, indentLevel);
        sb.append(mRep + "\n");
        return sb.toString();
    }

    /**
     * Returns a string representing the type of expression.
     * In the case of compound expression this will be a an operator or parentheses.
     * In the case of a literal it will be the number or letter.
     * @return the type or string representation
     */
    public String getType() {
        return mRep;
    }
}