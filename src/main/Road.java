package main;

public class Road {
    //Những điểm trên đường đi
    public static final Point[] wayPoints = new Point[] {
            new Point(0, 457),
            new Point(347, 457),
            new Point(347, 289),
            new Point(222, 289),
            new Point(222, 98),
            new Point(512, 98),
            new Point(512, 222),
            new Point(645, 222),
            new Point(645, 437),
            new Point(1020, 437),
    };

    //Tính khoảng cách giữa 2 điểm
    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

}
