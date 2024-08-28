/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.utils;


import com.pinitservices.proxy.googleApiModel.Coords;
import com.pinitservices.proxy.model.geojson.GeoPoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public static boolean areClose(List<Coords> l1, List<Coords> l2) {
        if (l1 == null || l2 == null) {
            return false;
        }
        if (l1.size() != l2.size()) {
            return false;
        }

        final Iterator<Coords> it1 = l1.iterator();
        final Iterator<Coords> it2 = l2.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            Coords c1 = it1.next();
            Coords c2 = it2.next();

            if (!areClose(c1, c2)) {
                return false;
            }
        }
        return true;
    }

    public static boolean areClose(Coords c1, Coords c2) {
        if (c1 == null || c2 == null) {
            return false;
        }
        final double distance = distance(c1, c2) * 1000;
        return distance < 10;
    }

    public static double distance(Coords coods1, Coords coods2) {
        return distance(Math.toRadians(coods1.lat()), Math.toRadians(coods1.lng()), Math.toRadians(coods2.lat()), Math.toRadians(coods2.lng()));
    }

    public static double distance(GeoPoint coods1, GeoPoint coods2) {
        return distance(Math.toRadians(coods1.getLat()), Math.toRadians(coods1.getLng()),
                Math.toRadians(coods2.getLat()), Math.toRadians(coods2.getLng()));
    }

    public static double distance(List<Coords> coordinateList) {
        if (coordinateList == null || coordinateList.size() <= 1) {
            return 0;
        } else {
            double distance = 0d;
            final Iterator<Coords> it = coordinateList.iterator();
            Coords curr = it.next();
            while (it.hasNext()) {
                final Coords next = it.next();
                distance += distance(curr, next);
                curr = next;
            }
            return distance;
        }
    }


    public static double angle(Coords c, Coords center) {
        double centerC1 = distance(center, c);
        Coords Coords = new Coords(center.lat(), c.lng());
        double centerC = distance(center, Coords);

        double d = Math.toDegrees(Math.acos(centerC / centerC1));

        if (c.lng() < center.lng()) {

            if (c.lat() > center.lat()) {
                d += 270;
            } else {
                d += 180;
            }
        } else {
            if (c.lat() > center.lat()) {
                d += 90;
            } else {
                d += 0;
            }
        }
        return d;
    }

    public static double area(List<Coords> list) {
        list.add(list.get(0));
        Coords[] points = list.toArray(new Coords[0]);
        double sum = 0.0;

        for (int i = 0; i < points.length - 1; ++i) {
            sum += (points[i].lat() * points[i + 1].lng()) - (points[i + 1].lat() * points[i].lng());
        }
        return sum;
    }

    public static List<Coords> decodePolyline(String encoded) {

        List<Coords> poly = new ArrayList<Coords>();
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
            Coords p = new Coords(lat / 1e5,
                    lng / 1e5);
            poly.add(p);
        }

        return poly;
    }

    public static Coords from(Coords c) {
        return new Coords(c.lat(), c.lng());
    }

}
