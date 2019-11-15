package main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainMenu extends Application {
    private GraphicsContext gc;
    GamePlay gamePlay = new GamePlay();

    public void start(Stage stage) {
        Canvas canvas = new Canvas(1020, 600);
        gc = canvas.getGraphicsContext2D();
        Group root = new Group();

        ImageView ss = new ImageView("file:src/Image/ScreenSaver.png");

        Button playButton = new Button();
        Image playImg = new Image("file:src/Image/PlayNow.png");
        ImageView playIv = new ImageView(playImg);
        playIv.setFitWidth(200); playIv.setFitHeight(60);
        playIv.setLayoutX(400); playIv.setLayoutY(350);

        playButton.setLayoutX(400); playButton.setLayoutY(350);
        playButton.setPrefSize(200, 60);
        playButton.setOpacity(0);

        playButton.setOnMouseEntered((Event1) -> {  // Làm sáng button khi di chuột vào
            Bloom bloom = new Bloom();
            // Đặt ngưỡng (Threshold).
            bloom.setThreshold(0.75);
            playIv.setEffect(bloom);

            playButton.setOnMouseExited((Event2) -> {
                bloom.setThreshold(1);
                playIv.setEffect(bloom);
            });
        });


        playButton.setOnMouseClicked((Event) -> {
            gamePlay.start(stage);
        });

        root.getChildren().addAll(canvas, ss, playIv, playButton);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Tower Defense");
        stage.show();
    }
}
