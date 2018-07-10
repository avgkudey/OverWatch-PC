package overwatchpc;

import detailsCollector.Collector;
import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ConnectionController implements Initializable {

    @FXML
    private AnchorPane ancpane;

    @FXML
    private Label lblinfo;

    @FXML
    private Label lblconnection;
    @FXML
    private ImageView imgqr;
    public static PermissionStates permissionStates = new PermissionStates();
    private int animX = 500;
    private int animY = 300;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        permissionStates.setState(0);

        ancpane.setBackground(Background.EMPTY);
        try {

            File file = new File("C:\\Users\\Public\\qr.jpg");
            Image image = new Image(file.toURI().toString());
            imgqr.setImage(image);
            lblinfo.setText(connect.ConnectionDetails.serverIP);
        } catch (Exception e) {
            System.err.println(e);
        }
        detailsCollector.Collector collector = new Collector();
        collector.start();
        int numberOfSquares = 30;
        while (numberOfSquares > 0) {
            generateAnimation();
            numberOfSquares--;
        }
        imgqr.toFront();
        permissionStates.stateProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                if (permissionStates.getState() == 1) {
                    Platform.runLater(() -> {
                        try {
                            authorize();
                        } catch (Exception ex) {
                            System.err.println(ex);
                        }
                    });
                }
            }
        });
    }

    public void authorize() {
        try {
            Stage primaryStage = (Stage) lblinfo.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("connectedScreen.fxml").openStream());
            Scene scene = new Scene(root);
            scene.setFill(null);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e2) {

        }
    }

    public void minimize(ActionEvent e) {
        Stage primaryStage = (Stage) lblinfo.getScene().getWindow();
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
        ancpane.getChildren().add(ancpane.getChildren().size() - 1, r1);
    }
}
