/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model.geojson;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 *
 *
 */
@FieldNameConstants
@Getter
@Setter
public class BasicGeoShape {

    public static final String TYPE_POINT = "Point";
    public static final String TYPE_POLYGON = "Polygon";

    public static final String FIELD_TYPE = "type";
    public static final String FIELD_COORDINATES = "coordinates";

    protected String type;

}
