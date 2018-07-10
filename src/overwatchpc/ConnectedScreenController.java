/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overwatchpc;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import livescreen.SendLiveScreenThread;
import mouse.MouseControllerThread;

public class ConnectedScreenController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private Label networkip;

    @FXML
    private Label clientip;
    private int animX = 500;
    private int animY = 300;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mouse.MouseControllerThread ms = new MouseControllerThread();
        ms.start();
        livescreen.SendLiveScreenThread ls = new SendLiveScreenThread();
        ls.start();
        networkip.setText(connect.ConnectionDetails.serverIP);
        clientip.setText(connect.ConnectionDetails.androidIP);
//        Stage primaryStage = (Stage) networkip.getScene().getWindow();
//        primaryStage.setIconified(true);
        int numberOfSquares = 30;
        while (numberOfSquares > 0) {
            generateAnimation();
            numberOfSquares--;
        }
    }

    public void minimize(ActionEvent e) {
        Stage primaryStage = (Stage) networkip.getScene().getWindow();
        primaryStage.setIconified(true);
    }

    public void exit(ActionEvent e) {
        Platform.exit();
        System.exit(0);
    }

    public void generateAnimation() {
        Random rand = new Random();
        int sizeOfSqaure = rand.nextInt(40) + 1;
        int speedOfSqaure = rand.nextInt(10) + 5;
        int startXPoint = rand.nextInt(animY);
        int startYPoint = rand.nextInt(animX);
        int direction = rand.nextInt(5) + 1;

        KeyValue moveXAxis = null;
        KeyValue moveYAxis = null;
        Rectangle r1 = null;
//        r1.toBack();

        switch (direction) {
            case 1:
                // MOVE LEFT TO RIGHT
                r1 = new Rectangle(0, startYPoint, sizeOfSqaure, sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), animX - sizeOfSqaure);
                break;
            case 2:
                // MOVE TOP TO BOTTOM
                r1 = new Rectangle(startXPoint, 0, sizeOfSqaure, sizeOfSqaure);
                moveYAxis = new KeyValue(r1.yProperty(), animY - sizeOfSqaure);
                break;
            case 3:
                // MOVE LEFT TO RIGHT, TOP TO BOTTOM
                r1 = new Rectangle(startXPoint, 0, sizeOfSqaure, sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), animX - sizeOfSqaure);
                moveYAxis = new KeyValue(r1.yProperty(), animY - sizeOfSqaure);
                break;
            case 4:
                // MOVE BOTTOM TO TOP
                r1 = new Rectangle(startXPoint, animY - sizeOfSqaure, sizeOfSqaure, sizeOfSqaure);
                moveYAxis = new KeyValue(r1.xProperty(), 0);
                break;
            case 5:
                // MOVE RIGHT TO LEFT
                r1 = new Rectangle(animY - sizeOfSqaure, startYPoint, sizeOfSqaure, sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), 0);
                break;
            case 6:
                //MOVE RIGHT TO LEFT, BOTTOM TO TOP
                r1 = new Rectangle(startXPoint, 0, sizeOfSqaure, sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), animX - sizeOfSqaure);
                moveYAxis = new KeyValue(r1.yProperty(), animY - sizeOfSqaure);
                break;

            default:
                System.out.println("default");
        }

        r1.setFill(Color.web("red"));
        r1.setOpacity(0.2);
        r1.toBack();

        KeyFrame keyFrame = new KeyFrame(Duration.millis(speedOfSqaure * 1000), moveXAxis, moveYAxis);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        pane.getChildren().add(pane.getChildren().size() - 1, r1);
    }
}
