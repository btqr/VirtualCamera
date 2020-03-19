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
public class Tree implements Drawable {

        private List<Line> lineList;
        private List<Point> pointList;
        private List<Polygon> polygonList;

        private double distanceFromCamera;
        private double distanceBetweenPoints;

        public Tree() {
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
        //down
        pointList.add(new model.Point(3*d, -d, distanceFromCamera+d));
        pointList.add(new model.Point(3*d+30, -d, distanceFromCamera+d));
        pointList.add(new model.Point(3*d, -d, distanceFromCamera+d+30));
        pointList.add(new model.Point(3*d+30, -d, distanceFromCamera+d+30));

        //up
        pointList.add(new model.Point(3*d, d, distanceFromCamera+d));
        pointList.add(new model.Point(3*d+30, d, distanceFromCamera+d));
        pointList.add(new model.Point(3*d, d, distanceFromCamera+d+30));
        pointList.add(new model.Point(3*d+30, d, distanceFromCamera+d+30));

        //green part
        pointList.add(new model.Point(3*d-15, d, distanceFromCamera+d-15));
        pointList.add(new model.Point(3*d+45, d, distanceFromCamera+d-15));
        pointList.add(new model.Point(3*d-15, d, distanceFromCamera+d+45));
        pointList.add(new model.Point(3*d+45, d, distanceFromCamera+d+45));
        pointList.add(new Point(3*d+15, d+150, distanceFromCamera+d));
    }

    private void initLines() {
        //down+up
        lineList.add(new Line(pointList.get(1), pointList.get(0),Color.darkGray));
        lineList.add(new Line(pointList.get(0), pointList.get(2),Color.darkGray));
        lineList.add(new Line(pointList.get(2), pointList.get(3),Color.darkGray));
        lineList.add(new Line(pointList.get(1), pointList.get(3),Color.darkGray));

        lineList.add(new Line(pointList.get(5), pointList.get(4),Color.darkGray));
        lineList.add(new Line(pointList.get(4), pointList.get(6),Color.darkGray));
        lineList.add(new Line(pointList.get(6), pointList.get(7),Color.darkGray));
        lineList.add(new Line(pointList.get(7), pointList.get(5),Color.darkGray));

        lineList.add(new Line(pointList.get(0), pointList.get(4),Color.darkGray));
        lineList.add(new Line(pointList.get(1), pointList.get(5),Color.darkGray));
        lineList.add(new Line(pointList.get(2), pointList.get(6),Color.darkGray));
        lineList.add(new Line(pointList.get(3), pointList.get(7),Color.darkGray));

        //green part
        lineList.add(new Line(pointList.get(9), pointList.get(8),Color.GREEN));
        lineList.add(new Line(pointList.get(8), pointList.get(10),Color.GREEN));
        lineList.add(new Line(pointList.get(10), pointList.get(11),Color.GREEN));
        lineList.add(new Line(pointList.get(9), pointList.get(11),Color.GREEN));

        lineList.add(new Line(pointList.get(8), pointList.get(12),Color.GREEN));
        lineList.add(new Line(pointList.get(9), pointList.get(12),Color.GREEN));
        lineList.add(new Line(pointList.get(10), pointList.get(12),Color.GREEN));
        lineList.add(new Line(pointList.get(11), pointList.get(12),Color.GREEN));
    }

    private void initPolygons() {
        polygonList.add(new Polygon(lineList.get(0), lineList.get(1), lineList.get(2), lineList.get(3)));
        polygonList.add(new Polygon(lineList.get(4), lineList.get(5), lineList.get(6), lineList.get(7)));
        polygonList.add(new Polygon(lineList.get(1), lineList.get(8), lineList.get(5), lineList.get(10)));
        polygonList.add(new Polygon(lineList.get(2), lineList.get(10), lineList.get(6), lineList.get(11)));
        polygonList.add(new Polygon(lineList.get(0), lineList.get(8), lineList.get(4), lineList.get(9)));
        polygonList.add(new Polygon(lineList.get(3), lineList.get(9), lineList.get(7), lineList.get(11)));

        polygonList.add(new Polygon(lineList.get(12), lineList.get(13), lineList.get(14), lineList.get(15)));
        polygonList.add(new Polygon(lineList.get(12), lineList.get(16), lineList.get(17)));
        polygonList.add(new Polygon(lineList.get(13), lineList.get(16), lineList.get(18)));
        polygonList.add(new Polygon(lineList.get(14), lineList.get(18), lineList.get(19)));
        polygonList.add(new Polygon(lineList.get(15), lineList.get(17), lineList.get(19)));
    }
}
