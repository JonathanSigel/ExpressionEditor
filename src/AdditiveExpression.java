import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class AdditiveExpression extends OperationExpression {

    /**
     * An expression whose root is addition
     */
    public AdditiveExpression() {
        super("+");
    }

    public AdditiveExpression(Node initialNode) {
        super("+", initialNode);
    }
}