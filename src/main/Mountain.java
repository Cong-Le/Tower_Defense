package main;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

class Mountain {
    private Button button;
    private int x;
    private int y;
    private boolean empty;  // Vị trí đã đặt tháp hay chưa

    public Button getButton() {
        return button;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public boolean getEmpty() {
        return this.empty;
    }
    public boolean setEmpty(boolean empty) {
        return this.empty = empty;
    }

    public static List<Mountain> mountainsSmall() {       // List button để hiện store khi kéo chuột vào (vị trí các ô có thể đặt tháp)
        List<Mountain> mountains = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Mountain m = new Mountain();
            m.button = new Button();
            m.button.setLayoutX(MountainPoints[i].getX() + 3);
            m.button.setLayoutY(MountainPoints[i].getY() + 3);
            m.button.setPrefSize(60, 25);
            m.button.setOpacity(0);       ///Chỉnh độ mờ để check vị trí
            m.x = MountainPoints[i].getX() - 7;   // set tọa độ tâm button
            m.y = MountainPoints[i].getY() - 45;
            m.empty = true;
            mountains.add(m);
        }
        return mountains;
    }
    public static List<Mountain> mountainsBig() {     // List button để ẩn store sau khi kéo chuột ra
        List<Mountain> mountains = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Mountain m = new Mountain();
            m.button = new Button();
            m.button.setLayoutX(MountainPoints[i].getX() -48);
            m.button.setLayoutY(MountainPoints[i].getY() -63);
            m.button.setPrefSize(160, 130);
            m.button.setOpacity(0);     ///Chỉnh độ mờ để check vị trí
            m.x = MountainPoints[i].getX() -7;
            m.y = MountainPoints[i].getY() - 45;
            mountains.add(m);
        }
        return mountains;
    }
    public static List<Mountain> StoreButton(int x, int y) {     // List button chọn mua tháp
        List<Mountain> storeButton = new ArrayList<>();
        for (int i=0; i<3; i++) {
            Mountain m = new Mountain();
            m.button = new Button();
            m.button.setPrefSize(37, 40);
            m.button.setOpacity(0);     //Chỉnh độ mờ để check vị trí
            //m.x = MountainPoints[i].x - 7;
            //m.y = MountainPoints[i].y - 45;
            storeButton.add(m);
        }
        storeButton.get(0).button.setLayoutX(x - 12); storeButton.get(0).button.setLayoutY(y + 52);
        storeButton.get(1).button.setLayoutX(x + 22); storeButton.get(1).button.setLayoutY(y + 7);
        storeButton.get(2).button.setLayoutX(x + 56); storeButton.get(2).button.setLayoutY(y + 52);

        return storeButton;
    }

    public static final Point[] MountainPoints = new Point[] {   //Những điểm có thể đặt tháp
            new Point(126, 222),
            new Point(253, 372),
            new Point(254, 526),
            new Point(290, 222),
            new Point(406, 161),
            new Point(578, 164),
            new Point(543, 289),
            new Point(546, 380),
            new Point(714, 376),
            new Point(820, 376),
    };

}
