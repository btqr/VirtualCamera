package data;

import configuration.Configuration;
import lombok.Data;
import model.Drawable;
import model.Line;
import model.Point;
import model.Polygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class Door implements Drawable {

    private List<Line> lineList;
    private List<Point> pointList;
    private List<Polygon> polygonList;

    private double distanceFromCamera;
    private double distanceBetweenPoints;

    public Door() {
        this.lineList = new ArrayList<Line>();
        this.pointList = new ArrayList<Point>();
        this.polygonList = new ArrayList<Polygon>();
        this.distanceFromCamera = Configuration.DISTANCE_FROM_CAMERA;
        this.distanceBetweenPoints = 150;
        init();
    }

    public void init() {
        initPoints();
        initLines();
        initPolygons();
    }

    private void initPoints() {
        double d = distanceBetweenPoints/2.0;

        pointList.add(new model.Point(-0.5*d, -d, distanceFromCamera+d-2));
        pointList.add(new model.Point(0.5*d, -d, distanceFromCamera+d-2));
        pointList.add(new model.Point(-0.5*d, 20, distanceFromCamera+d-2));
        pointList.add(new model.Point(0.5*d, 20, distanceFromCamera+d-2));

        pointList.add(new model.Point(20, -25, distanceFromCamera+d-2));
        pointList.add(new model.Point(28, -25, distanceFromCamera+d-2));
        pointList.add(new model.Point(20, -20, distanceFromCamera+d-2));
        pointList.add(new Point(28, -20, distanceFromCamera+d-2));
    }

    private void initLines() {
        //down+up
        lineList.add(new Line(pointList.get(0), pointList.get(1),Color.BLUE));
        lineList.add(new Line(pointList.get(0), pointList.get(2),Color.BLUE));
        lineList.add(new Line(pointList.get(1), pointList.get(3),Color.BLUE));
        lineList.add(new Line(pointList.get(2), pointList.get(3),Color.BLUE));

        lineList.add(new Line(pointList.get(4), pointList.get(5),Color.BLACK));
        lineList.add(new Line(pointList.get(4), pointList.get(6),Color.BLACK));
        lineList.add(new Line(pointList.get(5), pointList.get(7),Color.BLACK));
        lineList.add(new Line(pointList.get(6), pointList.get(7),Color.BLACK));
    }

    private void initPolygons() {
        polygonList.add(new Polygon(lineList.get(0), lineList.get(1), lineList.get(3), lineList.get(2)));
        polygonList.add(new Polygon(lineList.get(4), lineList.get(5), lineList.get(7), lineList.get(6)));
    }
}
