/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.googleApiModel;


import lombok.Getter;
import lombok.Setter;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 *
 */

@Getter
@Setter
public class Row {

    private List<Element> elements;


    public boolean isFull() {
        if (elements == null) {
            return false;
        }

        final Iterator<Element> it = elements.iterator();
        while (it.hasNext()) {
            if (it.next() == null) {
                return false;
            }
        }
        return true;
    }

    public Optional<Element> getFirstOkElement() {
        return elements.stream().filter(element -> element.getStatus() == ElementStatus.OK).findFirst();
    }

    public boolean hasOkElement() {
        return getFirstOkElement().isPresent();
    }


}
