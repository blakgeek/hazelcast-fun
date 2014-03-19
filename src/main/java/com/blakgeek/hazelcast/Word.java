package com.blakgeek.hazelcast;

import java.io.Serializable;

/**
 * User: Carlos Lawton
 * Date: 3/16/14
 * Time: 5:15 PM
 */
public class Word implements Serializable {

    private String value;

    public Word(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
