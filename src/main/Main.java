package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {

    GraphicsContext gc;
    List<TowerObject>  TowerObjects  = new ArrayList<>();
    List<EnemyObject>  EnemyObjects  = new ArrayList<>();
    List<BulletObject> BulletObjects = new ArrayList<>();
    List<EnemyObject> temp = Run.enemy();

    final List<Moutain> moutainsSmall = Moutain.moutainsSmall();    //List các điểm có thể đặt tháp (Moutain Point), kèm button tương ứng
    final List<Moutain> moutainsBig   = Moutain.moutainsBig();
    List<Moutain> storeButton;

    CreateEnemy  ce = new CreateEnemy();
    CreateTower  ct = new CreateTower();
    CreateBullet cb = new CreateBullet();
    Enemy e = new Enemy();
    Bullet b = new Bullet();
    Road road = new Road();
    Run run = new Run();

    int flag = 0,flag2 = 0;   // Chuột có ở Moutain Point hay không
    int sx, sy; // Tọa độ để vẽ Store
    int time[] = new int[10];
    int timeE = 0;
    int index = 0; // Chỉ mục của list enemy
    int HP = Config.Player_HP, Money = Config.Player_Money;

    @Override
    public void start(Stage stage) {
        Arrays.fill(time, 100);


        Canvas canvas = new Canvas(1020, 600);
        gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        Button helpB = new Button("Click me");   helpB.setLayoutX(950); helpB.setLayoutY(10); //helpB.setPrefSize(30, 30);
        helpB.setOnMouseClicked((eventH) -> {
            flag2 = 1;
        });
        helpB.setOnMouseExited((eventH) -> {
            flag2 = 0;
        });
        root.getChildren().addAll(canvas, helpB);

        // Code tạo tháp
        for (int i=0; i<moutainsSmall.size(); i++) {
            root.getChildren().addAll(moutainsBig.get(i).button, moutainsSmall.get(i).button);
            int finalI = i;
            moutainsSmall.get(i).button.setOnMouseEntered((event) -> {   // Kéo chuột vào Moutain Point
                if (moutainsSmall.get(finalI).empty == true) {  // Vị trí còn trống (Chưa được xây tháp)
                    flag = 1;
                    sx = moutainsSmall.get(finalI).x;
                    sy = moutainsSmall.get(finalI).y;

                    storeButton = Moutain.StoreButton(sx, sy);  // Tạo 3 button của store
                    root.getChildren().addAll(storeButton.get(0).button, storeButton.get(1).button, storeButton.get(2).button);

                    storeButton.get(0).button.setOnMouseClicked((event2) -> {   // Click đặt Normal Tower
                        if (run.MONEY >= Config.NorT_Price) {
                            TowerObjects.add(ct.creNormalTower(moutainsSmall.get(finalI).x, moutainsSmall.get(finalI).y));
                            moutainsSmall.get(finalI).empty = false;
                            run.MONEY -= Config.NorT_Price;
                        }
                    });
                    storeButton.get(1).button.setOnMouseClicked((event3) -> {   // Click đặt Sniper Tower
                        if (run.MONEY >= Config.SniT_Price) {
                            TowerObjects.add(ct.creSniperTower(moutainsSmall.get(finalI).x, moutainsSmall.get(finalI).y));
                            moutainsSmall.get(finalI).empty = false;
                            run.MONEY -= Config.SniT_Price;
                        }
                    });
                    storeButton.get(2).button.setOnMouseClicked((event4) -> {   // Click đặt MachineGun Tower
                        if (run.MONEY >= Config.MacT_Price) {
                            TowerObjects.add(ct.creMachineTower(moutainsSmall.get(finalI).x, moutainsSmall.get(finalI).y));
                            moutainsSmall.get(finalI).empty = false;
                            run.MONEY -= Config.MacT_Price;
                        }
                    });
                }

                storeButton.clear();
            });
            moutainsBig.get(i).button.setOnMouseExited((event) -> {  //Kéo chuột khỏi Moutain Point
                flag = 0;
            });

        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Tower Defense");
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                drawMap(gc);
                if (flag2 == 1) drawHelpTable(gc);
                render();
                update();
                if (flag == 1) drawStore(gc, sx, sy);   // Hiện store khi kéo chuột đến Moutain Point
                //run.GameStatus(stage);

                if (timeE >= 300 && index <=55) {
                    EnemyObjects.add(temp.get(index++));
                    timeE = 0;
                }

                // Code kiểm tra và tạo bullet tấn công địch
                int iE = 0;
                for (int iT = 0; iT < TowerObjects.size(); iT++) {
                   // for (int iE = 0; iE < EnemyObjects.size() ; iE++) {
                    while (iE < EnemyObjects.size()) {

                        int xPreEnemy = e.getPresentCoordinates(EnemyObjects.get(iE)).x;    // Tọa độ hiện tại của địch
                        int yPreEnemy = e.getPresentCoordinates(EnemyObjects.get(iE)).y;

                        double distance = road.distance(TowerObjects.get(iT).x, TowerObjects.get(iT).y, xPreEnemy, yPreEnemy); // Khoảng cách

                        //System.out.println("Time" + iT + "= " + time[iT]);
                        if (distance <= TowerObjects.get(iT).range && time[iT] > TowerObjects.get(iT).spaw) { //Địch trong tầm bắn và đủ thời gian để sinh đạn
                            time[iT] = 0;
                            //System.out.println("Hihi");
                            if (TowerObjects.get(iT).ID == 1)   // Là Normal Tower
                                BulletObjects.add(cb.creNormalBullet(TowerObjects.get(iT).x, TowerObjects.get(iT).y, xPreEnemy, yPreEnemy));
                                // Tạo 1 bullet tương ứng với Tower
                            if (TowerObjects.get(iT).ID == 2)   // Là Sniper Tower
                                BulletObjects.add(cb.creSniperBullet(TowerObjects.get(iT).x, TowerObjects.get(iT).y, xPreEnemy, yPreEnemy));

                            if (TowerObjects.get(iT).ID == 3)   // Là Machine Tower
                                BulletObjects.add(cb.creMachineBullet(TowerObjects.get(iT).x, TowerObjects.get(iT).y, xPreEnemy, yPreEnemy));

                                // Thêm bullet đó vào List để render
                            break;
                        }
                        time[iT]++;

                        int iB = 0;
                        while (iB < BulletObjects.size()) { // Xóa các bullet đã trúng địch
                            if (BulletObjects.get(iB).hit) {    // Đạn đã trúng địch
                                EnemyObjects.get(iE).health -= BulletObjects.get(iB).damage;    // Trừ máu địch theo sát thương của đạn
                                BulletObjects.remove(iB);       // Xóa đạn trong List
                            } else iB++;
                        }

                        if (EnemyObjects.get(iE).health <= 0) {   // Hạ địch, cộng money cho người chơi
                            run.MONEY += EnemyObjects.get(iE).reward;
                            System.out.println("Death");
                            EnemyObjects.remove(iE);
                        } else if (EnemyObjects.get(iE).pass) { // Địch đến điểm cuối, trừ HP người chơi
                            EnemyObjects.remove(iE);
                            run.HP--;
                        }else iE++;
                        //run.GameStatus();
                    }
                    timeE += 1; // Càng nhiều Tower, địch sinh càng nhanh;

                }
                timeE += 3;

            }

        };
        timer.start();

    }
    private void drawHelpTable(GraphicsContext gc) {        // Có thể in ra bảng trợ giúp
        Image helptable = new Image("file:src/Image/HelpTable.png");
        SnapshotParameters params = new SnapshotParameters();

        params.setFill(Color.TRANSPARENT);
        ImageView iv = new ImageView(helptable);
        //iv.setFitWidth(130);
        //iv.setFitHeight(130);
        Image ht = iv.snapshot(params, null);
        gc.drawImage(ht, 1010 - helptable.getWidth(), 40);
    }

    private void drawStore(GraphicsContext gc, int x, int y) {  // In ra store
        Image store = new Image("file:src/Image/Store.png");
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        ImageView iv = new ImageView(store);
        iv.setFitWidth(130);
        iv.setFitHeight(130);
        Image st = iv.snapshot(params, null);
        gc.drawImage(st, x-24, y-5);
    }

    private void drawMap(GraphicsContext gc) {
        gc.drawImage(new Image("file:src/Image/Map3.png"), 0, 0);
    }

    public void update() {
        BulletObjects.forEach(GameObject::update);
        EnemyObjects.forEach(GameObject::update);
        TowerObjects.forEach(GameObject::update);
    }

    public void render() {
        BulletObjects.forEach(g -> g.render(gc));
        EnemyObjects.forEach(g -> g.render(gc));
        TowerObjects.forEach(g -> g.render(gc));
    }
}
