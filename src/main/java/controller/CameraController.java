package controller;

import configuration.Configuration;
import lombok.Data;
import model.Drawable;
import model.Line;
import model.Point;
import model.Scene;
import view.VirtualCamera;

import java.awt.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

@Data
public class CameraController {
    private Scene scene;
    private VirtualCamera camera;
    private int screenHeight, screenWidth;
    private double rotationDegree, movingStep, scalingStep, scale, distanceFromCamera;

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
        g.clearRect(0,0, screenWidth, screenHeight);
        for(Drawable d : scene.getDrawableList()) {
            for (Line l : d.getLineList()) {
                if(l.getA().getZ() * l.getB().getZ() < 0) l = trimLine(l); //trims line to z = 0 to avoid bugging
                double x1 = scale * l.getA().getX()/l.getA().getZ() * distanceFromCamera + screenWidth/2.0;
                double y1 = screenHeight - (scale * l.getA().getY()/l.getA().getZ() * distanceFromCamera + screenHeight/2.0);
                double x2 = scale * l.getB().getX()/l.getB().getZ() * distanceFromCamera + screenWidth/2.0;
                double y2 = screenHeight - (scale * l.getB().getY()/l.getB().getZ() * distanceFromCamera + screenHeight/2.0);
                g.setColor(l.getColor());
                if(l.getA().getZ() >= 0 && l.getB().getZ() >= 0) {
                    g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
                }
            }
        }
    }

     Line trimLine(Line l) {
        double dx = l.getB().getX() - l.getA().getX();
        double dy =  l.getB().getY() - l.getA().getY();
        double dz = l.getB().getZ() - l.getA().getZ();
        double y = - dy * (l.getA().getZ()-1)/dz + l.getA().getY();
        double x = - dx * (l.getA().getZ()-1)/dz + l.getA().getX();
        if(l.getA().getZ() < 0) {
            return new Line(new Point(x,y,0),l.getB(),l.getColor());
        } else {
            return new Line(l.getA(),new Point(x,y,0),l.getColor());
        }
     }
}
