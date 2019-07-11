package com.melorean.avia.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class LocationItem {

    @SerializedName("name")
    @Expose
    private String locName;
    @SerializedName("code")
    @Expose
    private String locCode;

    @SerializedName("name_translations")
    @Expose
    private NameTranslation nameTranslation;
}
