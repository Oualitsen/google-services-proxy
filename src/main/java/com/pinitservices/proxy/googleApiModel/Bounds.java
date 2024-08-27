/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.googleApiModel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ramdane
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bounds {
    private Coords northeast;
    private Coords southwest;
}
