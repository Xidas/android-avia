package com.melorean.avia.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Location {

    @JsonProperty("time_zone")
    private String timeZone;
    private String name;
    private Boolean flightable;
    private Coordinate coordinates;
    private String code;
    @JsonProperty("name_translations")
    private NameTranslation nameTranslation;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("city_code")
    private String cityCode;
}
