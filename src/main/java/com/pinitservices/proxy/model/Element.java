/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Element {

    private ElementStatus status;
    private TextValue duration;
    private TextValue distance;

    @JsonProperty("duration_in_traffic")
    private TextValue durationInTraffic;

    public void setDuration(TextValue duration) {
        this.duration = duration;
        if (duration != null) {
            duration.setDistance(false);
        }
    }

    public void setDistance(TextValue distance) {
        this.distance = distance;
        if (distance != null) {
            distance.setDistance(true);
        }
    }

    public void setDurationInTraffic(TextValue durationInTraffic) {
        this.durationInTraffic = durationInTraffic;
        if (durationInTraffic != null) {
            durationInTraffic.setDistance(false);
        }
    }

    public boolean add(Element element) {
        if (element.getStatus() == ElementStatus.OK) {
            duration.add(element.duration);
            distance.add(element.distance);
            durationInTraffic.add(element.durationInTraffic);

        }
        status = element.status;
        return status == ElementStatus.OK;
    }

}
