import javafx.application.Application;
import java.util.*;

import javafx.geometry.Bounds;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

public class ExpressionEditor extends Application {

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

		/**
		 * Mouse handle event for manipulating expression.
		 * Changes focus on mouse release when there is no copy present.
		 * Creates a copy of focused subexpression on drag and drags it around to change location of focused expression in its parent expression.
		 * If a copy is present releases it on mouse release.
		 * @param event the mouse event
		 */
		public void handle (MouseEvent event) {

			//coordinates of mouse in scene
			final double x = event.getSceneX();
			final double y = event.getSceneY();

			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				//do nothing when mouse is pressed
				//everything happens on release or drag
				//because otherwise it is impossible to tell if the user intends to change focus
				//or create copy on mouse click
			} else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				//if there is an expression in focus drag its copy
				if (mFocusedExpression != null) {
					//if a copy does not exist, create it
					if (mCopyExpression == null) {
						copyFocusedExpression();
					}
					//drag copy to current location of mouse
					mCopyExpression.getNode().setTranslateX(mCopyExpression.getNode().getTranslateX() + (x - mLastX));
					mCopyExpression.getNode().setTranslateY(mCopyExpression.getNode().getTranslateY() + (y - mLastY));
					//swaps focused expression accordingly
					Bounds copyBounds = mCopyExpression.getNode().localToScene(mCopyExpression.getNode().getBoundsInLocal());
					mFocusedExpression.swap(copyBounds.getMinX());
				}
			} else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
				//if there is currently no copy, then change focus
				if (mCopyExpression == null) {
					if (mFocusedExpression == null) {
						mFocusedExpression = mRootExpression.focus(x, y);
					} else {
						((HBox) mFocusedExpression.getNode()).setBorder(Expression.NO_BORDER);
						mFocusedExpression = mFocusedExpression.focus(x, y);
					}
				} else {
					//if there is a copy, set it down (aka set it to null and remove from pane)
					mFocusedExpression.setColor(Color.BLACK);
					mPane.getChildren().remove(mCopyExpression.getNode());
					mCopyExpression = null;
				}
			}

			mLastX = x;
			mLastY = y;
		}

		/**
		 * Copies the focused expression and puts the copy in the same location as the original
		 */
		private void copyFocusedExpression() {
			mCopyExpression = mFocusedExpression.deepCopy();
			//ghosts the focused expression
			mFocusedExpression.setColor(Expression.GHOST_COLOR);
			mPane.getChildren().add(mCopyExpression.getNode());

			Bounds originalBounds = mFocusedExpression.getNode().localToScene(mFocusedExpression.getNode().getBoundsInLocal());
			Bounds copyBounds = mCopyExpression.getNode().localToScene(mCopyExpression.getNode().getBoundsInLocal());

			mCopyExpression.getNode().setLayoutX(originalBounds.getMinX() - copyBounds.getMinX());
			mCopyExpression.getNode().setLayoutY(originalBounds.getMinY()- copyBounds.getMinY());
		}
	}

	/**
	 * Size of the GUI
	 */
	private static final int WINDOW_WIDTH = 500, WINDOW_HEIGHT = 250;

	/**
	 * Initial expression shown in the textbox
	 */
	private static final String EXAMPLE_EXPRESSION = "2*x+3*y+4*z+(7+6*z)";

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
					System.out.println(expression.convertToString(0));
					expressionPane.getChildren().clear();
					expressionPane.getChildren().add(expression.getNode());
					expression.setFont(Font.font("Times",FontPosture.REGULAR, 36));
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
