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
public class CenterCube implements Drawable {

    private List<Line> lineList;
    private List<Point> pointList;
    private List<Polygon> polygonList;

    private double distanceFromCamera;
    private double distanceBetweenPoints;

    public CenterCube() {
        this.lineList = new ArrayList<Line>();
        this.pointList = new ArrayList<Point>();
        this.polygonList = new ArrayList<Polygon>();
        this.distanceFromCamera = Configuration.DISTANCE_FROM_CAMERA;
        this.distanceBetweenPoints = 150;
        init();
    }

    private void init() {
        initPoints();
        initLines();
        initPolygons();
    }

    private void initPoints() {
        double d = distanceBetweenPoints/2.0;

        pointList.add(new Point(-2*d, -d, distanceFromCamera+d));//
        pointList.add(new Point(-2*d, 12*d, distanceFromCamera+d));//
        pointList.add(new Point(2*d, 12*d, distanceFromCamera+d));//
        pointList.add(new Point(2*d, -d, distanceFromCamera+d));//

        pointList.add(new Point(-2*d, -d, distanceFromCamera+3*d));
        pointList.add(new Point(-2*d, 12*d, distanceFromCamera+3*d));
        pointList.add(new Point(2*d, 12*d, distanceFromCamera+3*d));
        pointList.add(new Point(2*d, -d, distanceFromCamera+3*d));

        //pointList.add(new Point(0, -d, distanceFromCamera+d));
        //pointList.add(new Point(0, d, distanceFromCamera+d));
    }

    private void initLines() {
        for(int i=0;i<3;i++) {
            lineList.add(new Line(pointList.get(i), pointList.get(i + 1), Color.LIGHT_GRAY));
            lineList.add(new Line(pointList.get(i+4), pointList.get(i + 5), Color.LIGHT_GRAY));
            lineList.add(new Line(pointList.get(i), pointList.get(i + 4), Color.LIGHT_GRAY));
        }
        lineList.add(new Line(pointList.get(0), pointList.get(3), Color.LIGHT_GRAY));
        lineList.add(new Line(pointList.get(4), pointList.get(7), Color.LIGHT_GRAY));
        lineList.add(new Line(pointList.get(3), pointList.get(7), Color.LIGHT_GRAY));
    }

    private void initPolygons() {
        polygonList.add(new Polygon(lineList.get(0), lineList.get(5), lineList.get(1), lineList.get(2)));
        polygonList.add(new Polygon(lineList.get(10), lineList.get(1), lineList.get(4), lineList.get(7)));
        polygonList.add(new Polygon(lineList.get(11), lineList.get(7), lineList.get(8), lineList.get(6)));
        polygonList.add(new Polygon(lineList.get(9), lineList.get(0), lineList.get(3), lineList.get(6)));

        polygonList.add(new Polygon(lineList.get(2), lineList.get(10), lineList.get(11), lineList.get(9)));
        polygonList.add(new Polygon(lineList.get(3), lineList.get(5), lineList.get(4), lineList.get(8)));
    }

}
