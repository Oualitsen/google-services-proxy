/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

/**
 *
 *
 */

public class TextValue {

    private String text;
    private long value;

    boolean distance;

    public TextValue(String text, long value) {
        this.text = text;
        this.value = value;
    }

    public TextValue() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public void setDistance(boolean distance) {
        this.distance = distance;
    }

    public boolean isDistance() {
        return distance;
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
