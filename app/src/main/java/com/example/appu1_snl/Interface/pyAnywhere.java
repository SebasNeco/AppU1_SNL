package com.example.appu1_snl.Interface;

import com.example.appu1_snl.model.Post;
import com.example.appu1_snl.model.generandoJson;



import retrofit2.Call;
import retrofit2.http.GET;

public interface pyAnywhere {
    @GET("/generandojson")
    Call<generandoJson> obtenerJsonGenerado();
}
