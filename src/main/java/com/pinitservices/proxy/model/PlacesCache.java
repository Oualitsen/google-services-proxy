/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

import com.pinitservices.proxy.googleApiModel.PlacesResult;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 * @author Ramdane
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
public class PlacesCache extends Cache {

    private PlacesResult result;
    private String query;
    private String lang;

    public PlacesCache() {
    }

    public PlacesCache(PlacesResult result, String query, String lang) {
        this.result = result;
        this.query = query;
        this.lang = lang;
    }

    public PlacesResult getResult() {
        return result;
    }

    public void setResult(PlacesResult result) {
        this.result = result;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

}
