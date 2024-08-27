/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.utils;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.pinitservices.proxy.googleApiModel.Coords;
import com.pinitservices.proxy.model.Coordinates;
import com.pinitservices.proxy.model.geojson.GeoPoint;

/**
 *
 */
public class CoordsUtils {

    public static final long R = 6371;//KM

    public static double distance(double lat1Rad, double lng1Rad, double lat2Rad, double lng2Rad) {

        double dlat = lat2Rad - lat1Rad;
        double dlng = lng2Rad - lng1Rad;
        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2)
                + Math.sin(dlng / 2) * Math.sin(dlng / 2) * Math.cos(lat1Rad) * Math.cos(lat2Rad);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;

    }

    public static boolean areClose(List<Coordinates> l1, List<Coordinates> l2) {
        if (l1 == null || l2 == null) {
            return false;
        }
        if (l1.size() != l2.size()) {
            return false;
        }

        final Iterator<Coordinates> it1 = l1.iterator();
        final Iterator<Coordinates> it2 = l2.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            Coordinates c1 = it1.next();
            Coordinates c2 = it2.next();

            if (!areClose(c1, c2)) {
                return false;
            }
        }
        return true;
    }

    public static boolean areClose(Coordinates c1, Coordinates c2) {
        if (c1 == null || c2 == null) {
            return false;
        }
        final double distance = distance(c1, c2) * 1000;
        return distance < 10;
    }

    public static double distance(Coordinates coods1, Coordinates coods2) {
        return distance(Math.toRadians(coods1.getLatitude()), Math.toRadians(coods1.getLongitude()), Math.toRadians(coods2.getLatitude()), Math.toRadians(coods2.getLongitude()));
    }

    public static double distance(Coords coods1, Coords coods2) {
        return distance(Math.toRadians(coods1.lat()), Math.toRadians(coods1.lng()), Math.toRadians(coods2.lat()), Math.toRadians(coods2.lng()));
    }

    public static double distance(GeoPoint coods1, GeoPoint coods2) {
        return distance(Math.toRadians(coods1.getLat()), Math.toRadians(coods1.getLng()),
                Math.toRadians(coods2.getLat()), Math.toRadians(coods2.getLng()));
    }

    public static double distance(List<Coordinates> coordinateList) {
        if (coordinateList == null || coordinateList.size() <= 1) {
            return 0;
        } else {
            double distance = 0d;
            final Iterator<Coordinates> it = coordinateList.iterator();
            Coordinates curr = it.next();
            while (it.hasNext()) {
                final Coordinates next = it.next();
                distance += distance(curr, next);
                curr = next;
            }
            return distance;
        }
    }

    public static double getSpeed(Coordinates c1, Coordinates c2, boolean basedOnLocalTime) {
        if (c1 == null || c2 == null) {
            return 0;
        }
        final double distance = CoordsUtils.distance(c2, c1);

        if (distance == 0) {
            return 0;
        }

        double dT = (basedOnLocalTime ? (c1.getLocalTime() - c2.getLocalTime()) : (c1.getServerTime() - c2.getServerTime()));
        dT /= (1000 * 60 * 60);
        if (dT == 0) {
            return 0;
        }
        double speedInKmH = distance / dT;
        return Math.abs(speedInKmH);
    }

    public static double getAvgSpeed(boolean basedOnLocalTime, List<Coordinates> coordinateList) {
        if (coordinateList == null || coordinateList.size() <= 1) {
            return 0;
        }

        int count = 0;
        double speedAcc = 0;
        final Iterator<Coordinates> it = coordinateList.iterator();
        Coordinates current = it.next();
        while (it.hasNext()) {
            Coordinates next = it.next();
            speedAcc += CoordsUtils.getSpeed(current, next, basedOnLocalTime);
            count++;
        }
        return speedAcc / count;
    }

    public static Coordinates getRecentCoordinates(List<Coordinates> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        final Iterator<Coordinates> it = list.iterator();
        Coordinates prev = it.next();
        while (it.hasNext()) {
            final Coordinates next = it.next();
            if (next.getLocalTime() > prev.getLocalTime()) {
                prev = next;
            }
        }
        return prev;
    }

    public static double angle(Coordinates c, Coordinates center) {
        double centerC1 = distance(center, c);
        Coordinates coordinates = new Coordinates(center.getLatitude(), c.getLongitude());
        double centerC = distance(center, coordinates);

        double d = Math.toDegrees(Math.acos(centerC / centerC1));

        if (c.getLongitude() < center.getLongitude()) {

            if (c.getLatitude() > center.getLatitude()) {
                d += 270;
            } else {
                d += 180;
            }
        } else {
            if (c.getLatitude() > center.getLatitude()) {
                d += 90;
            } else {
                d += 0;
            }
        }
        return d;
    }

    public static double area(List<Coordinates> list) {
        list.add(list.get(0));
        Coordinates[] points = list.toArray(new Coordinates[0]);
        double sum = 0.0;

        for (int i = 0; i < points.length - 1; ++i) {
            sum += (points[i].getLatitude() * points[i + 1].getLongitude()) - (points[i + 1].getLatitude() * points[i].getLongitude());
        }
        return sum;
    }

    public static List<Coordinates> decodePolyline(String encoded) {

        List<Coordinates> poly = new ArrayList<Coordinates>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            Coordinates p = new Coordinates(lat / 1e5,
                    lng / 1e5);
            poly.add(p);
        }

        return poly;
    }

    public static Coordinates from(Coords c) {
        return new Coordinates(c.lat(), c.lng());
    }

}
