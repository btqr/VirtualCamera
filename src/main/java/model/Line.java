package model;

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
}
