
package com.pinitservices.proxy.model;

import java.util.List;
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
public class PlacesResult {

    private List<Prediction> predictions;
    private ResponseStatus status;

}
