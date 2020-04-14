package com.example.commonicon.Retrofit;

import com.google.gson.annotations.SerializedName;

public class Usuarios {

    @SerializedName("nombre")
    private String Nombre;
    @SerializedName("email")
    private String email;
    private String contrasenna;
    @SerializedName("response")
    private String response;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String emil) {
        this.email = emil;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public String getResponse() {
        return response;
    }
}
