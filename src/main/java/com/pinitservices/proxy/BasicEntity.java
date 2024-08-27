/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinitservices.proxy;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

/**
 *
 */

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@FieldNameConstants
@SuperBuilder
@NoArgsConstructor
public abstract class BasicEntity {

    @Id
    protected String id;

    @CreatedDate
    protected long creationDate;

    @LastModifiedDate
    protected long lastUpdate;

}
