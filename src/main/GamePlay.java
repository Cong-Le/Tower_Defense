package main;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GamePlay {
    private AnimationTimer timer;
    private GraphicsContext gc;
    private MediaPlayer mediaPlayer;
    private MediaPlayer music;
    private MediaPlayer build;
    private MediaPlayer normal;
    private MediaPlayer sniper;
    private MediaPlayer machine;

    private List<TowerObject>  towerObjects  = new ArrayList<>();   // List các tower và enemy trên màn hình
    private List<EnemyObject>  enemyObjects  = new ArrayList<>();
    private List<EnemyObject> temp = Run.enemy();   // List các enemy mặc đinh

    private Mountain mountains = new Mountain();
    private List<Mountain> mountainsSmall = mountains.mountainsSmall();    //List các điểm có thể đặt tháp (Mountain Point), kèm button tương ứng
    private List<Mountain> mountainsBig   = mountains.mountainsBig();
    private List<Mountain> storeButton;

    private MainMenu mainMenu;
    private CreateTower  ct = new CreateTower();
    private CreateBullet cb = new CreateBullet();
    private Enemy e = new Enemy();
    private Run run = new Run();

    private int flagBuild = 0, flagSound = 1, flagMusic = 1, flagTime = 1;
    private int sx, sy; // Tọa độ để vẽ Store
    private int[] time = new int[10];   // Mảng lưu thời gian để xử lí tốc độ sinh đạn
    private int timeE = 0;
    private int index = 0; // Chỉ mục của list enemy
    private  int sizeOfEnemy = temp.size();

    private Stage window = new Stage();
    private Group root = new Group();
    private Image soundImg = new Image("file:src/Image/SoundOn.png");
    private Image musicImg = new Image("file:src/Image/MusicOn.png");

    //@Override
    void start(Stage stage) {
        Arrays.fill(time, 100);

        mainMenu = new MainMenu();
        window = stage;

        Canvas canvas = new Canvas(1020, 600);
        gc = canvas.getGraphicsContext2D();


        // Hiện HP, Money người chơi
        Label hp = new Label(); Label money = new Label(); Label wave = new Label();
        hp.setText(String.valueOf(run.HP)); money.setText(String.valueOf(run.MONEY));
        wave.setText("WAVE  " + String.valueOf(index) + "/56");

        hp.setFont(Font.loadFont("file:src/Font/FSAhkioThin.otf", 25));
        money.setFont(Font.loadFont("file:src/Font/FSAhkioThin.otf", 25));
        wave.setFont(Font.loadFont("file:src/Font/FSAhkioThin.otf", 18));

        hp.setTextFill(Color.WHEAT);    money.setTextFill(Color.WHEAT); wave.setTextFill(Color.WHEAT);
        hp.setLayoutX(80); hp.setLayoutY(18);
        money.setLayoutX(155); money.setLayoutY(18);
        wave.setLayoutX(93); wave.setLayoutY(54);

        // Sound
        Button soundButton = new Button();   soundButton.setLayoutX(960); soundButton.setLayoutY(80);
        soundButton.setPrefSize(50, 50);    soundButton.setOpacity(0);
        soundButton.setOnMouseClicked((eventSound) -> {
            if (flagSound == 1) {   // Âm đang bật
                flagSound = 0;
                soundImg = new Image("file:src/Image/SoundOff.png");
            } else {
                flagSound = 1;
                soundImg = new Image("file:src/Image/SoundOn.png");
            }
        });
        // Music
        Music(); music.play();
        Button musicButton = new Button();   musicButton.setLayoutX(960); musicButton.setLayoutY(150);
        musicButton.setPrefSize(50, 50);    musicButton.setOpacity(0);
        musicButton.setOnMouseClicked((eventMusic) -> {
            if (flagMusic == 1) {   // Âm đang bật
                flagMusic = 0;
                musicImg = new Image("file:src/Image/MusicOff.png");
                music.pause();
            } else {
                flagMusic = 1;
                musicImg = new Image("file:src/Image/MusicOn.png");
                music.play();
            }
        });

        // Pause, Play
        Button PauButton = new Button();
        PauButton.setLayoutX(950); PauButton.setLayoutY(10); PauButton.setPrefSize(50, 50); PauButton.setOpacity(0);
        PauButton.setOnMouseClicked((eventPause) -> {
            if (flagTime == 1) {
                timer.stop();
                music.pause();
                Image playImg = new Image("file:src/Image/Play.png");
                gc.drawImage(playImg, 960, 10);
                flagTime = 0;
            } else {
                timer.start();
                music.play();
                flagTime = 1;
            }
        });
        root.getChildren().addAll(canvas, hp, money, wave, PauButton, soundButton, musicButton);


        // Code tạo tháp
        for (int i=0; i<mountainsSmall.size(); i++) {
            root.getChildren().addAll(mountainsBig.get(i).getButton(), mountainsSmall.get(i).getButton());
            int i2 = i;
            mountainsSmall.get(i).getButton().setOnMouseEntered((event) -> {   // Kéo chuột vào Mountain Point
                if ( mountainsSmall.get(i2).getEmpty() ) {  // Vị trí còn trống (Chưa được xây tháp)
                    flagBuild = 1;
                    sx = mountainsSmall.get(i2).getX();   // Tọa độ để vẽ store
                    sy = mountainsSmall.get(i2).getY();

                    storeButton = Mountain.StoreButton(sx, sy);  // Tạo 3 button của store
                    root.getChildren().addAll(storeButton.get(0).getButton(), storeButton.get(1).getButton(), storeButton.get(2).getButton());

                    storeButton.get(0).getButton().setOnMouseClicked((event2) -> {   // Click đặt Normal Tower
                        if (run.MONEY >= Config.NorT_Price) {
                            if (flagSound==1) SoundBuild();
                            towerObjects.add(ct.creNormalTower(mountainsSmall.get(i2).getX(), mountainsSmall.get(i2).getY()));
                            mountainsSmall.get(i2).setEmpty(false);
                            run.MONEY -= Config.NorT_Price;
                            flagBuild = 0;
                        }
                    });
                    storeButton.get(1).getButton().setOnMouseClicked((event3) -> {   // Click đặt Sniper Tower
                        if (run.MONEY >= Config.SniT_Price) {
                            if (flagSound==1) SoundBuild();
                            towerObjects.add(ct.creSniperTower(mountainsSmall.get(i2).getX(), mountainsSmall.get(i2).getY()));
                            mountainsSmall.get(i2).setEmpty(false);
                            run.MONEY -= Config.SniT_Price;
                            flagBuild = 0;
                        }
                    });
                    storeButton.get(2).getButton().setOnMouseClicked((event4) -> {   // Click đặt MachineGun Tower
                        if (run.MONEY >= Config.MacT_Price) {
                            if (flagSound==1) SoundBuild();
                            towerObjects.add(ct.creMachineTower(mountainsSmall.get(i2).getX(), mountainsSmall.get(i2).getY()));
                            mountainsSmall.get(i2).setEmpty(false);
                            run.MONEY -= Config.MacT_Price;
                            flagBuild = 0;
                        }
                    });
                }
                storeButton.clear();
            });
            mountainsBig.get(i).getButton().setOnMouseExited((event) -> {  //Kéo chuột khỏi Mountain Point
                flagBuild = 0;
            });
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Tower Defense");
        stage.show();

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                drawMap(gc);
                render();
                update();
                checkGameOver(gc);
                if (flagBuild == 1) drawStore(gc, sx, sy);   // Hiện store khi kéo chuột đến Mountain Point

                hp.setText(String.valueOf(run.HP)); money.setText(String.valueOf(run.MONEY));   // Cập nhật HP và Money của người chơi
                wave.setText("WAVE  " + String.valueOf(index) + "/56");

                // Sinh địch từ list có sẵn
                if (timeE >= 450 && index < sizeOfEnemy) {
                    enemyObjects.add(temp.get(index++));
                    timeE = 0;
                }
                // Kiểm tra tình trạng của địch
                int iE = 0;
                while (iE < enemyObjects.size()) {
                    if (enemyObjects.get(iE).getHealth() <= 0) {     // Địch hết HP
                        run.MONEY += enemyObjects.get(iE).getReward();
                        enemyObjects.remove(iE);
                    } else if (enemyObjects.get(iE).getPass()) {     // Địch đã đến điểm cuối
                        enemyObjects.remove(iE);
                        run.HP--;
                    } else iE++;
                }

                // Code kiểm tra và tạo bullet tấn công địch
                for (int iT = 0; iT < towerObjects.size(); iT++) {
                    iE = 0;
                    if (time[iT] > towerObjects.get(iT).getSpaw()) {
                        while (iE < enemyObjects.size()) {

                            int xPreEnemy = e.getPresentCoordinates(enemyObjects.get(iE)).getX();    // Tọa độ hiện tại của địch
                            int yPreEnemy = e.getPresentCoordinates(enemyObjects.get(iE)).getY();
                            double distance = Road.distance(towerObjects.get(iT).getX()+30, towerObjects.get(iT).getY()+50, xPreEnemy, yPreEnemy); // Khoảng cách

                            if (distance <= towerObjects.get(iT).getRange()) { //Địch trong tầm bắn và đủ thời gian để sinh đạn
                                time[iT] = 0;   // reset lại thời gian chờ sinh đạn

                                // Kiểm tra loại tháp, tạo bullet tương ứng và thêm vào bullet list của tháp đó
                                if (towerObjects.get(iT).getID() == 1) {  // Là Normal Tower
                                    if (flagSound==1) SoundNormal();
                                    towerObjects.get(iT).getBulletofTower().add(cb.creNormalBullet(towerObjects.get(iT).getX(), towerObjects.get(iT).getY(), enemyObjects.get(iE)));
                                }
                                if (towerObjects.get(iT).getID() == 2) {  // Là Sniper Tower
                                    if (flagSound==1) SoundSniper();
                                    towerObjects.get(iT).getBulletofTower().add(cb.creSniperBullet(towerObjects.get(iT).getX(), towerObjects.get(iT).getY(), enemyObjects.get(iE)));
                                }
                                if (towerObjects.get(iT).getID() == 3) {  // Là Machine Tower
                                    if (flagSound==1) SoundMachine();
                                    towerObjects.get(iT).getBulletofTower().add(cb.creMachineBullet(towerObjects.get(iT).getX(), towerObjects.get(iT).getY(), enemyObjects.get(iE)));
                                }
                                break;
                            }
                            iE++;
                        }
                    }
                    // Xóa các bullet đã trúng địch
                    int iB = 0;
                    while (iB < towerObjects.get(iT).getBulletofTower().size()) {
                        if (towerObjects.get(iT).getBulletofTower().get(iB).getHit()) {    // Đạn đã trúng địch
                            towerObjects.get(iT).getBulletofTower().get(iB).getEnemy().setHealth(towerObjects.get(iT).getBulletofTower().get(iB).getEnemy().getHealth()  - (int)towerObjects.get(iT).getBulletofTower().get(iB).getDamage());    // Trừ máu địch theo sát thương của đạn
                            towerObjects.get(iT).getBulletofTower().remove(iB);       // Xóa đạn trong List
                        } else iB++;
                    }
                    timeE += 2;     // Càng nhiều Tower, địch sinh càng nhanh;
                    time[iT]++;     // Tăng thời gian chờ, đạt đủ sẽ có thể sinh đạn
                }
                timeE += 2;
            }

        };
        timer.start();

    }
    private void checkGameOver(GraphicsContext gc) {
        if (run.HP <= 0) {      // Defeat
            timer.stop();
            if (flagSound==1)
                try {
                    Media media = new Media(getClass().getResource("/Sound/Defeat.mp3").toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.setAutoPlay(true);
                    mediaPlayer.setVolume(0.5);
                } catch (Exception e) {}

            Image VictoryImg = new Image("file:src/Image/Defeat.png");
            gc.drawImage(VictoryImg, 330, 100, 360, 400);
            Continue();
        }
        if (index==sizeOfEnemy && enemyObjects.isEmpty()) {  // Victory
            timer.stop();
            if (flagSound==1)
                try {
                    Media media = new Media(getClass().getResource("/Sound/Victory.mp3").toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.setAutoPlay(true);
                    mediaPlayer.setVolume(0.5);
                } catch (Exception e) {}

            Image VictoryImg = new Image("file:src/Image/Victory.png");
            gc.drawImage(VictoryImg, 206, 50, 608, 500);
            Continue();
        }
    }   // Kiểm tra kết thúc game

    private void Continue() {
        Button ContiButton = new Button();
        ContiButton.setLayoutX(455);    ContiButton.setLayoutY(400);
        ContiButton.setPrefSize(110, 25);
        ContiButton.setOpacity(0);

        Image ContiImg = new Image("file:src/Image/Continue.png");
        ImageView ContiIv = new ImageView(ContiImg);
        ContiIv.setLayoutX(455); ContiIv.setLayoutY(400);

        ContiButton.setOnMouseEntered((eventContinue) -> {
            Bloom bloom = new Bloom();
            bloom.setThreshold(0.5);      // Đặt ngưỡng (Threshold).
            ContiIv.setEffect(bloom);

            ContiButton.setOnMouseExited((Event2) -> {
                bloom.setThreshold(1);
                ContiIv.setEffect(bloom);
            });
        });

        ContiButton.setOnMouseClicked((eventContinue) -> {
            music.stop();
            mainMenu.start(window);
        });

        root.getChildren().addAll(ContiIv, ContiButton);
    }   // Button continue quay về màn hình chờ

    private void drawStore(GraphicsContext gc, int x, int y) {
        Image store = new Image("file:src/Image/Store.png");
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        ImageView iv = new ImageView(store);
        iv.setFitWidth(130);
        iv.setFitHeight(130);
        Image st = iv.snapshot(params, null);
        gc.drawImage(st, x-24, y-5);
    }   // In ra store

    private void drawMap(GraphicsContext gc) {
        gc.drawImage(new Image("file:src/Image/Map3.png"), 0, 0);
        gc.drawImage(new Image("file:src/Image/PlayerInfo2.png"), 20, 10, 210, 75);
        gc.drawImage(new Image("file:src/Image/Pause.png"), 960, 10);
        gc.drawImage(soundImg, 960, 80);
        gc.drawImage(musicImg, 960, 150);
    }

    private void update() {
        towerObjects.forEach(bT -> bT.getBulletofTower().forEach(GameObject::update));
        enemyObjects.forEach(GameObject::update);
        towerObjects.forEach(GameObject::update);
    }

    private void render() {
        //In Enemy
        for(int i=enemyObjects.size()-1; i>=0; i--) {
            enemyObjects.get(i).render(gc);
        }

        towerObjects.forEach(g -> g.render(gc));    // In Tower

        for (TowerObject towerObject : towerObjects) {
            for (int n = 0; n < towerObject.getBulletofTower().size(); n++) {
                towerObject.getBulletofTower().get(n).render(gc);
            }
        }   //In Bullet
    }

    private void Music() {
        try {
            Media media = new Media(getClass().getResource("/Sound/PiratesoftheCaribbean.mp3").toURI().toString());
            music = new MediaPlayer(media);
            music.setVolume(0.15);
        } catch (Exception e) {}
    }
    private void SoundBuild() {
        try {
            Media media = new Media(getClass().getResource("/Sound/Build.mp3").toURI().toString());
            build = new MediaPlayer(media);
            build.setAutoPlay(true);
            build.setVolume(0.7);
        } catch (Exception e) {}
    }
    private void SoundNormal() {
        try {
            Media media = new Media(getClass().getResource("/Sound/Normal.mp3").toURI().toString());
            normal = new MediaPlayer(media);
            normal.setAutoPlay(true);
            normal.setVolume(0.1);
        } catch (Exception e) {}
    }
    private void SoundSniper() {
        try {
            Media media = new Media(getClass().getResource("/Sound/Sniper.mp3").toURI().toString());
            sniper = new MediaPlayer(media);
            sniper.setAutoPlay(true);
            sniper.setVolume(0.2);
        } catch (Exception e) {}
    }
    private void SoundMachine() {
        try {
            Media media = new Media(getClass().getResource("/Sound/Machine.mp3").toURI().toString());
            machine = new MediaPlayer(media);
            machine.setAutoPlay(true);
            machine.setVolume(0.2);
        } catch (Exception e) {}
    }
}
