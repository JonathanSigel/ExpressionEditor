import javafx.scene.control.Label;

public class LiteralExpression extends ExpressionImpl {

    /**
     * An expression which is a number or a letter
     * @param representation the number or letter
     */
    public LiteralExpression(String representation) {
        super(representation);
    }
}
