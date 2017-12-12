import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ParentheticalExpression extends CompoundExpressionImpl {

    /**
     * An expression whose root is parentheses
     */
    public ParentheticalExpression() {
        super("()");
    }

    public ParentheticalExpression(Pane initialNode) {
        super("()", initialNode);
    }
}