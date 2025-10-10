package com.example.appu1_snl.Interface;

import com.example.appu1_snl.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("/posts")
    Call<List<Post>> obtenerPublicaciones();
}
