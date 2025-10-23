package com.example.appu1_snl.Interface;

import com.example.appu1_snl.model.AuthRequest;
import com.example.appu1_snl.model.AuthResponse;
import com.example.appu1_snl.model.GuardarPersonaRequest;
import com.example.appu1_snl.model.RptaGeneral;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DAMusatAPI {
    @POST("auth")
    Call<AuthResponse> obtenerToken(@Body AuthRequest authRequest);

    @GET("generandojson")
    Call<RptaGeneral> obtenerDataJson(@Header("Authorization") String authorization);

    @POST ("api_guardar_persona")
    Call<RptaGeneral> guardarPersona(@Header("Authorization") String authorization,
                                     @Body GuardarPersonaRequest body);
    @GET("api_obtenerPersonas")
    Call<RptaGeneral> obtenerPersonas();
}
