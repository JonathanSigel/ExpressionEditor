import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ParentheticalExpression extends CompoundExpressionImpl {

    /**
     * An expression whose root is parentheses
     */
    public ParentheticalExpression() {
        super("()");
    }

    /**
     * Returns the JavaFX node associated with this expression.
     * @return the JavaFX node associated with this expression.
     */
    @Override
    public Node getNode() {
        final HBox expressionBox = new HBox();
        final Label leftParen = new Label("(");
        final Label rightParen = new Label(")");
        expressionBox.getChildren().add(leftParen);
        // because a parenthetical expression will only have one child, get the first element in the list of children
        expressionBox.getChildren().add(mChildren.get(0).getNode());
        expressionBox.getChildren().add(rightParen);

        return expressionBox;
    }
}