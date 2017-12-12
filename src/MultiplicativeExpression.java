import javafx.scene.layout.Pane;

public class MultiplicativeExpression extends OperationExpression {

    /**
     * An expression whose root is multiplication
     */
    public MultiplicativeExpression() {
        super("·");
    }

    /**
     * An expression whose root is multiplication and who has a JavaFX node
     */
    public MultiplicativeExpression(Pane initialNode) {
        super("·", initialNode);
    }
}
