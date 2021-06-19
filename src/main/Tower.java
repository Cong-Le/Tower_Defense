package main;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Tower extends TowerObject {


    @Override
    void render(GraphicsContext gc) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        ImageView iv = new ImageView(getTowerImg());
        iv.setFitWidth(65);
        iv.setFitHeight(75);
        Image tImg = iv.snapshot(params, null);
        gc.drawImage(tImg, getX()+8, getY()+3);

        //gc.setFill(Color.RED);
        //gc.fillOval(x, y,  10, 10);
    }

    @Override
    void update() {
    }
}

class CreateTower extends Tower {
    public Tower creNormalTower(int x, int y) {
        Tower tower = new Tower();
        tower.setX(x);    tower.setY(y);
        tower.setID(Config.NorT_ID);
        tower.setDamage(Config.NorT_Damage);
        tower.setRange(Config.NorT_Range);
        tower.setSpaw(Config.NorT_Spaw);
        tower.setPrice(Config.NorT_Price);
        tower.setTowerImg(new Image("file:src/Image/Tower_Normal.png"));
        return tower;
    }
    public Tower creSniperTower(int x, int y) {
        Tower tower = new Tower();
        tower.setX(x);    tower.setY(y-5);
        tower.setID(Config.SniT_ID);
        tower.setDamage(Config.SniT_Damage);
        tower.setRange(Config.SniT_Range);
        tower.setSpaw(Config.SniT_Spaw);
        tower.setPrice(Config.SniT_Price);
        tower.setTowerImg(new Image("file:src/Image/Tower_Sniper.png"));
        return tower;
    }
    public Tower creMachineTower(int x, int y) {
        Tower tower = new Tower();
        tower.setX(x);    tower.setY(y);
        tower.setID(Config.MacT_ID);
        tower.setDamage(Config.MacT_Damage);
        tower.setRange(Config.MacT_Range);
        tower.setSpaw(Config.MacT_Spaw);
        tower.setPrice(Config.MacT_Price);
        tower.setTowerImg(new Image("file:src/Image/Tower_Machine.png"));
        return tower;
    }
}
