package com.example.commonicon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.commonicon.Retrofit.Repositorio;
import com.example.commonicon.Retrofit.RetrofitService;
import com.example.commonicon.Retrofit.Usuarios;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText txtCorreo, txtContrasenna;
    Button buttInicio;
    boolean correo = false;
    boolean contrasenna = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("usuarioActual", Context.MODE_PRIVATE);
        String nombre = preferences.getString("nombre","");
        String email = preferences.getString("email","");
        if(!nombre.equals("")){
            String[] datos = {nombre, email};
            Intent intent = new Intent(getApplicationContext(), Menu.class);
            intent.putExtra("datosUsuario", datos);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        }else{

            setContentView(R.layout.activity_login);

            txtCorreo = findViewById(R.id.txtCorreo);
            txtContrasenna = findViewById(R.id.txtContrasenna);
            buttInicio = findViewById(R.id.buttInicioSesion);


            txtCorreo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if((s.toString().trim().length()==0)||contrasenna==false){
                        buttInicio.setEnabled(false);
                    } else {
                        buttInicio.setEnabled(true);
                    }
                    if(s.toString().trim().length()!=0){
                        correo = true;
                    }else{
                        correo = false;
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            txtContrasenna.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if((s.toString().trim().length()==0)||correo==false){
                        buttInicio.setEnabled(false);
                    } else {
                        buttInicio.setEnabled(true);
                    }

                    if(s.toString().trim().length()!=0){
                        contrasenna = true;
                    }else{
                        contrasenna = false;
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }


    }

    public void registrar(View view){
        Intent intent = new Intent(getApplicationContext(), Registro.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void iniciarSesion(View view) {
        RetrofitService service = Repositorio.getUserService();
        service.getUsuarioComprobar(txtCorreo.getText().toString(), txtContrasenna.getText().toString()).enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                if (response.body().getResponse().equals("no existe")) {
                    Toast.makeText(Login.this, "No existe ningún usuario con ese email.", Toast.LENGTH_LONG).show();
                } else if (response.body().getResponse().equals("no coincide")) {
                    Toast.makeText(Login.this, "Contraseña incorrecta.", Toast.LENGTH_LONG).show();
                } else {
                    RetrofitService service = Repositorio.getUserService();
                    service.getUsuariosEmail(txtCorreo.getText().toString()).enqueue(new Callback<Usuarios>() {
                        @Override
                        public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                            SharedPreferences sharedPreferences = getSharedPreferences("usuarioActual", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("nombre", response.body().getNombre());
                            editor.putString("email", response.body().getEmail());
                            editor.commit();
                            Intent intent = new Intent(getApplicationContext(), Menu.class);
                            String[] datos = {response.body().getNombre(), response.body().getEmail()};
                            intent.putExtra("datosUsuario", datos);
                            startActivity(intent);
                            overridePendingTransition(R.anim.left_in, R.anim.left_out);
                        }

                        @Override
                        public void onFailure(Call<Usuarios> call, Throwable t) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {

            }
        });
    }
}
