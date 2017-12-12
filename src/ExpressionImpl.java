import javafx.collections.FXCollections;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import javafx.scene.paint.Color;

public class ExpressionImpl implements Expression {

    protected CompoundExpression mParent;
    protected String mRep;
    // is an Pane instead of a Node because it is essential to be able to access its children,
    // and Pane is the highest superclass with that functionality
    protected Pane mNode;
    protected Color mTextColor;

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
     * Implementation of the Expression Interface in the case of having a non-null JavaFX node.
     * Superclass for all types of expressions.
     * @param representation a string representing the expression or its type
     * @param nodeRepresentation a Pane that is the JavaFX representation of the expression
     */
    protected ExpressionImpl(String representation, Pane nodeRepresentation){
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
     * Creates and returns a deep copy of the expression and its JavaFX node if it has one
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

    /**
     * Creates and returns a deep copy of the JavaFX node representing the expression
     * Should be overridden for compound expressions so as to also deep copy the children of the node
     * @return a Pane which is the deep copy of the expression's JavaFX node
     */
    protected Pane copyNode() {
        Pane copy = new HBox();
        Labeled oldLabel = (Labeled) mNode.getChildren().get(0);
        Labeled toAdd = new Label((oldLabel).getText());
        copy.getChildren().add(toAdd);
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

    /**
     * Sets the JavaFX node associated with this expression.
     * @param newNode the Pane to set the JavaFX node to
     */
    protected void setNode(Pane newNode) {
        mNode = newNode;
    }

    /**
     * Flattens the expression as much as possible, including the JavaFX nodes
     * throughout the entire tree. This method modifies the expression itself.
     * Should be overridden for compound expressions so as to also recursively flatten children.
     */
    public void flatten () {
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
    protected String getType() {
        return mRep;
    }

    /**
     * Switches placement in parent expression with the sibling whose JavaFX node is at the given x coordinate.
     * Affects both organization of expression tree and JavaFX nodes
     * @param x the x coordinate
     */
    public void swap(double x) {
        if (mParent != null && mNode != null) {
            final Pane p = (Pane) mNode.getParent();
            //makes a copy of the node's parent's children
            List<Node> currentCase = FXCollections.observableArrayList(p.getChildren());

            final int currentIndex = currentCase.indexOf(mNode);
            //+- 2 so as to skip over operation labels
            final int leftIndex = currentIndex - 2;
            final int rightIndex = currentIndex + 2;

            //finding index in expression's parent's children
            final int expressionIndex = (int) currentIndex / 2;
            final int leftExpressionIndex = expressionIndex - 1;
            final int rightExpressionIndex = expressionIndex + 1;

            //determining coordinates in scene
            Bounds currentBoundsInScene = mNode.localToScene(mNode.getBoundsInLocal());
            final double currentX = currentBoundsInScene.getMinX();
            //if there is no sibling to the left, then the farthest leftwards x coordinate would be that of this expression's JavaFX node
            double leftX = currentX;
            double leftWidth = 0;
            double operatorWidth = 0;

            //determining width of labels representing operations
            if (currentCase.size() > 0) {
                if (currentIndex == 0) {
                    operatorWidth = ((Region)currentCase.get(1)).getWidth();
                } else {
                    operatorWidth = ((Region)currentCase.get(currentCase.size() - 2)).getWidth();
                }
            }
            //checking if this expression and its JavaFX node should be swapped with its sibling to the left
            //first make sure there is a sibling to the left
            if (leftIndex >= 0) {
                Bounds leftBoundsInScene = p.getChildren().get(leftIndex).localToScene(p.getChildren().get(leftIndex).getBoundsInLocal());
                //if the node of this expression was to be in the left position,
                // then its x coordinate would be that of the leftwards sibling
                leftX = leftBoundsInScene.getMinX();
                leftWidth = leftBoundsInScene.getWidth();

                if (Math.abs(x - leftX) < Math.abs(x - currentX)) {
                    Collections.swap(currentCase, currentIndex, leftIndex);
                    p.getChildren().setAll(currentCase);
                    //also swaps the expression itself, not just its JavaFX node
                    swapSubexpressions(expressionIndex, leftExpressionIndex);
                    return;
                }
            }
            //checking if this expression and its JavaFX node should be swapped with its sibling to the right
            //first make sure there is a sibling to the right
            if (rightIndex < currentCase.size()) {
                Bounds rightBoundsInScene = p.getChildren().get(rightIndex).localToScene(p.getChildren().get(rightIndex).getBoundsInLocal());
                //if the node of this expression was to be in the right position,
                // then its x coordinate would be the coordinate of the left sibling, the width of the left sibling, the width of this expression's node, and the width of any labels in place for operator symbols
                final double rightX = leftX + leftWidth + operatorWidth + rightBoundsInScene.getWidth() + operatorWidth;

                if (Math.abs(x - rightX) < Math.abs(x - currentX)) {
                    Collections.swap(currentCase, currentIndex, rightIndex);
                    p.getChildren().setAll(currentCase);
                    //also swaps the expression itself, not just its JavaFX node
                    swapSubexpressions(expressionIndex, rightExpressionIndex);
                    return;
                }
            }
        }
    }

    /**
     * Switches placement in parent expression with the sibling at the given index of swapIndex
     * @param currentIndex index of this expression in its parent's list of children
     * @param swapIndex index of the sibling to switch with in the parent's list of children
     */
    private void swapSubexpressions(int currentIndex, int swapIndex) {
        Collections.swap(mParent.getSubexpressions(), currentIndex, swapIndex);
    }

    /**
     * Focuses on the subexpression at scene coordinates (x,y)
     * If no subexpression is clicked, lor no subexpression exists, returns null
     * @param x scene x coordinate
     * @param y scene y coordinate
     * @return the new focused Expression
     */
    public Expression focus(double x, double y) {
        return null;
    }

    /**
     * Changes color of the text in the expression's JavaFX node to given color
     * Should be overridden for compound expressions in order to also change the color of all children's JavaFX nodes
     * @param c the given color
     */
    public void setColor(Color c) {
        ((Labeled)mNode.getChildren().get(0)).setTextFill(c);
    }
}