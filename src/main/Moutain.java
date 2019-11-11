package main;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class Moutain {
    Button button;
    int x;
    int y;
    boolean empty;  // Vị trí đã đặt tháp hay chưa

    public static List<Moutain> moutainsSmall() {       // button để hiện store khi kéo chuột vào (vị trí các ô có thể đặt tháp)
        List<Moutain> moutains = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Moutain m = new Moutain();
            m.button = new Button();
            m.button.setLayoutX(MoutainPoints[i].x + 3);
            m.button.setLayoutY(MoutainPoints[i].y + 3);
            m.button.setPrefSize(60, 25);
            m.button.setOpacity(0);       ///Chỉnh độ mờ để check vị trí
            m.x = MoutainPoints[i].x - 7;
            m.y = MoutainPoints[i].y - 45;
            m.empty = true;
            moutains.add(m);
        }
        return moutains;
    }
    public static List<Moutain> moutainsBig() {     // button để ẩn store sau khi kéo chuột ra
        List<Moutain> moutains = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Moutain m = new Moutain();
            m.button = new Button();
            m.button.setLayoutX(MoutainPoints[i].x -33);
            m.button.setLayoutY(MoutainPoints[i].y -53);
            m.button.setPrefSize(130, 110);
            m.button.setOpacity(0);     ///Chỉnh độ mờ để check vị trí
            m.x = MoutainPoints[i].x -7;
            m.y = MoutainPoints[i].y - 45;
            moutains.add(m);
        }
        return moutains;
    }
    public static List<Moutain> StoreButton(int x, int y) {     // button chọn mua tháp
        List<Moutain> storeButton = new ArrayList<>();
        for (int i=0; i<3; i++) {
            Moutain m = new Moutain();
            m.button = new Button();
            m.button.setPrefSize(37, 40);
            m.button.setOpacity(0);     //Chỉnh độ mờ để check vị trí
            //m.x = MoutainPoints[i].x - 7;
            //m.y = MoutainPoints[i].y - 45;
            storeButton.add(m);
        }
        storeButton.get(0).button.setLayoutX(x - 12); storeButton.get(0).button.setLayoutY(y + 52);
        storeButton.get(1).button.setLayoutX(x + 22); storeButton.get(1).button.setLayoutY(y + 1);
        storeButton.get(2).button.setLayoutX(x + 56); storeButton.get(2).button.setLayoutY(y + 52);

        return storeButton;
    }

    public static final Point[] MoutainPoints = new Point[] {   //Những điểm có thể đặt tháp
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
