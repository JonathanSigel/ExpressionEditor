import javafx.collections.FXCollections;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        mNode = null;
    }

    /**
     * Implementation of the Expression Interface. Superclass for all types of expressions.
     * @param representation a string representing the expression or its type
     * @param nodeRepresentation a node that is the JavaFX representation of the expression
     */
    protected ExpressionImpl(String representation, Node nodeRepresentation){
        mParent = null;
        mRep = representation;
        mNode = nodeRepresentation;
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

        if(mNode == null) {
            return new ExpressionImpl(new String(mRep));
        }

        return new ExpressionImpl(new String(mRep), copyNode());
    }

    protected Node copyNode() {
        HBox copy = new HBox();
        Node oldLabel = ((HBox) mNode).getChildren().get(0);
        copy.getChildren().add(new Label(((Label)oldLabel).getText()));

        return copy;
    }

    /**
     * Returns the JavaFX node associated with this expression.
     * Should be overridden for subclasses of compound expression so as to also include the nodes of the children.
     * @return the JavaFX node associated with this expression.
     */
    public Node getNode() {
        return mNode;
    }

    public void setNode(Node newNode) {
        mNode = newNode;
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

    public void swap(double x) {
        if (mParent != null && mNode != null) {
            final HBox p = (HBox) mNode.getParent();
            List<Node> currentCase = FXCollections.observableArrayList(p.getChildren());

            final int currentIndex = currentCase.indexOf(mNode);
            // 2 so as to skip over operation labels
            final int leftIndex = currentIndex - 2;
            final int rightIndex = currentIndex + 2;

            final int expressionIndex = (int) currentIndex / 2;
            final int leftExpressionIndex = expressionIndex - 1;
            final int rightExpressionIndex = expressionIndex + 1;

            Bounds currentBoundsInScene = mNode.localToScene(mNode.getBoundsInLocal());
            final double currentX = currentBoundsInScene.getMinX();
            double leftX = currentX;
            double leftWidth = 0;
            double operatorWidth = 0;

            if (currentCase.size() > 0) {
                if (currentIndex == 0) {
                    operatorWidth = ((Region)currentCase.get(1)).getWidth();
                } else {
                    operatorWidth = ((Region)currentCase.get(currentCase.size() - 2)).getWidth();
                }
            }

            List<Node> leftCase = FXCollections.observableArrayList(p.getChildren());
            if (leftIndex >= 0) {
                Collections.swap(leftCase, currentIndex, leftIndex);

                Bounds leftBoundsInScene = ((HBox)p).getChildren().get(leftIndex).localToScene(((HBox)p).getChildren().get(leftIndex).getBoundsInLocal());
                leftX = leftBoundsInScene.getMinX();
                leftWidth = leftBoundsInScene.getWidth();

                if (Math.abs(x - leftX) < Math.abs(x - currentX)) {
                    p.getChildren().setAll(leftCase);
                    swapSubexpressions(expressionIndex, leftExpressionIndex);
                    return;
                }
            }

            List<Node> rightCase = FXCollections.observableArrayList(p.getChildren());
            if (rightIndex < rightCase.size()) {
                Collections.swap(rightCase, currentIndex, rightIndex);

                Bounds rightBoundsInScene = ((HBox)p).getChildren().get(rightIndex).localToScene(((HBox)p).getChildren().get(rightIndex).getBoundsInLocal());
                
                final double rightX = leftX + leftWidth + operatorWidth + rightBoundsInScene.getWidth() + operatorWidth;

                if (Math.abs(x - rightX) < Math.abs(x - currentX)) {
                    p.getChildren().setAll(rightCase);
                    swapSubexpressions(expressionIndex, rightExpressionIndex);
                    return;
                }
            }
        }
    }

    private void swapSubexpressions(int currentIndex, int swapIndex) {
        Collections.swap(((CompoundExpressionImpl) mParent).getSubexpressions(), currentIndex, swapIndex);
    }

    public Expression focus(double x, double y) {
        return null;
    }
}