/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model.cache;

import com.pinitservices.proxy.model.BasicEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 *
 * @author Ramdane
 */
@FieldNameConstants
@Getter
@Setter
public class Cache extends BasicEntity {

    protected String userId;

    @Indexed
    protected String lang;

    public void setLang(String lang) {
        if (lang == null) {
            this.lang = null;
        } else {
            this.lang = lang.toLowerCase();
        }

    }

}
