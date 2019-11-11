package main;

public class Road {
    public static final Point[] wayPoints = new Point[] {   //Những điểm trên đường đi
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

    public static double distance(double x1, double y1, double x2, double y2) { //Tính khoảng cách giữa 2 điểm
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public int wayPointIndex = 0;
    public Point getNextWayPoint() {  //Trả về điểm tiếp theo trên đường đi [1,...,10]
        if (wayPointIndex < Road.wayPoints.length - 1)
            return Road.wayPoints[++wayPointIndex];
        return null;
    }
}
