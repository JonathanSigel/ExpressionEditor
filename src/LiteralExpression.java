import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class LiteralExpression extends ExpressionImpl {

    /**
     * An expression which is a number or a letter
     * @param representation the number or letter
     */
    public LiteralExpression(String representation) {
        super(representation);
    }

    public LiteralExpression(String representation, Pane nodeRepresentation) {
        super(representation, nodeRepresentation);
    }
}
