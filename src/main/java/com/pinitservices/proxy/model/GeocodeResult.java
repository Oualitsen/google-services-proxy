/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pinitservices.proxy.model.geojson.GeoPoint;
import com.pinitservices.proxy.model.geojson.GeoPolygon;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

/**
 *
 *
 */
@FieldNameConstants
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeResult extends GeoPolygon {

    @JsonProperty("address_components")
    private List<AddressComponent> addressComponents;

    @JsonProperty("formatted_address")
    private String formattedAddress;

    private Geometry geometry;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoPoint center;

    @JsonProperty("place_id")
    private String placeId;

    private List<String> types;

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
        if (geometry != null) {
            if (center == null) {
                center = new GeoPoint();
            }
            final Coords coord = geometry.getLocation();
            double[] coords = {coord.getLng(), coord.getLat()};
            center.setCoordinates(coords);
        } else {
            center = null;
        }
    }

}
