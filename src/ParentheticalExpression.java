import javafx.scene.layout.Pane;

public class ParentheticalExpression extends CompoundExpressionImpl {

    /**
     * An expression whose root is parentheses
     */
    public ParentheticalExpression() {
        super("()");
    }

    /**
     * An expression whose root is parentheses and who has a JavaFX node
     */
    public ParentheticalExpression(Pane initialNode) {
        super("()", initialNode);
    }
}