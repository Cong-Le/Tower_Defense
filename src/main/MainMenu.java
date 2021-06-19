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
    GraphicsContext gc;
    GamePlay gamePlay = new GamePlay();
    MediaPlayer mediaPlayer;
    int flagMusic = 1;

    public void start(Stage stage) {
        Canvas canvas = new Canvas(1020, 600);
        Group root = new Group();
        gc = canvas.getGraphicsContext2D();
        Image ss = new Image("file:src/Image/ScreenSaver.png");
        gc.drawImage(ss, 0, 0);

        Image playImg = new Image("file:src/Image/PlayNow.png");
        ImageView playIv = new ImageView(playImg);
        playIv.setFitWidth(200); playIv.setFitHeight(60);
        playIv.setLayoutX(400); playIv.setLayoutY(350);

        Button playButton = new Button();
        playButton.setLayoutX(400); playButton.setLayoutY(350);
        playButton.setPrefSize(200, 60);
        playButton.setOpacity(0);

        // Bật tắt nhạc
        music(); mediaPlayer.play();
        Button musicButton = new Button();   musicButton.setLayoutX(960); musicButton.setLayoutY(15);
        musicButton.setPrefSize(50, 50);    musicButton.setOpacity(0);
        gc.drawImage(new Image("file:src/Image/MusicOn.png"), 960, 15);
        musicButton.setOnMouseClicked((eventMusic) -> {
            if (flagMusic == 1) {   // Âm đang bật
                flagMusic = 0;
                gc.drawImage(new Image("file:src/Image/MusicOff.png"), 960, 15);
                mediaPlayer.stop();
            } else {
                flagMusic = 1;
                gc.drawImage(new Image("file:src/Image/MusicOn.png"), 960, 15);
                mediaPlayer.play();
            }
        });

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

        root.getChildren().addAll(canvas, playIv, label, musicButton, playButton);
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
