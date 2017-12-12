import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

public class OperationExpression extends CompoundExpressionImpl {

    /**
     * An expression whose root is an operator (as in not a parentheses)
     * @param representation a string representing the type of expression (as in "+", or "·")
     */
    protected OperationExpression(String representation) {
        super(representation);
    }

    /**
     * An expression whose root is an operator (as in not a parentheses) and who has a JavaFX node
     * @param representation a string representing the type of expression (as in "+", or "·")
     * @param nodeRepresentation a Pane that is the JavaFX representation of the expression
     */
    protected OperationExpression(String representation, Pane nodeRepresentation) {
        super(representation, nodeRepresentation);
    }

    /**
     * Recursively flattens the expression as much as possible
     * throughout the entire tree, including the JavaFX nodes
     * Specifically, in every multiplicative
     * or additive expression x whose first or last
     * child c is of the same type as x, the children of c will be added to x, and
     * c itself will be removed. This method modifies the expression itself.
     */
    @Override
    public void flatten() {
        flattenChildren();
        flattenSelf();
    }

    /**
     * Helper method for applying the flatten method to the compound expression itself.
     * If any of the expression's children are the same type as itself,
     * the child's children will be added to the list of children of this expression and the child removed.
     * The same happens with the JavaFX node as well if the expression has one
     */
    private void flattenSelf() {
        final List<Expression> newChildren = new ArrayList<>();

        if (mNode != null) {
            mNode.getChildren().clear();
        }

        for (Expression existingChild: mChildren) {
            // first cast as an ExpressionImpl to use the getType method since it could be a literal
            if (((ExpressionImpl)existingChild).getType() == mRep) {
                //----stuff for expression
                // Since the child has the same type as this object we know the child is an OperationExpression
                final List<Expression> childrenToAdd = ((OperationExpression)existingChild).getSubexpressions();
                // update the new children to have this as their parent
                for (Expression child: childrenToAdd) {
                    child.setParent(this);
                }
                // add all the children to the newChildren list
                newChildren.addAll(childrenToAdd);

                //----stuff for nodes
                if (mNode != null) {
                    final List<Node> nodesToAdd = ((Pane) existingChild.getNode()).getChildren();
                    mNode.getChildren().addAll(nodesToAdd);
                    Labeled toAdd = new Label(mRep);
                    mNode.getChildren().add(toAdd);
                }
            }
            else{
                //----stuff for expression
                // if it's not the same type, keep the existing child and add to the new list to preserve order
                newChildren.add(existingChild);

                //----stuff for nodes
                if (mNode != null) {
                    mNode.getChildren().add(existingChild.getNode());
                    Labeled toAdd = new Label(mRep);
                    mNode.getChildren().add(toAdd);
                }
            }
        }
        // replace the children list with the new list
        mChildren = newChildren;

        if (mNode != null) {
            //removes last additional operation sign
            mNode.getChildren().remove( mNode.getChildren().size()-1, mNode.getChildren().size());
        }
    }
}