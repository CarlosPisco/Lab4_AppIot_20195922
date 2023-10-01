package com.example.contactos_y_sensores.services;

import com.example.contactos_y_sensores.dto.Perfil;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProfileService {

    //Separar la URL base del URL path
    @GET("/api/")
    Call<Perfil> getPerfil();


}
