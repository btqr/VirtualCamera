package controller;

import configuration.Configuration;
import lombok.Data;
import model.Drawable;
import model.Line;
import model.Point;
import model.Polygon;
import model.Scene;
import view.VirtualCamera;


import java.awt.*;
import java.util.*;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

@Data
public class CameraController {
    private Scene scene;
    private VirtualCamera camera;
    private int screenHeight, screenWidth;
    private double rotationDegree, movingStep, scalingStep, scale, distanceFromCamera;
    private boolean wallHackActive;
    private final List<Polygon> polygonList;

    public CameraController(Scene scene, VirtualCamera camera) {
        this.scene = scene;
        this.camera = camera;
        this.rotationDegree = Configuration.ROTATION_DEGREE;
        this.movingStep = Configuration.MOVING_STEP;
        this.scalingStep = Configuration.SCALING_STEP;
        this.distanceFromCamera = Configuration.DISTANCE_FROM_CAMERA;
        this.screenHeight = Configuration.SCREEN_HEIGHT;
        this.screenWidth = Configuration.SCREEN_WIDTH;
        this.scale = 1.0;
        this.wallHackActive = true;
        this.polygonList = sortPolygons();
        camera.setController(this);
    }

    public void rotateX(double sign) {
        for(Drawable d : scene.getDrawableList()) {
            for(Point p : d.getPointList()) {
                double x = p.getX();
                double y = p.getY();
                double z = p.getZ();
                p.setX(x);
                p.setY(y * cos(rotationDegree*sign) - z * sin(rotationDegree*sign));
                p.setZ(y * sin(rotationDegree*sign) + z * cos(rotationDegree*sign));
            }
        }
    }

    public void rotateY(double sign) {
        for(Drawable d : scene.getDrawableList()) {
            for (Point p : d.getPointList()) {
                double x = p.getX();
                double y = p.getY();
                double z = p.getZ();
                p.setX(x * cos(rotationDegree*sign) + z * sin(rotationDegree*sign));
                p.setY(y);
                p.setZ(-x * sin(rotationDegree*sign) + z * cos(rotationDegree*sign));
            }
        }
    }

    public void rotateZ(double sign) {
        for(Drawable d : scene.getDrawableList()) {
            for (Point p : d.getPointList()) {
                double x = p.getX();
                double y = p.getY();
                double z = p.getZ();
                p.setX(x * cos(rotationDegree*sign) - y * sin(rotationDegree*sign));
                p.setY(x * sin(rotationDegree*sign) + y * cos(rotationDegree*sign));
                p.setZ(z);
            }
        }
    }

    public void moveForward() {
        for(Drawable d : scene.getDrawableList()) {
            for (Point p : d.getPointList()) {
                p.setZ(p.getZ() - movingStep);
            }
        }
    }

    public void moveBack() {
        for(Drawable d : scene.getDrawableList()) {
            for (Point p : d.getPointList()) {
                p.setZ(p.getZ() + movingStep);
            }
        }
    }

    public void moveLeft() {
        for(Drawable d : scene.getDrawableList()) {
            for (Point p : d.getPointList()) {
                p.setX(p.getX() + movingStep);
            }
        }
    }

    public void moveRight() {
        for(Drawable d : scene.getDrawableList()) {
            for (Point p : d.getPointList()) {
                p.setX(p.getX() - movingStep);
            }
        }
    }

    public void moveUp() {
        for(Drawable d : scene.getDrawableList()) {
            for (Point p : d.getPointList()) {
                p.setY(p.getY() - movingStep);
            }
        }
    }

    public void moveDown() {
        for(Drawable d : scene.getDrawableList()) {
            for (Point p : d.getPointList()) {
                p.setY(p.getY() + movingStep);
            }
        }
    }

    public void scaleUp() {
        scale += scalingStep;
    }

    public void scaleDown() {
        scale -= scalingStep;
    }

