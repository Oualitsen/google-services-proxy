/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model.cache;

import com.pinitservices.proxy.model.PlacesResult;

/**
 *
 * @author Ramdane
 */

public class PlacesCache extends Cache {

    public static final String FIELD_RESULT = "result";
    public static final String FIELD_QUERY = "query";
    public static final String FIELD_LANG = "lang";

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
