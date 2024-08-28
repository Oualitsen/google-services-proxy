/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.googleApiModel;

import com.pinitservices.proxy.utils.CoordsUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Getter
@Setter
public class DistanceMatrixRequest {

    private List<Coords> origins;
    private List<Coords> destinations;

    public void addOrigin(Coords coordinates) {
        if (origins == null) {
            origins = new ArrayList<>();
        }
        origins.add(coordinates);
    }

    public void addDest(Coords coordinates) {
        if (destinations == null) {
            destinations = new ArrayList<>();
        }
        destinations.add(coordinates);
    }


    public boolean isCloseTo(DistanceMatrixRequest request) {
        if (request != null) {
            return CoordsUtils.areClose(origins, request.origins) && CoordsUtils.areClose(destinations, request.destinations);
        }
        return false;
    }

}
