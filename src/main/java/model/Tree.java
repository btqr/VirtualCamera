package model;

import configuration.Configuration;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
@Data
public class Tree implements Drawable{
        private List<Line> lineList;
        private List<Point> pointList;
        private double distanceFromCamera;
        private double distanceBetweenPoints;

        public Tree() {
            this.lineList = new ArrayList<Line>();
            this.pointList = new ArrayList<Point>();
            this.distanceFromCamera = Configuration.DISTANCE_FROM_CAMERA;
            this.distanceBetweenPoints = 150;
            init();
        }

    public void init() {
        double d = distanceBetweenPoints/2.0;

        //down
        pointList.add(new Point(3*d, -d, distanceFromCamera+d));
        pointList.add(new Point(3*d+30, -d, distanceFromCamera+d));
        pointList.add(new Point(3*d, -d, distanceFromCamera+d+30));
        pointList.add(new Point(3*d+30, -d, distanceFromCamera+d+30));

        //up
        pointList.add(new Point(3*d, d, distanceFromCamera+d));
        pointList.add(new Point(3*d+30, d, distanceFromCamera+d));
        pointList.add(new Point(3*d, d, distanceFromCamera+d+30));
        pointList.add(new Point(3*d+30, d, distanceFromCamera+d+30));

        //green part
        pointList.add(new Point(3*d-15, d, distanceFromCamera+d-15));
        pointList.add(new Point(3*d+45, d, distanceFromCamera+d-15));
        pointList.add(new Point(3*d-15, d, distanceFromCamera+d+45));
        pointList.add(new Point(3*d+45, d, distanceFromCamera+d+45));
        pointList.add(new Point(3*d+15, d+150, distanceFromCamera+d));

        //down+up
        lineList.add(new Line(pointList.get(0), pointList.get(1),Color.darkGray));
        lineList.add(new Line(pointList.get(0), pointList.get(2),Color.darkGray));
        lineList.add(new Line(pointList.get(1), pointList.get(3),Color.darkGray));
        lineList.add(new Line(pointList.get(2), pointList.get(3),Color.darkGray));
        lineList.add(new Line(pointList.get(4), pointList.get(5),Color.darkGray));
        lineList.add(new Line(pointList.get(4), pointList.get(6),Color.darkGray));
        lineList.add(new Line(pointList.get(5), pointList.get(7),Color.darkGray));
        lineList.add(new Line(pointList.get(6), pointList.get(7),Color.darkGray));
        lineList.add(new Line(pointList.get(0), pointList.get(4),Color.darkGray));
        lineList.add(new Line(pointList.get(1), pointList.get(5),Color.darkGray));
        lineList.add(new Line(pointList.get(2), pointList.get(6),Color.darkGray));
        lineList.add(new Line(pointList.get(3), pointList.get(7),Color.darkGray));

        //green part
        lineList.add(new Line(pointList.get(8), pointList.get(9),Color.GREEN));
        lineList.add(new Line(pointList.get(8), pointList.get(10),Color.GREEN));
        lineList.add(new Line(pointList.get(9), pointList.get(11),Color.GREEN));
        lineList.add(new Line(pointList.get(10), pointList.get(11),Color.GREEN));
        lineList.add(new Line(pointList.get(8), pointList.get(12),Color.GREEN));
        lineList.add(new Line(pointList.get(9), pointList.get(12),Color.GREEN));
        lineList.add(new Line(pointList.get(10), pointList.get(12),Color.GREEN));
        lineList.add(new Line(pointList.get(11), pointList.get(12),Color.GREEN));
    }
}
