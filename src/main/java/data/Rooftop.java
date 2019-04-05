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
public class Rooftop implements Drawable {

    private List<Line> lineList;
    private List<Point> pointList;
    private List<Polygon> polygonList;

    private double distanceFromCamera;
    private double distanceBetweenPoints;

    public Rooftop() {
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
        pointList.add(new Point(-2*d, 12*d, distanceFromCamera+d));
        pointList.add(new Point(2*d, 12*d, distanceFromCamera+d));
        pointList.add(new Point(2*d, 12*d, distanceFromCamera+3*d));
        pointList.add(new Point(-2*d, 12*d, distanceFromCamera+3*d));

        pointList.add(new Point(0, 12*d+100, distanceFromCamera+d));
        pointList.add(new Point(0, 12*d+100, distanceFromCamera+3*d));
    }

    private void initLines() {
        lineList.add(new Line(pointList.get(0), pointList.get(1), Color.RED));
        lineList.add(new Line(pointList.get(0), pointList.get(3), Color.RED));
        lineList.add(new Line(pointList.get(1), pointList.get(2), Color.RED));
        lineList.add(new Line(pointList.get(2), pointList.get(3), Color.RED));

        lineList.add(new Line(pointList.get(4), pointList.get(0), Color.RED));
        lineList.add(new Line(pointList.get(1), pointList.get(4), Color.RED));
        lineList.add(new Line(pointList.get(3), pointList.get(5), Color.RED));
        lineList.add(new Line(pointList.get(5), pointList.get(2), Color.RED));

        lineList.add(new Line(pointList.get(5), pointList.get(4), Color.RED));
    }

    private void initPolygons() {
        polygonList.add(new Polygon(lineList.get(0), lineList.get(2), lineList.get(3), lineList.get(1)));
        polygonList.add(new Polygon(lineList.get(0), lineList.get(5), lineList.get(4)));
        polygonList.add(new Polygon(lineList.get(3), lineList.get(6), lineList.get(7)));
        polygonList.add(new Polygon(lineList.get(4), lineList.get(1), lineList.get(6), lineList.get(8)));
        polygonList.add(new Polygon(lineList.get(2), lineList.get(7), lineList.get(8), lineList.get(5)));
    }

}