    public void draw(Graphics g) {
        List<Polygon> localPolygonList = splitPolygons();
        g.clearRect(0, 0, screenWidth, screenHeight);
        if(wallHackActive == false){
            drawWithoutWallhack(g);
        } else {
            for (Polygon p : localPolygonList) {
                for (Line l : p.getLineList()) {
                    l = l.trimLine().scaleLine(scale).projectTo2D().moveToCenter().revertCoordinates();
                    double x1 = l.getA().getX();
                    double y1 = l.getA().getY();
                    double x2 = l.getB().getX();
                    double y2 = l.getB().getY();

                    g.setColor(l.getColor());
                    if (l.getA().getZ() >= 0 && l.getB().getZ() >= 0) {
                        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
                    }
                }
            }
        }
    }

    private void drawWithoutWallhack(Graphics g) {
        List<Polygon> localPolygonList = splitPolygons();
        int j = 0;
        for (Polygon p : localPolygonList) {
            Polygon pWithChangedOrder = p.changeLineOrder2().changeLineOrder().changeLineOrder2().changeLineOrder();
            int[] xpoints = new int[pWithChangedOrder.getLineList().size() * 2];
            int[] ypoints = new int[pWithChangedOrder.getLineList().size() * 2];
            int i = 0;
            j++;
            for (Line l : pWithChangedOrder.getLineList()) {
                l = l.trimLine().scaleLine(scale).projectTo2D().moveToCenter().revertCoordinates();
                if (l.getA().getZ() >= 0 && l.getB().getZ() >= 0) {
                    xpoints[i] = (int) l.getA().getX();
                    xpoints[i+1] = (int) l.getB().getX();
                    ypoints[i] = (int) l.getA().getY();
                    ypoints[i+1] = (int) l.getB().getY();
                    i += 2;
                    g.setColor(l.getColor());
                }
            }
            g.fillPolygon(xpoints, ypoints, i);
        }
    }

    private List<Polygon> splitPolygons() {
        LinkedList<Polygon> polygonQueue = new LinkedList();
        for(Polygon p : polygonList) {
                polygonQueue.add(p);
        }
        for(int i = 0; i < Configuration.SPLITTING_CONST; i++) {
            Polygon p = polygonQueue.pop();
            List<Polygon> polygonsToAdd = splitPolygon(p);
            for(Polygon po : polygonsToAdd) polygonQueue.add(po);
        }
        List<Polygon> localPolygonList = new ArrayList<>();
        while(!polygonQueue.isEmpty()) {
            localPolygonList.add(polygonQueue.pop());
        }
        sortPolygons(localPolygonList);
        return localPolygonList;
    }

