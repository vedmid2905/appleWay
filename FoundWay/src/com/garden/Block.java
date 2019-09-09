package com.garden;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Block {

    Point start;
    Point end;
    List<Route> routes = new ArrayList();
    List<Point> inPoints= new ArrayList();;
    List<Point> outPoints= new ArrayList();;
}

class Route {
    Point start;
    Point end;
    int length;
    boolean leave =true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return
                start.equals(route.start) &&
                end.equals(route.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "Route{" +
                "start=" + start +
                ", end=" + end +
                ", length=" + length +
                '}';
    }
}