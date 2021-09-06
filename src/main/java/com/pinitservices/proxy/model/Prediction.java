/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Prediction {

    private String description;
    private String id;

    @JsonProperty("matched_substrings")
    private List<MatchedSubstring> matchedSubStrings;

    @JsonProperty("place_id")
    private String placeId;

    private String reference;

    @JsonProperty("structured_formatting")
    private StructuredFormatting structuredFormattings;

    private List<Term> terms;

    private List<String> types;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MatchedSubstring> getMatchedSubStrings() {
        return matchedSubStrings;
    }

    public void setMatchedSubStrings(List<MatchedSubstring> matchedSubStrings) {
        this.matchedSubStrings = matchedSubStrings;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public StructuredFormatting getStructuredFormattings() {
        return structuredFormattings;
    }

    public void setStructuredFormattings(StructuredFormatting structuredFormattings) {
        this.structuredFormattings = structuredFormattings;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

}
