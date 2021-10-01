/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model.cache;

import com.pinitservices.proxy.model.PlacesResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 *
 * @author Ramdane
 */
@FieldNameConstants
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlacesCache extends Cache {

    private PlacesResult result;
    private String query;

}
