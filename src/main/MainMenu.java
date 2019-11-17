package main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenu extends Application {

    GamePlay gamePlay = new GamePlay();
    MediaPlayer mediaPlayer;

    public void start(Stage stage) {
        Canvas canvas = new Canvas(1020, 600);
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
        music();
        mediaPlayer.play();

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
            mediaPlayer.stop();
            gamePlay.start(stage);
        });

        Label label = new Label("*Hình ảnh chỉ mang tính chất minh họa cho sản phẩm");
        label.setFont(Font.loadFont("file:src/Font/FSAhkioThin.otf", 20));
        label.setTextFill(Color.WHITE);
        label.setLayoutX(20); label.setLayoutY(570);

        root.getChildren().addAll(canvas, ss, playIv, label, playButton);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Tower Defense");
        stage.show();
    }
    private void music() {
        try {
            Media media = new Media(getClass().getResource("/Sound/ScreenSaver.mp3").toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.2);
        } catch (Exception e) {}
    }
}
