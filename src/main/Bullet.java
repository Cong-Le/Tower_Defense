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
        ImageView iv = new ImageView(getBulletImg());
        iv.setRotate(Deg);
        //iv.setFitWidth(10);
        //iv.setFitHeight(35);
        Image bImg = iv.snapshot(params, null);

        gc.drawImage(bImg, getX(), getY());
    }

    @Override
    void update() {
        // Đạn đến gần địch -> trúng/xóa
        if (Math.abs(getX()-getEnemy().getX() + getY()-getEnemy().getY()) < 20)  setHit(true);

        setX(getX() + (int) Math.round( (float) (getEnemy().getX()-getX())*speed/(Math.abs(getEnemy().getX()-getX())+ Math.abs(getEnemy().getY()-getY())) ));
        setY(getY() + (int) Math.round( (double)(getEnemy().getY()-getY())*speed/(Math.abs(getEnemy().getX()-getX())+ Math.abs(getEnemy().getY()-getY())) ));

        // Góc quay của đạn
        Deg = (int) Math.toDegrees( Math.atan2(getEnemy().getY()-getY(), getEnemy().getX()-getX()) ) ;
        if (Deg < 0)    Deg += 360;

    }
}
class CreateBullet extends Bullet {
    public Bullet creNormalBullet(int x, int y, EnemyObject enemy) {
        Bullet bullet = new Bullet();
        bullet.setX(x+35); bullet.setY(y); // Điểm bắt đầu
        bullet.setEnemy(enemy);
        bullet.setID(Config.NorT_ID);
        bullet.setSpeed(Config.NorB_Speed);
        bullet.setDamage(Config.NorT_Damage);
        bullet.setRange(Config.NorT_Range);
        bullet.setBulletImg(new Image("file:src/Image/Bullet_Normal3.png"));
        return bullet;
    }
    public Bullet creSniperBullet(int x, int y, EnemyObject enemy) {
        Bullet bullet = new Bullet();
        bullet.setX(x+35); bullet.setY(y); // Điểm bắt đầu
        bullet.setEnemy(enemy);
        bullet.setID(Config.SniT_ID);
        bullet.setSpeed(Config.SniB_Speed);
        bullet.setDamage(Config.NorT_Damage);
        bullet.setRange(Config.NorT_Range);
        bullet.setBulletImg(new Image("file:src/Image/Bullet_Sniper3.png"));
        return bullet;
    }
    public Bullet creMachineBullet(int x, int y, EnemyObject enemy) {
        Bullet bullet = new Bullet();
        bullet.setX(x+35); bullet.setY(y); // Điểm bắt đầu
        bullet.setEnemy(enemy);
        bullet.setID(Config.MacT_ID);
        bullet.setSpeed(Config.MacB_Speed);
        bullet.setDamage(Config.NorT_Damage);
        bullet.setRange(Config.NorT_Range);
        bullet.setBulletImg(new Image("file:src/Image/Bullet_Machine3.png"));
        return bullet;
    }
}

