package com.iti.java.foodplannerbykhalidamr.home.model;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSource {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static MealsRemoteDataSource mealsRemoteDataSource = null;
    public ApiService apiService;
    private MealsRemoteDataSource(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
                apiService= retrofit.create(ApiService.class);
    }
    public static MealsRemoteDataSource getApiService() {
        if (mealsRemoteDataSource == null) {
            mealsRemoteDataSource = new MealsRemoteDataSource();
        }
        return mealsRemoteDataSource;
    }


}
