package main;

public enum Direction {
    LEFT(180), UP(270), RIGHT(0), DOWN(90);

    int degree;

    Direction(int i) {
        degree = i;
    }

    int getDegree() {
        return degree;
    }
}

