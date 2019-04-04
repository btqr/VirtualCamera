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
        g.clearRect(0, 0, screenWidth, screenHeight);
        if(wallHackActive == false){
            drawWithoutWallhack(g);
        } else {

            for (Drawable d : scene.getDrawableList()) {
                for (Line l : d.getLineList()) {
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
        List<Polygon> sortedPolygonList = sortPolygons();
        int j = 0;
        for (Polygon p : sortedPolygonList) {
            Polygon pWithChangedOrder = p.changeLineOrder();
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

    /* void drawWithZBuffor(Graphics g) {
        Map<Point, Color> colorMap = new HashMap<>();
        List<Polygon> polygonList = sortPolygons();
        for(int x = 0; x < screenWidth; x++) {
            for(int y = 0; y < screenHeight; y++) {
                Point currentPoint = new Point(x,y, Double.MAX_VALUE);
                colorMap.put(currentPoint, Color.WHITE);
                for(Polygon p : polygonList) {
                    currentPoint.setZ(p.getZForXY(x,y));
                    if(colorMap.get(currentPoint) == null) {
                        colorMap.put(currentPoint, p.getColor)
                    }
                }
            }
        }
    }*/

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
}
