package model;

import configuration.Configuration;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CenterCube implements Drawable{

    private List<Line> lineList;
    private List<Point> pointList;
    private double distanceFromCamera;
    private double distanceBetweenPoints;

    public CenterCube() {
        this.lineList = new ArrayList<Line>();
        this.pointList = new ArrayList<Point>();
        this.distanceFromCamera = Configuration.DISTANCE_FROM_CAMERA;
        this.distanceBetweenPoints = 150;
        init();
    }

    private void init() {
        double d = distanceBetweenPoints/2.0;

        pointList.add(new Point(-2*d, -d, distanceFromCamera+d));
        pointList.add(new Point(-2*d, d, distanceFromCamera+d));
        pointList.add(new Point(2*d, d, distanceFromCamera+d));
        pointList.add(new Point(2*d, -d, distanceFromCamera+d));

        pointList.add(new Point(-2*d, -d, distanceFromCamera+3*d));
        pointList.add(new Point(-2*d, d, distanceFromCamera+3*d));
        pointList.add(new Point(2*d, d, distanceFromCamera+3*d));
        pointList.add(new Point(2*d, -d, distanceFromCamera+3*d));

        for(int i=0;i<3;i++) {
            lineList.add(new Line(pointList.get(i), pointList.get(i + 1)));
            lineList.add(new Line(pointList.get(i+4), pointList.get(i + 5)));
            lineList.add(new Line(pointList.get(i), pointList.get(i + 4)));
        }
        lineList.add(new Line(pointList.get(0), pointList.get(3)));
        lineList.add(new Line(pointList.get(4), pointList.get(7)));
        lineList.add(new Line(pointList.get(3), pointList.get(7)));
    }

}
