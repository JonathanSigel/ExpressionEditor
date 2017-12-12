import javafx.scene.layout.Pane;

public class AdditiveExpression extends OperationExpression {

    /**
     * An expression whose root is addition
     */
    public AdditiveExpression() {
        super("+");
    }

    /**
     * An expression whose root is addition and who has a JavaFX node
     */
    public AdditiveExpression(Pane initialNode) {
        super("+", initialNode);
    }
}