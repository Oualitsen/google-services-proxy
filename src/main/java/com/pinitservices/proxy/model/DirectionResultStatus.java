/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy.model;

/**
 *
 * @author Ramdane
 */
public enum DirectionResultStatus {
    OK, NOT_FOUND, ZERO_RESULTS, MAX_WAYPOINTS_EXCEEDED, MAX_ROUTE_LENGTH_EXCEEDED, INVALID_REQUEST, OVER_DAILY_LIMIT,
    OVER_QUERY_LIMIT, REQUEST_DENIED, UNKNOWN_ERROR
}
