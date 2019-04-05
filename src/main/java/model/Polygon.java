package model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Data
public class Polygon implements Comparable<Polygon> {
    private List<Line> lineList;

    public Polygon(Line... lines) {
        this.lineList = new ArrayList<Line>();
        for(Line line : lines) {
            lineList.add(line);
        }
    }

    public Polygon(List<Line> lines) {
        this.lineList = new ArrayList<Line>();
        for(Line line : lines) {
            this.lineList.add(line);
        }
    }

    public double findMinZ() {
        double minZ = Double.MAX_VALUE;
        for (Line line : lineList) {
            minZ = min(min(line.getA().getZ(), line.getB().getZ()),minZ);
        }
        return minZ;
    }

    public double findMaxZ() {
        double maxZ = -1000000000;
        for (Line line : lineList) {
            maxZ = max(max(line.getA().getZ(), line.getB().getZ()),maxZ);
        }
        return maxZ;
    }

    public double findMinY() {
        double minY = Double.MAX_VALUE;
        for (Line line : lineList) {
            minY = min(min(line.getA().getY(), line.getB().getY()),minY);
        }
        return minY;
    }

    public double findMaxY() {
        double maxY = -1000000000;
        for (Line line : lineList) {
            maxY = max(max(line.getA().getY(), line.getB().getY()),maxY);
        }
        return maxY;
    }

    public double findMinX() {
        double minX = Double.MAX_VALUE;
        for (Line line : lineList) {
            minX = min(min(line.getA().getX(), line.getB().getX()),minX);
        }
        return minX;
    }

    public double findMaxX() {
        double maxX = -1000000000;
        for (Line line : lineList) {
            maxX = max(max(line.getA().getX(), line.getB().getX()),maxX);
        }
        return maxX;
    }

    public Point findMidpoint() {
        Point centroid = new Point((findMaxX()+findMinX())/2.0,(findMaxY()+findMinY())/2.0,(findMaxZ()+findMinZ())/2.0);
        return centroid;
    }

    public Polygon changeLineOrder() {
        Polygon p = new Polygon();
        int i = 0;
        Point lastPoint = null;

        for(Line line : lineList) {
            if(i == 0) {
                if(getLineList().get(0).getB().equals(getLineList().get(1).getA()) || getLineList().get(0).getB().equals(getLineList().get(1).getB())) {
                    p.getLineList().add(line);
                } else {
                    p.getLineList().add(new Line(lineList.get(i).getB(), lineList.get(i).getA(), lineList.get(i).getColor()));
                }
            } else {
                if(lineList.get(i).getA().equals(lastPoint)) {
                    p.getLineList().add(line);
                } else {
                    p.getLineList().add(new Line(lineList.get(i).getB(), lineList.get(i).getA(), lineList.get(i).getColor()));
                }
            }

            lastPoint = p.getLineList().get(i).getB();
            i++;
        }
        return p;
    }

    public Polygon changeLineOrder2() {
        if(getLineList().size() < 3) return this;
        List<Line> newLineList = new ArrayList<>();
        newLineList.add(getLineList().get(0));
        getLineList().remove(0);
        while(!getLineList().isEmpty()) {
            double min = 10000000000000.0;
            int min_indx = -1;
            for (int j = 0; j < getLineList().size(); j++) {
                if(getLineList().get(j).getA().distance(newLineList.get(newLineList.size()-1).getB()) < min) {
                    min = getLineList().get(j).getA().distance(newLineList.get(newLineList.size() - 1).getB());
                    min_indx = j;
                }
                if(getLineList().get(j).getB().distance(newLineList.get(newLineList.size()-1).getB()) < min) {
                    min = getLineList().get(j).getB().distance(newLineList.get(newLineList.size()-1).getB());
                    min_indx = j;
                }
            }
            newLineList.add(getLineList().get(min_indx));
            getLineList().remove(min_indx);
        }
        return new Polygon(newLineList);
    }

    @Override
    public int compareTo(Polygon o) {
        Point centroid = findMidpoint();
        Point oCentroid = o.findMidpoint();
        double myDistance = centroid.getX() * centroid.getX() + centroid.getY() * centroid.getY() + centroid.getZ()*centroid.getZ();
        double oDistance = oCentroid.getX() * oCentroid.getX() + oCentroid.getY() * oCentroid.getY() + oCentroid.getZ() * oCentroid.getZ();
        return (int) (oDistance - myDistance);

    }
}
