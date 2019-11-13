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

public class PlayGame {

    private GraphicsContext gc;
    private List<TowerObject>  towerObjects  = new ArrayList<>();
    private List<EnemyObject>  enemyObjects  = new ArrayList<>();
    private List<EnemyObject> temp = Run.enemy();

    private final List<Moutain> moutainsSmall = Moutain.moutainsSmall();    //List các điểm có thể đặt tháp (Moutain Point), kèm button tương ứng
    private final List<Moutain> moutainsBig   = Moutain.moutainsBig();
    private List<Moutain> storeButton;

    private CreateTower  ct = new CreateTower();
    private CreateBullet cb = new CreateBullet();
    private Enemy e = new Enemy();
    private Run run = new Run();

    private int flag = 0,flag2 = 0;   // Chuột có ở Moutain Point hay không
    private int sx, sy; // Tọa độ để vẽ Store
    private int[] time = new int[10];   // Mảng lưu thời gian để xử lí tốc độ sinh đạn
    private int timeE = 0;
    private int index = 0; // Chỉ mục của list enemy

    //@Override
    public void start(Stage stage) {
        Arrays.fill(time, 100);


        Canvas canvas = new Canvas(1020, 600);
        gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        Button helpB = new Button("Click me");   helpB.setLayoutX(950); helpB.setLayoutY(10); //helpB.setPrefSize(30, 30);
        helpB.setOnMouseClicked((eventH) -> {
            flag2 = 1;
        });
        helpB.setOnMouseExited((eventH) -> {        // Bảng trợ giúap
            flag2 = 0;
        });
        root.getChildren().addAll(canvas, helpB);

        // Code tạo tháp
        for (int i=0; i<moutainsSmall.size(); i++) {
            root.getChildren().addAll(moutainsBig.get(i).button, moutainsSmall.get(i).button);
            int i2 = i;
            moutainsSmall.get(i).button.setOnMouseEntered((event) -> {   // Kéo chuột vào Moutain Point
                if ( moutainsSmall.get(i2).empty ) {  // Vị trí còn trống (Chưa được xây tháp)
                    flag = 1;
                    sx = moutainsSmall.get(i2).x;
                    sy = moutainsSmall.get(i2).y;

                    storeButton = Moutain.StoreButton(sx, sy);  // Tạo 3 button của store
                    root.getChildren().addAll(storeButton.get(0).button, storeButton.get(1).button, storeButton.get(2).button);
                    //Circle towerRange = new Circle();
                    //towerRange.setCenterX(moutainsSmall.get(i2).x); towerRange.setCenterY(moutainsSmall.get(i2).y);

                    storeButton.get(0).button.setOnMouseClicked((event2) -> {   // Click đặt Normal Tower
                        if (run.MONEY >= Config.NorT_Price) {
                            towerObjects.add(ct.creNormalTower(moutainsSmall.get(i2).x, moutainsSmall.get(i2).y));
                            moutainsSmall.get(i2).empty = false;
                            run.MONEY -= Config.NorT_Price;
                            flag = 0;
                        }
                    });
                    storeButton.get(1).button.setOnMouseClicked((event3) -> {   // Click đặt Sniper Tower
                        if (run.MONEY >= Config.SniT_Price) {
                            towerObjects.add(ct.creSniperTower(moutainsSmall.get(i2).x, moutainsSmall.get(i2).y));
                            moutainsSmall.get(i2).empty = false;
                            run.MONEY -= Config.SniT_Price;
                            flag = 0;
                        }
                    });
                    storeButton.get(2).button.setOnMouseClicked((event4) -> {   // Click đặt MachineGun Tower
                        if (run.MONEY >= Config.MacT_Price) {
                            towerObjects.add(ct.creMachineTower(moutainsSmall.get(i2).x, moutainsSmall.get(i2).y));
                            moutainsSmall.get(i2).empty = false;
                            run.MONEY -= Config.MacT_Price;
                            flag = 0;
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

                // Sinh địch từ list có sẵn
                if (timeE >= 600 && index <=55) {
                    enemyObjects.add(temp.get(index++));
                    timeE = 0;
                }
                // Kiểm tra tình trạng của địch
                int iE = 0;
                while (iE < enemyObjects.size()) {
                    if (enemyObjects.get(iE).health <= 0) {
                        run.MONEY += enemyObjects.get(iE).reward;
                        //System.out.println("Money = " + run.MONEY);
                        enemyObjects.remove(iE);
                    } else if (enemyObjects.get(iE).pass) {
                        enemyObjects.remove(iE);
                        run.HP--;
                    } else iE++;
                }

                // Code kiểm tra và tạo bullet tấn công địch
                for (int iT = 0; iT < towerObjects.size(); iT++) {
                    iE = 0;
                    if (time[iT] > towerObjects.get(iT).spaw) {
                        while (iE < enemyObjects.size()) {

                            int xPreEnemy = e.getPresentCoordinates(enemyObjects.get(iE)).x;    // Tọa độ hiện tại của địch
                            int yPreEnemy = e.getPresentCoordinates(enemyObjects.get(iE)).y;
                            double distance = Road.distance(towerObjects.get(iT).x, towerObjects.get(iT).y, xPreEnemy, yPreEnemy); // Khoảng cách

                            if (distance <= towerObjects.get(iT).range) { //Địch trong tầm bắn và đủ thời gian để sinh đạn
                                time[iT] = 0;   // reset lại thời gian chờ sinh đạn

                                // Kiểm tra loại tháp, tạo bullet tương ứng và thêm vào bullet list của tháp đó
                                if (towerObjects.get(iT).ID == 1)  // Là Normal Tower
                                    towerObjects.get(iT).bulletofTower.add(cb.creNormalBullet(towerObjects.get(iT).x, towerObjects.get(iT).y, enemyObjects.get(iE)));
                                if (towerObjects.get(iT).ID == 2)   // Là Sniper Tower
                                    towerObjects.get(iT).bulletofTower.add(cb.creSniperBullet(towerObjects.get(iT).x, towerObjects.get(iT).y, enemyObjects.get(iE)));
                                if (towerObjects.get(iT).ID == 3)   // Là Machine Tower
                                    towerObjects.get(iT).bulletofTower.add(cb.creMachineBullet(towerObjects.get(iT).x, towerObjects.get(iT).y, enemyObjects.get(iE)));
                                break;
                            }
                            iE++;
                        }
                    }
                    // Xóa các bullet đã trúng địch
                    int iB = 0;
                    while (iB < towerObjects.get(iT).bulletofTower.size()) {
                        if (towerObjects.get(iT).bulletofTower.get(iB).hit) {    // Đạn đã trúng địch
                            towerObjects.get(iT).bulletofTower.get(iB).enemy.health -= towerObjects.get(iT).bulletofTower.get(iB).damage;    // Trừ máu địch theo sát thương của đạn
                            // Viên đạn thứ iB của tháp thứ iT (Cơ mà đoạn này có khi chỉ cần xét iB = 0)
                            towerObjects.get(iT).bulletofTower.remove(iB);       // Xóa đạn trong List
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

    private void update() {
        //BulletObjects.forEach(GameObject::update);
        towerObjects.forEach(bT -> bT.bulletofTower.forEach(GameObject::update));
        enemyObjects.forEach(GameObject::update);
        towerObjects.forEach(GameObject::update);
    }

    private void render() {
        //BulletObjects.forEach(g -> g.render(gc));
        //TowerObjects.forEach(bT -> bT.bulletofTower.forEach(g -> g.render(gc)));


        for(int i=enemyObjects.size()-1; i>=0; i--) {
            enemyObjects.get(i).render(gc);
        }   // // In Enemy

        towerObjects.forEach(g -> g.render(gc));    // In Tower

        for (TowerObject towerObject : towerObjects) {
            for (int n = 0; n < towerObject.bulletofTower.size(); n++) {
                towerObject.bulletofTower.get(n).render(gc);
            }
        }   // In Bullet
    }
}
