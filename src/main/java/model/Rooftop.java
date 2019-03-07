package model;

import configuration.Configuration;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Rooftop implements Drawable{
    private List<Line> lineList;
    private List<Point> pointList;
    private double distanceFromCamera;
    private double distanceBetweenPoints;

    public Rooftop() {
        this.lineList = new ArrayList<Line>();
        this.pointList = new ArrayList<Point>();
        this.distanceFromCamera = Configuration.DISTANCE_FROM_CAMERA;
        this.distanceBetweenPoints = 150;
        init();
    }

    public void init() {
        double d = distanceBetweenPoints/2.0;

        pointList.add(new Point(-2*d, d, distanceFromCamera+d));
        pointList.add(new Point(2*d, d, distanceFromCamera+d));
        pointList.add(new Point(-2*d, d, distanceFromCamera+3*d));
        pointList.add(new Point(2*d, d, distanceFromCamera+3*d));

        pointList.add(new Point(0, d+100, distanceFromCamera+d));
        pointList.add(new Point(0, d+100, distanceFromCamera+3*d));

        lineList.add(new Line(pointList.get(0), pointList.get(1)));
        lineList.add(new Line(pointList.get(0), pointList.get(2)));
        lineList.add(new Line(pointList.get(1), pointList.get(3)));
        lineList.add(new Line(pointList.get(2), pointList.get(3)));

        lineList.add(new Line(pointList.get(0), pointList.get(4)));
        lineList.add(new Line(pointList.get(1), pointList.get(4)));
        lineList.add(new Line(pointList.get(2), pointList.get(5)));
        lineList.add(new Line(pointList.get(3), pointList.get(5)));

        lineList.add(new Line(pointList.get(4), pointList.get(5)));
    }

}
