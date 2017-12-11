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

    public ParentheticalExpression(Node initialNode) {
        super("()", initialNode);
    }
}