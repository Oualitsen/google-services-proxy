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

/**
 *
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeResult extends GeoPolygon {

    public static final String FIELD_ADDRESS_COMPONENTS = "addressComponents";
    public static final String FIELD_FORMATTED_ADDRESS = "formattedAddress";
    public static final String FIELD_GEOMETRY = "geometry";
    public static final String FIELD_PLACE_ID = "placeId";
    public static final String FIELD__PLACE_ID = "place_id";
    public static final String FIELD_TYPES = "types";
    public static final String FIELD_CENTRE = "center";

    @JsonProperty("address_components")
    private List<AddressComponent> addressComponents;

    @JsonProperty("formatted_address")
    private String formattedAddress;

    private Geometry geometry;

    private GeoPoint center;

    @JsonProperty("place_id")
    private String placeId;

    private List<String> types;

    public List<AddressComponent> getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(List<AddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
        if (geometry != null) {
            if (center == null) {
                center = new GeoPoint();
            }
            final Coords coord = geometry.getLocation();
            double[] coords = { coord.getLng(), coord.getLat() };
            center.setCoordinates(coords);
        } else {
            center = null;
        }
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public boolean contains(double lat, double lng) {
        return geometry.contains(lat, lng);
    }

    public GeoPoint getCenter() {
        return center;
    }

    public void setCenter(GeoPoint center) {
        this.center = center;
    }

}
