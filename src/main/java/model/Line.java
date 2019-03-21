package model;

import configuration.Configuration;
import lombok.Data;

import java.awt.*;

@Data
public class Line {
    private Point a,b;
    private Color color;

    public Line(Point a, Point b) {
        this.a = a;
        this.b = b;
        this.color = Color.BLACK;
    }

    public Line(Point a, Point b, Color color) {
        this.a = a;
        this.b = b;
        this.color = color;
    }

    public Line trimLine() {
        if(getA().getZ() * getB().getZ() < 0) {
            double dx = getB().getX() - getA().getX();
            double dy = getB().getY() - getA().getY();
            double dz = getB().getZ() - getA().getZ();
            double y = -dy * (getA().getZ() - 1) / dz + getA().getY();
            double x = -dx * (getA().getZ() - 1) / dz + getA().getX();
            if (getA().getZ() < 0) {
                return new Line(new Point(x, y, 0), getB(), getColor());
            } else {
                return new Line(getA(), new Point(x, y, 0), getColor());
            }
        }
        return this;
    }

    public Line scaleLine(double scale) {
        double x1 = getA().getX() * scale;
        double y1 = getA().getY() * scale;
        double z1 = getA().getZ();
        double x2 = getB().getX() * scale;
        double y2 = getB().getY() * scale;
        double z2 = getB().getZ();
        return new Line(new Point(x1, y1, z1), new Point(x2, y2, z2), getColor());
    }

    public Line projectTo2D() {
        double distanceFromCamera = Configuration.DISTANCE_FROM_CAMERA;
        double x1 = getA().getX()/getA().getZ() * distanceFromCamera;
        double y1 = getA().getY()/getA().getZ() * distanceFromCamera;
        double z1 = getA().getZ();
        double x2 = getB().getX()/getB().getZ() * distanceFromCamera;
        double y2 = getB().getY()/getB().getZ() * distanceFromCamera;
        double z2 = getB().getZ();
        return new Line(new Point(x1, y1, z1), new Point(x2, y2, z2), getColor());
    }

    public Line moveToCenter() {
        double screenWidth = Configuration.SCREEN_WIDTH;
        double screenHeight = Configuration.SCREEN_HEIGHT;
        double x1 = getA().getX() + screenWidth/2.0;
        double y1 = getA().getY() + screenHeight/2.0;
        double z1 = getA().getZ();
        double x2 = getB().getX() + screenWidth/2.0;
        double y2 = getB().getY() + screenHeight/2.0;
        double z2 = getB().getZ();
        return new Line(new Point(x1, y1, z1), new Point(x2, y2, z2), getColor());
    }

    public Line revertCoordinates() {
        double screenHeight = Configuration.SCREEN_HEIGHT;
        double x1 = getA().getX();
        double y1 = screenHeight - getA().getY();
        double z1 = getA().getZ();
        double x2 = getB().getX();
        double y2 = screenHeight - getB().getY();
        double z2 = getB().getZ();
        return new Line(new Point(x1, y1, z1), new Point(x2, y2, z2), getColor());
    }
}
