import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class MultiplicativeExpression extends OperationExpression {

    /**
     * An expression whose root is multiplication
     */
    public MultiplicativeExpression() {
        super("·");
    }

    public MultiplicativeExpression(Node initialNode) {
        super("·", initialNode);
    }
}
