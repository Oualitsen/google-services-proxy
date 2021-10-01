/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
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

}
