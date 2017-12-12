import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class MultiplicativeExpression extends OperationExpression {

    /**
     * An expression whose root is multiplication
     */
    public MultiplicativeExpression() {
        super("·");
    }

    public MultiplicativeExpression(Pane initialNode) {
        super("·", initialNode);
    }
}
