import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
public class WhatAWaste extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	private static class MouseEventHandler implements
			EventHandler<MouseEvent> {
		/* IMPLEMENT ME */
		double _lastX, _lastY;
		private Node _node;

		MouseEventHandler(Node node) {
			_node = node;
		}

		public void handle(MouseEvent event) {
			final double sceneX = event.getSceneX();
			final double sceneY = event.getSceneY();
			if (event.getEventType() ==
					MouseEvent.MOUSE_PRESSED) {
// IMPLEMENT ME
			} else if (event.getEventType() ==
					MouseEvent.MOUSE_DRAGGED) {
				_node.setTranslateX(_node.getTranslateX() + (sceneX - _lastX));
				_node.setTranslateY(_node.getTranslateY() + (sceneY - _lastY));
				_node.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: blue;");
			} else if (event.getEventType() ==
					MouseEvent.MOUSE_RELEASED) {
				_node.setLayoutX(_node.getLayoutX() +
						_node.getTranslateX());
				_node.setLayoutY(_node.getLayoutY() +
						_node.getTranslateY());
				_node.setTranslateX(0);
				_node.setTranslateY(0);
				_node.setStyle("-fx-border-style: none;");
			}
			_lastX = sceneX;
			_lastY = sceneY;
		}
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("EventDrivenProgram");
		final Pane root = new Pane();
		final Pane minipane1 = new Pane();
		minipane1.setPrefSize(400,400);
		final Pane minipane12 = new Pane();
		minipane1.setPrefSize(200,200);
		final HBox hbox = new HBox();
		final HBox hbox2 = new HBox();
		final Label label = new Label("Drag me!");
		final Label label2 = new Label("Drag me too!");
		final Label label3 = new Label("Drag me three!");
		final Label label4 = new Label("STAY AWAY FROM ME!");
		final Label label5 = new Label("Meh.");
		final Label label6 = new Label("I'm a rebel!");
		root.getChildren().add(minipane1);
		minipane1.getChildren().add(label);
		minipane1.getChildren().add(label2);

		//handlers
		final MouseEventHandler handler = new
				MouseEventHandler(label);
		label.setOnMousePressed(handler);
		label.setOnMouseDragged(handler);
		label.setOnMouseReleased(handler);
		final MouseEventHandler handler2 = new
				MouseEventHandler(label2);
		label2.setOnMousePressed(handler2);
		label2.setOnMouseDragged(handler2);
		label2.setOnMouseReleased(handler2);
		final MouseEventHandler handler3 = new
				MouseEventHandler(minipane1);
		minipane1.setOnMousePressed(handler3);
		minipane1.setOnMouseDragged(handler3);
		minipane1.setOnMouseReleased(handler3);

		primaryStage.setScene(new Scene(root, 600, 480));
		primaryStage.show();
	}
}