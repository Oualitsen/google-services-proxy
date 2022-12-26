/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 *
 *
 */
@FieldNameConstants
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TextValue {

    private String text;
    private long value;

    boolean distance;

    public void add(TextValue value) {
        if (value != null) {
            this.value += value.value;
            updateText();
        }
    }

    private void updateText() {
        if (distance) {
            text = value + " KM";
        } else {

            long hours = value / 60;
            long minutes = value % 60;

            if (hours == 0) {
                text = value + " Min";
            } else {
                text = String.format("%s h %s min", hours, minutes);
            }

        }
    }

}
