package com.example.mykitchen.network;

import com.example.mykitchen.pojo.Hits;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeAPI {
    @GET("search?app_id=3032b46c&app_key=039f5db0b7ad60f1f3e9536166884084")
    Call<Hits> getRecipes (@Query("q") String q );
}
