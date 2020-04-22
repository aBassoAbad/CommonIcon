package com.example.commonicon.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("pruebaConsulta.php")
    Call<Usuarios> getUsuarios();

    @GET("obtenerUsuariosEmail.php")
    Call<Usuarios> getUsuariosEmail(@Query("email_param") String nombre);

    @GET("registro.php")
    Call<Usuarios> insertUser(
            @Query("nombre") String nombre,
            @Query("email") String email,
            @Query("contrasenna") String contrasenna
    );

    @GET("insertarMarcador.php")
    Call<Usuarios> insertarPuntuacion(
            @Query("puntuacion") int puntuacion,
            @Query("modo") String modo,
            @Query("nombreusuario") String usuario
    );

    @GET("comprobarExiste.php")
    Call<Usuarios> getUsuarioComprobar(@Query("email") String email, @Query("contrasenna") String contrasenna);

    @GET("obtenerPuntuacionesUsuario.php")
    Call<List<Puntuaciones>> getPuntuacionUsuario(@Query("usuario") String usuario);

    @GET("obtenerPuntuacionesProgresivo.php")
    Call<List<Puntuaciones>> getPuntuacionProgresivo();

    @GET("obtenerPuntuaciones1Minuto.php")
    Call<List<Puntuaciones>> getPuntuacion1Minuto();
}
