package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

abstract class GameObject {
    int x, y;

    abstract void render(GraphicsContext gc);
    abstract void update();
}
abstract class EnemyObject extends GameObject {
    double x = Config.Spawner_x;   // Điểm sinh quân địch
    double y = Config.Spawner_y;
    int ID;
    public int health;
    public int reward;
    public double speed;
    boolean pass = false;   // Địch đã đến điểm kết thúc hay chưa
    Image EnemyImg;
    Direction direction;
    int healthSpace;
}
abstract class TowerObject extends GameObject {
    int ID;
    public int damage;
    public int price;
    public double range;
    public double spaw;
    Image TowerImg;
    List<BulletObject> bulletofTower = new ArrayList<>();   // List lưu các bullet hiện tại của Tower
}
abstract class BulletObject extends GameObject {
    boolean hit = false;    // Bullet đã trúng địch hay chưa
    int ID;
    public int damage;
    public double speed;
    public double range;
    Image BulletImg;
    Direction direction;
    EnemyObject enemy;
}
