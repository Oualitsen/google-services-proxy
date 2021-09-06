/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 *
 */

public class DistanceMatrixResponse {

    private ResponseStatus status;

    @JsonProperty("origin_addresses")
    private List<String> originalAddresses;

    @JsonProperty("destination_addresses")
    private List<String> destinationAddresses;

    @JsonProperty("error_message")
    private String errorMessage;

    private List<Element> elements;

    private List<Row> rows;

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public List<String> getOriginalAddresses() {
        return originalAddresses;
    }

    public void setOriginalAddresses(List<String> originalAddresses) {
        this.originalAddresses = originalAddresses;
    }

    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    public void setDestinationAddresses(List<String> destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Returns true if all requested a distances have results
     *
     * @return
     */
    public boolean isFull() {
        if (rows != null) {
            final Iterator<Row> it = rows.iterator();
            while (it.hasNext()) {
                Row next = it.next();
                if (next == null) {
                    return false;
                } else {
                    boolean rowFull = next.isFull();
                    if (!rowFull) {
                        return false;
                    }
                }

            }
        }
        return true;
    }

    public boolean append(DistanceMatrixResponse response) {
        status = response.status;
        if (response.getStatus() == ResponseStatus.OK) {
            if (this.rows == null) {
                if (response.getRows().get(0).getElements().get(0).getStatus() == ElementStatus.OK) {
                    this.rows = response.getRows();
                    addElement(rows.get(0).getElements().get(0));
                    return true;
                } else {
                    return false;
                }
            } else {
                addElement(rows.get(0).getElements().get(0));
                return rows.get(0).getElements().get(0).add(response.getRows().get(0).getElements().get(0));
            }

        }

        return false;
    }

    private void addElement(Element element) {
        if (this.elements == null) {
            this.elements = new ArrayList<>();
        }
        this.elements.add(element);
    }

    public List<Element> getElements() {
        if (elements != null) {
            return elements;
        }
        return new ArrayList<>(0);
    }

}
