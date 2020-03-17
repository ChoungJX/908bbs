package com.demo.index.util;

import java.util.UUID;

/**
 * Uuid_generator
 */
public class Uuid_generator {

    private String getUuid;

    public Uuid_generator() {
        setGetUuid(UUID.randomUUID().toString());
    }

    public String getGetUuid() {
        return getUuid;
    }

    public void setGetUuid(String getUuid) {
        this.getUuid = getUuid;
    }
}