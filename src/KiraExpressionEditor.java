import javafx.application.Application;
import java.util.*;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class KiraExpressionEditor extends Application {
    public static void main (String[] args) {
        launch(args);
    }

    /**
     * Mouse event handler for the entire pane that constitutes the ExpressionEditor
     */
    private static class MouseEventHandler implements EventHandler<MouseEvent> {

        Pane mPane;
        CompoundExpression mRootExpression;
        Expression mFocusedExpression;
        Expression mCopyExpression;
        double mLastX;
        double mLastY;

        MouseEventHandler (Pane pane, CompoundExpression rootExpression) {
            mPane = pane;
            mRootExpression = rootExpression;
            mFocusedExpression = null;
            mCopyExpression = null;
        }

        public void handle (MouseEvent event) {

            final double x = event.getSceneX();
            final double y = event.getSceneY();

            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                if (mFocusedExpression != null) {
                    if (mCopyExpression == null) {
                        mCopyExpression = mFocusedExpression.deepCopy();
                        ((ExpressionImpl)mFocusedExpression).setColor(Expression.GHOST_COLOR);
                        mPane.getChildren().add(mCopyExpression.getNode());

                        Bounds originalBounds = mFocusedExpression.getNode().localToScene(mFocusedExpression.getNode().getBoundsInLocal());
                        Bounds copyBounds = mCopyExpression.getNode().localToScene(mCopyExpression.getNode().getBoundsInLocal());

                        mCopyExpression.getNode().setLayoutX(originalBounds.getMinX() - copyBounds.getMinX());
                        mCopyExpression.getNode().setLayoutY(originalBounds.getMinY()- copyBounds.getMinY());
                    }

                    mCopyExpression.getNode().setTranslateX(mCopyExpression.getNode().getTranslateX() + (x - mLastX));
                    mCopyExpression.getNode().setTranslateY(mCopyExpression.getNode().getTranslateY() + (y - mLastY));
                    ((ExpressionImpl) mFocusedExpression).swap(x);
                }
            } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {

                if (mCopyExpression == null) {
                    if (mFocusedExpression == null) {
                        mFocusedExpression = ((ExpressionImpl)mRootExpression).focus(x, y);
                    } else {
                        ((HBox) mFocusedExpression.getNode()).setBorder(Expression.NO_BORDER);
                        mFocusedExpression = ((ExpressionImpl) mFocusedExpression).focus(x, y);
                    }
                } else {
                    ((ExpressionImpl)mFocusedExpression).setColor(Color.BLACK);
                    mPane.getChildren().remove(mCopyExpression.getNode());
                    mCopyExpression = null;
                    System.out.println(mRootExpression.convertToString(0));
                }
            }

            mLastX = x;
            mLastY = y;
        }
    }

    /**
     * Size of the GUI
     */
    private static final int WINDOW_WIDTH = 500, WINDOW_HEIGHT = 250;

    /**
     * Initial expression shown in the textbox
     */
    private static final String EXAMPLE_EXPRESSION = "2+6*6+(9*7+8)";

    /**
     * Parser used for parsing expressions.
     */
    private final ExpressionParser expressionParser = new SimpleExpressionParser();

    @Override
    public void start (Stage primaryStage) {
        primaryStage.setTitle("Expression Editor");

        // Add the textbox and Parser button
        final Pane queryPane = new HBox();
        final TextField textField = new TextField(EXAMPLE_EXPRESSION);
        final Button button = new Button("Parse");
        queryPane.getChildren().add(textField);

        final Pane expressionPane = new Pane();

        // Add the callback to handle when the Parse button is pressed
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle (MouseEvent e) {
                // Try to parse the expression
                try {
                    // Success! Add the expression's Node to the expressionPane
                    final Expression expression = expressionParser.parse(textField.getText(), true);
                    //expression.setExpressionFont("Comic Sans MS", 40.0);
                    System.out.println(expression.convertToString(0));
                    expressionPane.getChildren().clear();
                    expressionPane.getChildren().add(expression.getNode());
                    expression.getNode().setLayoutX(WINDOW_WIDTH/4);
                    expression.getNode().setLayoutY(WINDOW_HEIGHT/2);

                    // If the parsed expression is a CompoundExpression, then register some callbacks
                    if (expression instanceof CompoundExpression) {
                        ((Pane) expression.getNode()).setBorder(Expression.NO_BORDER);
                        final MouseEventHandler eventHandler = new MouseEventHandler(expressionPane, (CompoundExpression) expression);
                        expressionPane.setOnMousePressed(eventHandler);
                        expressionPane.setOnMouseDragged(eventHandler);
                        expressionPane.setOnMouseReleased(eventHandler);
                    }
                } catch (ExpressionParseException epe) {
                    // If we can't parse the expression, then mark it in red
                    textField.setStyle("-fx-text-fill: red");
                }
            }
        });
        queryPane.getChildren().add(button);

        // Reset the color to black whenever the user presses a key
        textField.setOnKeyPressed(e -> textField.setStyle("-fx-text-fill: black"));

        final BorderPane root = new BorderPane();
        root.setTop(queryPane);
        root.setCenter(expressionPane);

        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.show();
    }
}
