package com.surrussent.pokemon.API;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    // Use method GET with Path url
    @GET("type/{type_name}/")
    Call<Map> getType(@Path("type_name") String type_name);

}
