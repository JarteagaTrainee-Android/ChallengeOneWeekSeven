package com.applaudostudio.weeksevenchallengeone.apiclient;

import com.applaudostudio.weeksevenchallengeone.model.gsonmodels.JsonModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotosService {
    //?sol={sol}&page={page}&api_key={key}
    @GET("curiosity/photos")
    Call<JsonModel> getPhotos(@Query("sol") int sol, @Query("page") int page, @Query("api_key") String key);
}
