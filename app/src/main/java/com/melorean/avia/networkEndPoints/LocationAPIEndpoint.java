package com.melorean.avia.networkEndPoints;

import com.melorean.avia.model.LocationItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LocationAPIEndpoint {

    @GET("/data/ru/airports.json")
    Call<List<LocationItem>> getAirportItems();

    @GET("/data/ru/cities.json")
    Call<List<LocationItem>> getCitiesItems();
}
