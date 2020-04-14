package com.example.commonicon;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.commonicon.Retrofit.Repositorio;
import com.example.commonicon.Retrofit.RetrofitService;
import com.example.commonicon.Retrofit.Usuarios;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Registro extends AppCompatActivity {

    EditText txtNombre, txtEmail, txtContrasenna;
    Button button;
    boolean nombre =false, email=false, contrasenna=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtNombre = findViewById(R.id.txtRegistroNombre);
        txtEmail = findViewById(R.id.txtRegistroCorreo);
        txtContrasenna = findViewById(R.id.txtRegistroContrasenna);
        button = findViewById(R.id.buttRegistro);

        txtNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if((s.toString().trim().length()==0)||email==false||contrasenna==false){
                    button.setEnabled(false);
                } else {
                    button.setEnabled(true);
                }
                if(s.toString().trim().length()!=0){
                    nombre = true;
                }else{
                    nombre = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if((s.toString().trim().length()==0)||nombre==false||contrasenna==false){
                    button.setEnabled(false);
                } else {
                    button.setEnabled(true);
                }

                if(s.toString().trim().length()!=0){
                    email = true;
                }else{
                    email = false;
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
                if((s.toString().trim().length()==0)||email==false||nombre==false){
                    button.setEnabled(false);
                } else {
                    button.setEnabled(true);
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
    public void registro(View view){
        RetrofitService service = Repositorio.getUserService();
        service.insertUser(txtNombre.getText().toString(), txtEmail.getText().toString(), txtContrasenna.getText().toString()).enqueue(new Callback<Usuarios>() {

            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                if(response.body().getResponse().equals("Registrado correctamente")){
                    onBackPressed();
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                }else if(response.body().getResponse().equals("Ya hay un usuario con ese email")){
                    Toast.makeText(Registro.this, "Ya hay un usuario con ese email.", Toast.LENGTH_LONG).show();
                }else if(response.body().getResponse().equals("Ya hay un usuario con ese nombre")){
                    Toast.makeText(Registro.this, "Ya hay un usuario con ese nombre.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {
                Log.i("fallo", "onFailure: "+t);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }
}
