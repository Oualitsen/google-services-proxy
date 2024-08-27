/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.googleApiModel;


import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Getter
@Setter
public class TextValue {

    boolean distance;
    private String text;
    private long value;

    public TextValue(String text, long value) {
        this.text = text;
        this.value = value;
    }

    public TextValue() {
    }

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
