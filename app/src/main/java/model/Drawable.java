package model;

import java.util.List;

public interface Drawable {
    List<Polygon> getPolygonList();
    List<Point> getPointList();
    List<Line> getLineList();
}
