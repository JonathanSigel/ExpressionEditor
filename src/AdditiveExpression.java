import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class AdditiveExpression extends OperationExpression {

    /**
     * An expression whose root is addition
     */
    public AdditiveExpression() {
        super("+");
    }

    public AdditiveExpression(Pane initialNode) {
        super("+", initialNode);
    }
}