package com.aniket.homework.componentservice.util;

public enum Version {

    V1("V1"),
    INVALID_VERSION("INVALID_VERSION");


    private final String value;
    Version(String v){ value = v;}
    public static Version fromValue(String v) {
        for (Version c: Version.values()) {
            if (c.value.equalsIgnoreCase(v))
                return c;
        }
        return INVALID_VERSION;
    }
}