    private List<Polygon> splitPolygon(Polygon p) {
        List<Line> lineListBefore = new ArrayList<>();
        List<Line> lineListAfter = new ArrayList<>();
        List<Point> thresholdPointList = new ArrayList<>();
        List<Polygon> polygonsToAdd = new ArrayList<>();
        double d_height = p.findMaxY() - p.findMinY();
        double d_width = p.findMaxX() - p.findMinX();

        if(d_height > d_width) {
            double thresholdY = (p.findMaxY() + p.findMinY()+0.17342947238243874) / 2.0;
            for(Line line : p.getLineList()) {
                if(thresholdPointList.size() <2 && ((line.getA().getY() <= thresholdY && line.getB().getY() >= thresholdY) || (line.getA().getY() >= thresholdY && line.getB().getY() <= thresholdY))) {
                    Point p1 = line.getA();
                    Point p2 = line.getB();
                    double l = p2.getX() - p1.getX();
                    double m = p2.getY() - p1.getY();
                    double n = p2.getZ() - p1.getZ();

                    double x = - (l*(p1.getY() - thresholdY)/m - p1.getX());
                    double z = - (n*(p1.getY() - thresholdY)/m - p1.getZ());
                    thresholdPointList.add(new Point(x,thresholdY,z));
                    if(line.getA().getY() > thresholdY) {
                        lineListAfter.add(new Line(line.getA(),new Point(x,thresholdY,z), line.getColor()));
                        lineListBefore.add(new Line(new Point(x,thresholdY,z),line.getB(), line.getColor()));
                    } else {
                        lineListAfter.add(new Line(new Point(x,thresholdY,z),line.getB(), line.getColor()));
                        lineListBefore.add(new Line(line.getA(),new Point(x,thresholdY,z), line.getColor()));
                    }
                }
                if(line.getA().getY() < thresholdY && line.getB().getY() < thresholdY) {
                    lineListBefore.add(line);
                } else if(line.getA().getY() > thresholdY && line.getB().getY() > thresholdY) {
                    lineListAfter.add(line);
                }
            }
        } else {
            double thresholdX = (p.findMaxX() + p.findMinX()+0.1323947238243874) / 2.0;
            for(Line line : p.getLineList()) {
                if(thresholdPointList.size()<2 && ((line.getA().getX() <= thresholdX && line.getB().getX() >= thresholdX) || (line.getA().getX() >= thresholdX && line.getB().getX() <= thresholdX))) {
                    Point p1 = line.getA();
                    Point p2 = line.getB();
                    double l = p2.getX() - p1.getX();
                    double m = p2.getY() - p1.getY();
                    double n = p2.getZ() - p1.getZ();

                    double y = - (m*(p1.getX() - thresholdX)/l - p1.getY());
                    double z = - (n*(p1.getX() - thresholdX)/l - p1.getZ());
                    thresholdPointList.add(new Point(thresholdX,y,z));
                    if(line.getA().getX() > thresholdX) {
                        lineListAfter.add(new Line(line.getA(), new Point(thresholdX,y,z), line.getColor()));
                        lineListBefore.add(new Line(new Point(thresholdX,y,z),line.getB(), line.getColor()));
                    } else {
                        lineListAfter.add(new Line(new Point(thresholdX,y,z),line.getB(), line.getColor()));
                        lineListBefore.add(new Line(line.getA(),new Point(thresholdX,y,z), line.getColor()));
                    }
                }
                if(line.getA().getX() < thresholdX && line.getB().getX() < thresholdX) {
                    lineListBefore.add(line);
                } else if(line.getA().getX() > thresholdX && line.getB().getX() > thresholdX) {
                    lineListAfter.add(line);
                }
            }
        }
        if(thresholdPointList.size() == 2) {
            lineListBefore.add(new Line(thresholdPointList.get(0), thresholdPointList.get(1), lineListAfter.get(0).getColor()));
            lineListAfter.add(new Line(thresholdPointList.get(0), thresholdPointList.get(1), lineListAfter.get(0).getColor()));
            polygonsToAdd.add(new Polygon(lineListBefore));
            polygonsToAdd.add(new Polygon(lineListAfter));
        } else {
            System.out.println(thresholdPointList.size());
            if(d_height > d_width) {
                System.out.println("NEW POLYGON");
                System.out.println(p.findMaxY());
                System.out.println(p.findMinY());
                System.out.println((p.findMaxY() + p.findMinY()+1) / 2.0);
            } else {
                System.out.println(p.findMaxX());
                System.out.println(p.findMinX());
                System.out.println((p.findMaxX() + p.findMinX()) / 2.0);
            }
            for(Line line : p.getLineList()) {
                System.out.println(line);
            }
            return polygonsToAdd;
        }
        return polygonsToAdd;
    }


     private List<Polygon> sortPolygons() {
        List<Polygon> sortedPolygonList = new ArrayList<>();
        for(Drawable d : scene.getDrawableList()) {
            for(Polygon p : d.getPolygonList()) {
                sortedPolygonList.add(p);
            }
        }
        Collections.sort(sortedPolygonList);
        return sortedPolygonList;
     }

    private void sortPolygons(List<Polygon> polygonList) {
        Collections.sort(polygonList);
    }
}
