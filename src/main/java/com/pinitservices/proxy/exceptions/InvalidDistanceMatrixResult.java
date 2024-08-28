package com.pinitservices.proxy.exceptions;

import com.pinitservices.proxy.googleApiModel.DistanceMatrixResponse;

public class InvalidDistanceMatrixResult extends RuntimeException {

    private final DistanceMatrixResponse response;

    public InvalidDistanceMatrixResult(DistanceMatrixResponse response) {
        this.response = response;
    }

    @Override
    public String getMessage() {
        return String.format("Invalid response %s \n\r %s", response, super.getMessage());
    }


}
