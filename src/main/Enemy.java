package main;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

class Enemy extends EnemyObject {


    int wayPointIndex = 0;
    public Point getNextWayPoint() {  //Trả về điểm tiếp theo trên đường
        if (wayPointIndex < Road.wayPoints.length - 1)
            return Road.wayPoints[++wayPointIndex];
        return null;
    }

    public Point getPresentCoordinates(EnemyObject e) {  // Trả về điểm hiện tại của Enemy
        return new Point((int)e.x, (int)e.y);
    }

    @Override
    void render(GraphicsContext gc) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        ImageView iv = new ImageView(EnemyImg);
        //iv.setRotate(this.direction.getDegree());
        //iv.setFitWidth(30);
        //iv.setFitHeight(30);
        Image enemy = iv.snapshot(params, null);

        gc.drawImage(enemy, x, y);


       /* gc.setFill(Color.RED);
        gc.fillOval(Road.wayPoints[wayPointIndex].x, Road.wayPoints[wayPointIndex].y, 10, 10);
        gc.setFill(Color.BLUE);
        gc.fillOval(x, y, 10, 10);*/
    }
    void calculateDirection() {     // Tính hướng đi tiếp theo cho Object
        if (wayPointIndex >= Road.wayPoints.length) {       // Enemy đến điểm cuối
            return;
        }

        Point currentWP = Road.wayPoints[wayPointIndex];
        if (Road.distance(x, y, currentWP.x, currentWP.y) <= speed) {
            x = currentWP.x;
            y = currentWP.y;

            //System.out.println(x + " " + y);

            Point nextWayPoint = getNextWayPoint();
            if (nextWayPoint == null) return;
            double deltaX = nextWayPoint.x - x;
            double deltaY = nextWayPoint.y - y;
            if (deltaX > speed) direction = Direction.RIGHT;
            else if (deltaX < -speed) direction = Direction.LEFT;
            else if (deltaY > speed) direction = Direction.DOWN;
            else if (deltaY <= -speed) direction = Direction.UP;
        }
    }
    @Override
    void update() {
        calculateDirection();       // Có thể chỉnh sửa để Enemy đi được theo đường chéo (Dựa vào code Bullet)

        switch (direction) {
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
        }

        if (x >= 1020) pass = true; // Kiểm tra địch đã đến điểm kết thúc
    }
}
class CreateEnemy extends Enemy {
    public Enemy creNormalEnemy() {
        Enemy enemy = new Enemy();
        enemy.health = Config.NorE_Health;
        enemy.speed = Config.NorE_Speed;
        enemy.reward = Config.NorE_Reward;
        enemy.direction = Direction.UP;
        enemy.EnemyImg = new Image("file:src/Image/Enemy_Normal2.png");
        return enemy;
    }
    public Enemy creSmallerEnemy() {
        Enemy enemy = new Enemy();
        enemy.health = Config.SmaE_Health;
        enemy.speed = Config.SmaE_Speed;
        enemy.reward = Config.SmaE_Reward;
        enemy.direction = Direction.UP;
        enemy.EnemyImg = new Image("file:src/Image/Enemy_Smaller2.png");
        return enemy;
    }
    public Enemy creTankerEnemy() {
        Enemy enemy = new Enemy();
        enemy.health = Config.TanE_Health;
        enemy.speed = Config.TanE_Speed;
        enemy.reward = Config.TanE_Reward;
        enemy.direction = Direction.UP;
        enemy.EnemyImg = new Image("file:src/Image/Enemy_Tanker2.png");
        return enemy;
    }
    public Enemy creBossEnemy() {
        Enemy enemy = new Enemy();
        enemy.health = Config.BosE_Health;
        enemy.speed = Config.BosE_Speed;
        enemy.reward = Config.BosE_Reward;
        enemy.direction = Direction.UP;
        enemy.EnemyImg = new Image("file:src/Image/Enemy_Boss2.png");
        return enemy;
    }
}
