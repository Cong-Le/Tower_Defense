package main;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bullet extends BulletObject {
    Image BulletImg;
    Direction direction;

    @Override
    void render(GraphicsContext gc) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        ImageView iv = new ImageView(BulletImg);
        //iv.setRotate(this.direction.getDegree());
        iv.setFitWidth(10);
        iv.setFitHeight(10);
        Image bullet = iv.snapshot(params, null);

        gc.drawImage(bullet, x, y);
    }

    /*void calculateDirection() {     // Tính hướng đi tiếp theo cho Object
        if (x==xDes && y==yDes) {       // Enemy đến điểm cuối
            return;
        }

        if (Road.distance(x, y, xDes, yDes) <= speed) {
            x = xDes;
            y = yDes;
            System.out.println(x + " " + y);

            double deltaX = nextWayPoint.x - x;
            double deltaY = nextWayPoint.y - y;
            if (deltaX > speed) direction = Direction.RIGHT;
            else if (deltaX < -speed) direction = Direction.LEFT;   // Ideal : Trả về góc phụ thuộc vào tỉ lệ xDes-x và yDes-y
            else if (deltaY > speed) direction = Direction.DOWN;    // ...sử dụng góc đấy để quay bullet
            else if (deltaY <= -speed) direction = Direction.UP;
        }
    }*/
    @Override
    void update() {


        x += Math.round( (double)(xDes-x)*speed/Math.abs(xDes-x + yDes-y) );
        y += Math.round( (double)(yDes-y)*speed/Math.abs(xDes-x + yDes-y) );

        if (Math.abs(x-xDes + y-yDes) < 20)  hit = true;
        //System.out.println(x + "  " + y);
       // System.out.println(xDes + "  " + yDes);
       // System.out.println("= " + Math.abs(x-xDes + y-yDes) );

        //System.out.println(x + " " +y);

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
    public Bullet creNormalBullet(int x, int y, int xDes, int yDes) {
        Bullet bullet = new Bullet();
        bullet.x = x+30; bullet.y = y+15; // Điểm bắt đầu
        bullet.xDes = xDes; bullet.yDes = yDes; // Điểm đến
        bullet.ID = Config.NorT_ID;
        bullet.speed = Config.NorB_Speed;
        bullet.damage = Config.NorT_Damage;
        bullet.range = Config.NorT_Range;
        bullet.BulletImg = new Image("file:src/Image/Bullet.png");
        return bullet;
    }
    public Bullet creSniperBullet(int x, int y, int xDes, int yDes) {
        Bullet bullet = new Bullet();
        bullet.x = x+30; bullet.y = y+15; // Điểm bắt đầu
        bullet.xDes = xDes; bullet.yDes = yDes; // Điểm đến
        bullet.ID = Config.SniT_ID;
        bullet.speed = Config.SniB_Speed;
        bullet.damage = Config.NorT_Damage;
        bullet.range = Config.NorT_Range;
        bullet.BulletImg = new Image("file:src/Image/Bullet.png");
        return bullet;
    }
    public Bullet creMachineBullet(int x, int y, int xDes, int yDes) {
        Bullet bullet = new Bullet();
        bullet.x = x+30; bullet.y = y+15; // Điểm bắt đầu
        bullet.xDes = xDes; bullet.yDes = yDes; // Điểm đến
        bullet.ID = Config.MacT_ID;
        bullet.speed = Config.MacB_Speed;
        bullet.damage = Config.NorT_Damage;
        bullet.range = Config.NorT_Range;
        bullet.BulletImg = new Image("file:src/Image/Bullet.png");
        return bullet;
    }
}

