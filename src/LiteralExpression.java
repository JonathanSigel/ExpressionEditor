import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class LiteralExpression extends ExpressionImpl {

    /**
     * An expression which is a number or a letter
     * @param representation the number or letter
     */
    public LiteralExpression(String representation) {
        super(representation);
    }

    public LiteralExpression(String representation, Node nodeRepresentation) {
        super(representation, nodeRepresentation);
    }
}
