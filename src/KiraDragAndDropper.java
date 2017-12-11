import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.List;

public class KiraDragAndDropper extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private static class MouseEventHandler implements EventHandler<MouseEvent> {
        private Node mNode;
        double mLastX, mLastY;

        MouseEventHandler(Node node) {
            mNode = node;
        }

        public void handle(MouseEvent event) {
            final double sceneX = event.getSceneX();
            final double sceneY = event.getSceneY();

            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                swapChildren();

            } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                mNode.setTranslateX(mNode.getTranslateX() + (sceneX - mLastX));
                mNode.setTranslateY(mNode.getTranslateY() + (sceneY - mLastY));
                swapChildren();

            } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                mNode.setLayoutX(mNode.getLayoutX() + mNode.getTranslateX());
                mNode.setLayoutY(mNode.getLayoutY() + mNode.getTranslateY());
                mNode.setTranslateX(0);
                mNode.setTranslateY(0);
            }

            mLastX = sceneX;
            mLastY = sceneY;
        }

        private void swapChildren() {
            final HBox p = (HBox) mNode.getParent();
            List<Node> currentCase = FXCollections.observableArrayList(p.getChildren());

            final int currentIndex = currentCase.indexOf(mNode);
            // 2 so as to skip over operation labels
            final int leftIndex = currentIndex - 2;
            final int rightIndex = currentIndex + 2;

            final double currentX = mNode.getLayoutX();
            double leftWidth = 0;
            double leftX = currentX;
            double operatorWidth = 0;

            //may not work with "()"
            if (currentCase.size() > 0) {
                if (currentIndex == 0) {
                    operatorWidth = ((Region)currentCase.get(1)).getWidth();
                } else {
                    operatorWidth = ((Region)currentCase.get(currentCase.size() - 2)).getWidth();
                }
            }

            List<Node> leftCase = FXCollections.observableArrayList(p.getChildren());
            if (leftIndex >= 0) {
                Collections.swap(leftCase, currentIndex, leftIndex);
                p.getChildren().setAll(leftCase);

                leftX = currentCase.get(leftIndex).getLayoutX();
                if (Math.abs(mLastX - leftX) < Math.abs(mLastX - currentX)) {
                    return;
                }
                leftWidth = ((Region)currentCase.get(leftIndex)).getWidth();
            }

            List<Node> rightCase = FXCollections.observableArrayList(p.getChildren());
            if (rightIndex < rightCase.size()) {
                Collections.swap(rightCase, currentIndex, rightIndex);
                p.getChildren().setAll(rightCase);
                final double rightWidth = ((Region)currentCase.get(rightIndex)).getWidth();

                final double rightX = leftX + leftWidth + operatorWidth + rightWidth + operatorWidth;
                if (Math.abs(mLastX - rightX) < Math.abs(mLastX - currentX)) {

                    return;
                }
            }

            p.getChildren().setAll(currentCase);
        }

    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("EventDrivenProgram");
        final Pane root = new Pane();
        final HBox container = new HBox();
        final Label e1 = new Label("e1");
        final Label e2 = new Label("e2");
        final Label e0 = new Label("e0");
        container.getChildren().add(e0);
        container.getChildren().add(new Label("+"));
        container.getChildren().add(e1);
        container.getChildren().add(new Label("+"));
        container.getChildren().add(e2);
        root.getChildren().add(container);
        final MouseEventHandler handler = new MouseEventHandler(e1);
        e1.setOnMousePressed(handler);
        e1.setOnMouseDragged(handler);
        e1.setOnMouseReleased(handler);
        primaryStage.setScene(new Scene(root, 600, 480));
        primaryStage.show();
    }
}
