package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

abstract class GameObject {

    abstract void render(GraphicsContext gc);
    abstract void update();

}
abstract class EnemyObject extends GameObject {
    private int x = Config.Spawner_x;   // Điểm sinh quân địch
    private int y = Config.Spawner_y;
    private int ID;
    private int health;
    private int healthSpace;
    private int reward;
    private int speed;
    private boolean pass = false;   // Địch đã đến điểm kết thúc hay chưa
    private Image EnemyImg;
    private Direction direction;



    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }
    public int getReward() {
        return reward;
    }
    public int getSpeed() {
        return speed;
    }
    public boolean getPass() {
        return pass;
    }
    public Image getEnemyImg() {
        return EnemyImg;
    }
    public Direction getDirection() {
        return direction;
    }

    public int getHealthSpace() {
        return this.healthSpace;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void setReward(int reward) {
        this.reward = reward;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public void setEnemyImg(Image enemyImg) {
        EnemyImg = enemyImg;
    }
    public void setHealthSpace(int healthSpace) {
        this.healthSpace = healthSpace;
    }
}
abstract class TowerObject extends GameObject {
    private int x, y; //
    private int ID;
    private int damage;
    private int price;
    private double range;
    private double spaw;
    private Image TowerImg;
    List<BulletObject> bulletofTower = new ArrayList<>();   // List lưu các bullet hiện tại của Tower

    public List<BulletObject> getBulletofTower() {
        return this.bulletofTower;
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getID() {
        return this.ID;
    }
    public double getDamage() {
        return this.damage;
    }
    public int getPrice() {
        return this.price;
    }
    public double getRange() {
        return this.range;
    }
    public double getSpaw() {
        return this.spaw;
    }
    public Image getTowerImg() {
        return TowerImg;
    }

    public int setID(int ID) {
        return this.ID = ID;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setRange(double range) {
        this.range = range;
    }
    public void setSpaw(double spaw) {
        this.spaw = spaw;
    }
    public void setTowerImg(Image towerImg) {
        TowerImg = towerImg;
    }
}
abstract class BulletObject extends GameObject {
    private int x, y;
    private boolean hit = false;    // Đạn đã trúng địch hay chưa
    private int ID;
    private int damage;
    public double speed;
    private double range;
    private Direction direction;
    private Image BulletImg;
    private EnemyObject enemy;

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public boolean getHit() {
        return this.hit;
    }
    public int getID() {
        return this.ID;
    }
    public double getDamage() {
        return this.damage;
    }
    public double getSpeed() {
        return this.speed;
    }
    public Image getBulletImg() {
        return BulletImg;
    }
    public EnemyObject getEnemy() {
        return enemy;
    }

    public int setID(int ID) {
        return this.ID = ID;
    }
    public void setHit(boolean hit) {
        this.hit = hit;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public void setRange(double range) {
        this.range = range;
    }
    public void setBulletImg(Image bulletImg) {
        BulletImg = bulletImg;
    }
    public void setEnemy(EnemyObject enemy) {
        this.enemy = enemy;
    }
}
