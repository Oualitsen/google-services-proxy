package com.pinitservices.proxy.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;

@Slf4j
public class MyCircle extends Circle {
    public MyCircle(Point center, double radiusInMeters) {
        super(center, new Distance(radiusInMeters / 1000, Metrics.KILOMETERS));
    }

    public MyCircle(double lat, double lng, double radiusInMeters) {
        this(new Point(lng, lat), radiusInMeters);
    }
}


