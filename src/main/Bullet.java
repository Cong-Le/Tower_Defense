package main;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bullet extends BulletObject {
    int Deg;

    @Override
    void render(GraphicsContext gc) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        ImageView iv = new ImageView(BulletImg);
        iv.setRotate(Deg);
        //iv.setFitWidth(10);
        //iv.setFitHeight(35);
        Image bImg = iv.snapshot(params, null);

        gc.drawImage(bImg, x, y);
    }

    @Override
    void update() {
        if (Math.abs(x-enemy.x + y-enemy.y) < 20)  hit = true;

        x += Math.round( (double)(enemy.x-x)*speed/(Math.abs(enemy.x-x)+ Math.abs(enemy.y-y)) );
        y += Math.round( (double)(enemy.y-y)*speed/(Math.abs(enemy.x-x)+ Math.abs(enemy.y-y)) );

        Deg = (int) Math.toDegrees( Math.atan2(enemy.y-y, enemy.x-x) ) ;
        if (Deg < 0)    Deg += 360;


        /*calculateDirection();

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
        }**/
    }
}
class CreateBullet extends Bullet {
    public Bullet creNormalBullet(int x, int y, EnemyObject enemy) {
        Bullet bullet = new Bullet();
        bullet.x = x+35; bullet.y = y; // Điểm bắt đầu
       // bullet.xDes = xDes; bullet.yDes = yDes; // Điểm đến
        bullet.enemy = enemy;
        bullet.ID = Config.NorT_ID;
        bullet.speed = Config.NorB_Speed;
        bullet.damage = Config.NorT_Damage;
        bullet.range = Config.NorT_Range;
        bullet.BulletImg = new Image("file:src/Image/Bullet_Normal3.png");
        return bullet;
    }
    public Bullet creSniperBullet(int x, int y, EnemyObject enemy) {
        Bullet bullet = new Bullet();
        bullet.x = x+35; bullet.y = y; // Điểm bắt đầu
        //bullet.xDes = xDes; bullet.yDes = yDes; // Điểm đến
        bullet.enemy = enemy;
        bullet.ID = Config.SniT_ID;
        bullet.speed = Config.SniB_Speed;
        bullet.damage = Config.NorT_Damage;
        bullet.range = Config.NorT_Range;
        bullet.BulletImg = new Image("file:src/Image/Bullet_Sniper3.png");
        return bullet;
    }
    public Bullet creMachineBullet(int x, int y, EnemyObject enemy) {
        Bullet bullet = new Bullet();
        bullet.x = x+35; bullet.y = y; // Điểm bắt đầu
        //bullet.xDes = xDes; bullet.yDes = yDes; // Điểm đến
        bullet.enemy = enemy;
        bullet.ID = Config.MacT_ID;
        bullet.speed = Config.MacB_Speed;
        bullet.damage = Config.NorT_Damage;
        bullet.range = Config.NorT_Range;
        bullet.BulletImg = new Image("file:src/Image/Bullet_Machine3.png");
        return bullet;
    }
}

