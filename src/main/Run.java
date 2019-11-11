package main;

import javafx.scene.Group;
import javafx.scene.Parent;
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
import java.util.List;

public class Run {
    protected int HP = Config.Player_HP;
    protected int MONEY = Config.Player_Money;

   /* public void render(GraphicsContext gc) {
        Canvas canvas = new Canvas(1020, 600);
        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Tower Defense");
        stage.show();
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        ImageView iv = new ImageView(EnemyImg);
        //iv.setRotate(this.direction.getDegree());
        //iv.setFitWidth(30);
        //iv.setFitHeight(30);
        Image enemy = iv.snapshot(params, null);

        gc.drawImage(enemy, x, y);
    }*/
    public void GameStatus(Stage stage) {

    }

    static CreateEnemy ce = new CreateEnemy();

    public static List<EnemyObject> enemy() {
        List<EnemyObject> e = new ArrayList<>();
        for (int i=0; i<5; i++) {       // 5
            e.add(ce.creNormalEnemy());
        }
        for (int i=0; i<3; i++) {       // 3
            e.add(ce.creTankerEnemy());
        }
        for (int i=0; i<3; i++) {       // 3
            e.add(ce.creSmallerEnemy());
        }
        for (int i=0; i<3; i++) {       // 3*5 = 15
            e.add(ce.creNormalEnemy());
            e.add(ce.creNormalEnemy());
            e.add(ce.creTankerEnemy());
            e.add(ce.creSmallerEnemy());
            e.add(ce.creSmallerEnemy());
        }
        for (int i=0; i<5; i++) {       // 5*6 = 30     // Tổng 56
            e.add(ce.creBossEnemy());
            e.add(ce.creNormalEnemy());
            e.add(ce.creNormalEnemy());
            e.add(ce.creTankerEnemy());
            e.add(ce.creSmallerEnemy());
            e.add(ce.creSmallerEnemy());
        }
        return e;
    }



}
