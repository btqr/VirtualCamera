package model;

import configuration.Configuration;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class Door implements Drawable{
    private List<Line> lineList;
    private java.util.List<Point> pointList;
    private double distanceFromCamera;
    private double distanceBetweenPoints;

    public Door() {
        this.lineList = new ArrayList<Line>();
        this.pointList = new ArrayList<Point>();
        this.distanceFromCamera = Configuration.DISTANCE_FROM_CAMERA;
        this.distanceBetweenPoints = 150;
        init();
    }

    public void init() {
        double d = distanceBetweenPoints/2.0;

        pointList.add(new Point(-0.5*d, -d, distanceFromCamera+d));
        pointList.add(new Point(0.5*d, -d, distanceFromCamera+d));
        pointList.add(new Point(-0.5*d, 20, distanceFromCamera+d));
        pointList.add(new Point(0.5*d, 20, distanceFromCamera+d));

        pointList.add(new Point(20, -25, distanceFromCamera+d));
        pointList.add(new Point(28, -25, distanceFromCamera+d));
        pointList.add(new Point(20, -20, distanceFromCamera+d));
        pointList.add(new Point(28, -20, distanceFromCamera+d));

        //down+up
        lineList.add(new Line(pointList.get(0), pointList.get(1),Color.BLUE));
        lineList.add(new Line(pointList.get(0), pointList.get(2),Color.BLUE));
        lineList.add(new Line(pointList.get(1), pointList.get(3),Color.BLUE));
        lineList.add(new Line(pointList.get(2), pointList.get(3),Color.BLUE));

        lineList.add(new Line(pointList.get(4), pointList.get(5),Color.BLUE));
        lineList.add(new Line(pointList.get(4), pointList.get(6),Color.BLUE));
        lineList.add(new Line(pointList.get(5), pointList.get(7),Color.BLUE));
        lineList.add(new Line(pointList.get(6), pointList.get(7),Color.BLUE));
    }
}
