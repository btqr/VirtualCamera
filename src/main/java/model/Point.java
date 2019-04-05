package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.sqrt;

@Data
@AllArgsConstructor
public class Point {
    private double x,y,z;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point p) {
        return (p.getX()-getX())*(p.getX()-getX()) + (p.getY()-getY())*(p.getY()-getY()) + (p.getZ()-getZ())*(p.getZ()-getZ());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (abs(point.x -  x) > 0.000001) return false;
        if (abs(point.y -  y) > 0.000001) return false;
        if (abs(point.z -  z) > 0.000001) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
