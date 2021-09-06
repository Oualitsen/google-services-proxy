/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

/**
 *
 * @author Ramdane
 */

public class Bounds {

    private Coords northeast;
    private Coords southwest;

    public Bounds() {
    }

    public Bounds(Coords northeast, Coords southwest) {
        this.northeast = northeast;
        this.southwest = southwest;
    }

    public Coords getNortheast() {
        return northeast;
    }

    public void setNortheast(Coords northeast) {
        this.northeast = northeast;
    }

    public Coords getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Coords southwest) {
        this.southwest = southwest;
    }

}
