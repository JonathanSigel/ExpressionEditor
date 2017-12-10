import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
public class WhatAWaste extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	private static class MouseEventHandler implements
			EventHandler<MouseEvent> {
		private HBox _box;
		/* IMPLEMENT ME */
		double _lastX, _lastY;

		MouseEventHandler(HBox box) {
			_box = box;
		}

		public void handle(MouseEvent event) {
			final double sceneX = event.getSceneX();
			final double sceneY = event.getSceneY();
			if (event.getEventType() ==
					MouseEvent.MOUSE_PRESSED) {
// IMPLEMENT ME
			} else if (event.getEventType() ==
					MouseEvent.MOUSE_DRAGGED) {
				_box.setTranslateX(_box.getTranslateX() + (sceneX - _lastX));
				_box.setTranslateY(_box.getTranslateY() + (sceneY - _lastY));
				_box.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: blue;");
			} else if (event.getEventType() ==
					MouseEvent.MOUSE_RELEASED) {
				_box.setLayoutX(_box.getLayoutX() +
						_box.getTranslateX());
				_box.setLayoutY(_box.getLayoutY() +
						_box.getTranslateY());
				_box.setTranslateX(0);
				_box.setTranslateY(0);
				_box.setStyle("-fx-border-style: none;");
			}
			_lastX = sceneX;
			_lastY = sceneY;
		}
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("EventDrivenProgram");
		final Pane root = new Pane();
		final HBox hbox = new HBox();
		final HBox hbox2 = new HBox();
		final Label label = new Label("Drag me!");
		final Label label2 = new Label("Drag me too!");
		final Label label3 = new Label("Drag me three!");
		final Label label4 = new Label("STAY AWAY FROM ME!");
		final Label label5 = new Label("Meh.");
		final Label label6 = new Label("I'm a rebel!");
		root.getChildren().add(hbox);
		hbox.getChildren().add(label);
		hbox.getChildren().add(label2);
		hbox.getChildren().add(hbox2);
		hbox2.getChildren().add(label3);
		hbox2.getChildren().add(label4);
		hbox2.getChildren().add(label5);
		final MouseEventHandler handler = new
				MouseEventHandler(hbox);
		hbox.setOnMousePressed(handler);
		hbox.setOnMouseDragged(handler);
		hbox.setOnMouseReleased(handler);
		primaryStage.setScene(new Scene(root, 600, 480));
		primaryStage.show();
	}
}