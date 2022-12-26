package com.pinitservices.proxy.model.cache;

import com.pinitservices.proxy.model.DirectionResult;
import com.pinitservices.proxy.model.geojson.GeoPoint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Ramdane
 */
@Document
@FieldNameConstants
@Getter
@Setter
@NoArgsConstructor
public class DirectionsCache extends Cache {

    private DirectionResult result;
    private boolean withTrafficInfo;

    private long departureTime;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoPoint origin;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoPoint destination;

    public DirectionsCache(DirectionResult result, boolean withTrafficInfo, String lang, long departureTime) {
        this.result = result;
        this.withTrafficInfo = withTrafficInfo;
        this.lang = lang;
        this.departureTime = departureTime;
    }

}
