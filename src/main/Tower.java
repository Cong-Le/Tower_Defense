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

        ImageView iv = new ImageView(TowerImg);
        //iv.setRotate(this.direction.getDegree());
        iv.setFitWidth(65);
        iv.setFitHeight(75);
        Image tower = iv.snapshot(params, null);

        gc.drawImage(tower, x+7, y);
    }

    @Override
    void update() {
    }
}

class CreateTower extends Tower {
    public Tower creNormalTower(int x, int y) {
        Tower tower = new Tower();
        tower.x = x;    tower.y = y;
        tower.ID = Config.NorT_ID;
        tower.damage = Config.NorT_Damage;
        tower.range = Config.NorT_Range;
        tower.spaw = Config.NorT_Spaw;
        tower.price = Config.NorT_Price;
        tower.TowerImg = new Image("file:src/Image/Tower_Normal.png");
        return tower;
    }
    public Tower creSniperTower(int x, int y) {
        Tower tower = new Tower();
        tower.x = x;    tower.y = y-3;
        tower.ID = Config.SniT_ID;
        tower.damage = Config.SniT_Damage;
        tower.range = Config.SniT_Range;
        tower.spaw = Config.SniT_Spaw;
        tower.price = Config.SniT_Price;
        tower.TowerImg = new Image("file:src/Image/Tower_Sniper.png");
        return tower;
    }
    public Tower creMachineTower(int x, int y) {
        Tower tower = new Tower();
        tower.x = x;    tower.y = y;
        tower.ID = Config.MacT_ID;
        tower.damage = Config.MacT_Damage;
        tower.range = Config.MacT_Range;
        tower.spaw = Config.MacT_Spaw;
        tower.price = Config.MacT_Price;
        tower.TowerImg = new Image("file:src/Image/Tower_Machine.png");
        return tower;
    }
}
