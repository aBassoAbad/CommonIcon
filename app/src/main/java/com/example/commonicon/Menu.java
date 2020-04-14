package com.example.commonicon;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.commonicon.Retrofit.Puntuaciones;
import com.example.commonicon.Retrofit.Repositorio;
import com.example.commonicon.Retrofit.RetrofitService;
import com.example.commonicon.Retrofit.Usuarios;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu extends AppCompatActivity {
    Dialog myDialog;

    MediaPlayer mpClick;
    String nombre;
    String email;

    Button buttMultijugador;
    TextView txtUsuario;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        preferences = getSharedPreferences("usuarioActual", Context.MODE_PRIVATE);

        nombre = preferences.getString("nombre","");
        email = preferences.getString("email","");

        buttMultijugador = findViewById(R.id.buttMultijugador);

        myDialog = new Dialog(this);
        mpClick = MediaPlayer.create(getApplicationContext(),R.raw.click);
        txtUsuario = findViewById(R.id.txtUsuario);

        txtUsuario.setText("Usuario actual: "+nombre);
    }

    public void datosUsuario(View view){
        mpClick.start();
        myDialog.getWindow().setGravity(Gravity.TOP);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.fondoOscuro)));
        myDialog.show();
        Button cerrarSesion;
        final TextView txtNombre, txtEmail, txtPuntuaciones;
        myDialog.setContentView(R.layout.popup_datos_usuario);
        cerrarSesion = myDialog.findViewById(R.id.buttCerrar);
        txtNombre = myDialog.findViewById(R.id.textNombre2);
        txtEmail = myDialog.findViewById(R.id.textEmail2);
        txtPuntuaciones = myDialog.findViewById(R.id.textPuntuaciones2);
        RetrofitService service = Repositorio.getUserService();
        service.getPuntuacionUsuario(nombre).enqueue(new Callback<List<Puntuaciones>>() {
            @Override
            public void onResponse(Call<List<Puntuaciones>> call, Response<List<Puntuaciones>> response) {
                String puntuaciones = "";
                for(int i = 0; i < response.body().size();i++){
                    puntuaciones += "Modo "+response.body().get(i).getModo()+": "+response.body().get(i).getPuntuacion()+" puntos\n";
                }
                if(puntuaciones.equals("")){
                    puntuaciones = "Tu cuenta no tiene registrada ninguna puntuación.";
                }
                txtPuntuaciones.setText(puntuaciones);
            }

            @Override
            public void onFailure(Call<List<Puntuaciones>> call, Throwable t) {

            }
        });
        txtNombre.setText(nombre);
        txtEmail.setText(email);
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpClick.start();
                SharedPreferences sharedPreferences = getSharedPreferences("usuarioActual", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("nombre", "");
                editor.putString("email", "");
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){

    }

    public void unJugador(View v){
        mpClick.start();
        myDialog.getWindow().setGravity(Gravity.TOP);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        Button opcion1, opcion2;
        myDialog.setContentView(R.layout.popup_un_jugador);
        opcion1 = (Button) myDialog.findViewById(R.id.opcion1);
        opcion2 = (Button) myDialog.findViewById(R.id.opcion2);
        opcion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpClick.start();
                Intent intent = new Intent(getApplicationContext(), ModoUnJugadorProgresivo.class);
                startActivity(intent);
            }
        });
        opcion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpClick.start();
                Intent intent = new Intent(getApplicationContext(), ModoUnJugadorUnMinuto.class);
                startActivity(intent);
            }
        });
    }

    public void dosJugadores(View v){
        mpClick.start();
        Intent intent = new Intent(this, ModoDosJugadores.class);
        startActivity(intent);
    }
}
