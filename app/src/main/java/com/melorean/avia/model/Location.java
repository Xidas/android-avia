package com.melorean.avia.model;

import lombok.Data;

@Data
public class Location {

    private String timeZone;
    private String name;
    private Boolean flightable;
    private Coordinate coordinates;
    private String code;
    private NameTranslation nameTranslation;
    private String countryCode;
    private String cityCode;
}
