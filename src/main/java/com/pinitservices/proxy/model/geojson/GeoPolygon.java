
package com.pinitservices.proxy.model.geojson;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
public class GeoPolygon extends BasicGeoShape {

    private List<List<Double[]>> coordinates;

    public GeoPolygon() {
        type = TYPE_POLYGON;
    }

    public void addCoordinates(double[] coords) {
        if (coordinates == null) {
            coordinates = new ArrayList<>();
            coordinates.add(new ArrayList<>());
        }
        coordinates.get(0).add(new Double[] { coords[0], coords[1] });
    }

}
