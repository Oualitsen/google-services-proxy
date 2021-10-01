/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

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
public class StructuredFormatting {

    @JsonProperty("main_text")
    private String mainText;

    @JsonProperty("secondary_text")
    private String secondaryText;

    @JsonProperty("main_text_matched_substrings")
    private List<Term> mainTextMatchedSubstrings;

}
