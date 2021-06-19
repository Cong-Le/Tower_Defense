package main;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


class Enemy extends EnemyObject {
    private int wayPointIndex = 0;
    private Point getNextWayPoint() {  //Trả về điểm tiếp theo trên đường
        if (wayPointIndex < Road.wayPoints.length - 1)
            return Road.wayPoints[++wayPointIndex];
        return null;
    }

    Point getPresentCoordinates(EnemyObject e) {  // Trả về điểm hiện tại của Enemy
        return new Point((int)e.getX(), (int)e.getY());
    }

    @Override
    void render(GraphicsContext gc) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        ImageView iv = new ImageView(getEnemyImg());
        //iv.setRotate(this.direction.getDegree());
        Image eImg = iv.snapshot(params, null);
        gc.drawImage(eImg, getX(), getY());

        // Health Bar
        gc.setFill(Color.rgb(0, 200, 0));
        gc.fillRect(getX(), getY()-5, getHealth()*22.0/getHealthSpace(), 2);
        gc.setFill(Color.rgb(200, 0, 0));
        gc.fillRect(getX() + getHealth()*22.0/getHealthSpace(), getY()-5, 22-getHealth()*22.0/getHealthSpace(), 2);

    }
    private void calculateDirection() {     // Tính hướng đi tiếp theo cho Object
        if (wayPointIndex >= Road.wayPoints.length) {       // Enemy đến điểm cuối
            return;
        }

        Point currentWP = Road.wayPoints[wayPointIndex];
        if (Road.distance(getX(), getY(), currentWP.getX(), currentWP.getY()) <= getSpeed()) {
            setX(currentWP.getX());
            setY(currentWP.getY());

            Point nextWayPoint = getNextWayPoint();
            if (nextWayPoint == null) return;
            double deltaX = nextWayPoint.getX() - getX();
            double deltaY = nextWayPoint.getY() - getY();
            if (deltaX > getSpeed()) setDirection(Direction.RIGHT);
            else if (deltaX < -getSpeed()) setDirection(Direction.LEFT);
            else if (deltaY > getSpeed()) setDirection(Direction.DOWN);
            else if (deltaY <= -getSpeed()) setDirection(Direction.UP);
        }
    }
    @Override
    void update() {
        calculateDirection();       // Có thể chỉnh sửa để Enemy đi được theo đường chéo (Dựa vào code Bullet)

        switch (getDirection()) {
            case UP:
                setY(getY() - getSpeed());
                break;
            case DOWN:
                setY(getY() + getSpeed());
                break;
            case LEFT:
                setX(getX() - getSpeed());
                break;
            case RIGHT:
                setX(getX() + getSpeed());
                break;
        }

        if (getX() >= 1020) setPass(true); // Kiểm tra địch đã đến điểm kết thúc
    }
}
class CreateEnemy extends Enemy {
    Enemy creNormalEnemy() {
        Enemy enemy = new Enemy();
        enemy.setHealth(Config.NorE_Health);
        enemy.setHealthSpace(Config.NorE_Health);
        enemy.setSpeed(Config.NorE_Speed);
        enemy.setReward(Config.NorE_Reward);
        enemy.setDirection(Direction.UP);
        enemy.setEnemyImg(new Image("file:src/Image/Enemy_Normal2.png"));
        return enemy;
    }
    Enemy creSmallerEnemy() {
        Enemy enemy = new Enemy();
        enemy.setHealth(Config.SmaE_Health);
        enemy.setHealthSpace(Config.SmaE_Health);
        enemy.setSpeed(Config.SmaE_Speed);
        enemy.setReward(Config.SmaE_Reward);
        enemy.setDirection(Direction.UP);
        enemy.setEnemyImg(new Image("file:src/Image/Enemy_Smaller2.png"));
        return enemy;
    }
    Enemy creTankerEnemy() {
        Enemy enemy = new Enemy();
        enemy.setHealth(Config.TanE_Health);
        enemy.setHealthSpace(Config.TanE_Health);
        enemy.setSpeed(Config.TanE_Speed);
        enemy.setReward(Config.TanE_Reward);
        enemy.setDirection(Direction.UP);
        enemy.setEnemyImg(new Image("file:src/Image/Enemy_Tanker3.png"));
        return enemy;
    }
    Enemy creBossEnemy() {
        Enemy enemy = new Enemy();
        enemy.setHealth(Config.BosE_Health);
        enemy.setHealthSpace(Config.BosE_Health);
        enemy.setSpeed(Config.BosE_Speed);
        enemy.setReward(Config.BosE_Reward);
        enemy.setDirection(Direction.UP);
        enemy.setEnemyImg(new Image("file:src/Image/Enemy_Boss2.png"));
        return enemy;
    }
}
