package com.melorean.avia.service;

import com.melorean.avia.networkEndPoints.LocationAPIEndpoint;

import javax.inject.Singleton;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class NetworkService {

    private static NetworkService networkService;
    private Retrofit retrofit;

    public NetworkService(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public LocationAPIEndpoint getLocationAPIEndpoint(){
        return retrofit.create(LocationAPIEndpoint.class);
    }


}
