package com.example.commonicon;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ModoDosJugadores extends AppCompatActivity {


    Dialog myDialog;
    ImageButton button, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12;
    Carta[] cartasEnMesa;
    Baraja baraja;
    boolean repetido = false;
    TextView ptsJug1, ptsJug2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_dos_jugadores);

        myDialog = new Dialog(this);

        ptsJug1 = findViewById(R.id.ptJug1);
        ptsJug2 = findViewById(R.id.ptJug2);

        this.button = findViewById(R.id.imageButton1);
        this.button2 = findViewById(R.id.imageButton2);
        this.button3 = findViewById(R.id.imageButton3);
        this.button4 = findViewById(R.id.imageButton4);
        this.button5 = findViewById(R.id.imageButton5);
        this.button6 = findViewById(R.id.imageButton6);
        this.button7 = findViewById(R.id.imageButton11);
        this.button8 = findViewById(R.id.imageButton12);
        this.button9 = findViewById(R.id.imageButton13);
        this.button10 = findViewById(R.id.imageButton14);
        this.button11 = findViewById(R.id.imageButton15);
        this.button12 = findViewById(R.id.imageButton16);

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.setCancelable(false);
        myDialog.show();
        final TextView text;
        myDialog.setContentView(R.layout.popup_cuenta_atras);
        text = (TextView) myDialog.findViewById(R.id.txtCuentaAtras);
        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                text.setText(String.format(Locale.getDefault(), "%d", millisUntilFinished / 1000L+1));
            }

            public void onFinish() {
                myDialog.cancel();
            }
        }.start();

        baraja = new Baraja();
        baraja.crearBaraja();
        baraja.barajar();
        cartasEnMesa = baraja.sacar2Cartas();
        dibujarCartas();
    }

    public void pulsarIconoCarta1 (View v){
        ImageButton button = (ImageButton)v;
        String nombreBoton = getResources().getResourceName(button.getId());

        String numeroIcono = nombreBoton.substring(nombreBoton.length()-1);

        repetido = false;


        for(int i = 0; i < 6; i++){
            if(cartasEnMesa[0].getDibujos()[Integer.parseInt(numeroIcono)-1].equals(cartasEnMesa[1].getDibujos()[i])){
                repetido = true;
            }
        }

        if(repetido){
            int pts2 = Integer.parseInt(ptsJug2.getText().toString());
            pts2++;
            ptsJug2.setText(String.valueOf(pts2));
            if(baraja.getCartas().size()> 0) {
                cartasEnMesa[0] = baraja.sacarCarta();
                dibujarCartas();
                repetido = false;
            }else {
                if(Integer.parseInt(ptsJug1.getText().toString()) < Integer.parseInt(ptsJug2.getText().toString())){
                    ptsJug1.setTextColor(Color.RED);
                    ptsJug2.setTextColor(Color.GREEN);
                }else if(Integer.parseInt(ptsJug2.getText().toString()) < Integer.parseInt(ptsJug1.getText().toString())){
                    ptsJug2.setTextColor(Color.RED);
                    ptsJug1.setTextColor(Color.GREEN);
                }
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.setCanceledOnTouchOutside(false);
                myDialog.setCancelable(false);
                myDialog.show();
                Button menu, reiniciar;
                TextView textView;
                myDialog.setContentView(R.layout.popup_fin);
                textView = (TextView) myDialog.findViewById(R.id.txtPuntos);
                menu = (Button) myDialog.findViewById(R.id.menu);
                reiniciar = (Button) myDialog.findViewById(R.id.reiniciar);
                textView.setText("¡Se han acabado las cartas!");
                menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Menu.class);
                        startActivity(intent);
                    }
                });
                reiniciar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ModoDosJugadores.class);
                        startActivity(intent);
                    }
                });
            }
        }
    }
    public void pulsarIconoCarta2 (View v){
        ImageButton button = (ImageButton)v;
        String nombreBoton = getResources().getResourceName(button.getId());

        String numeroIcono = nombreBoton.substring(nombreBoton.length()-1);

        repetido = false;


        for(int i = 0; i < 6; i++){
            if(cartasEnMesa[1].getDibujos()[Integer.parseInt(numeroIcono)-1].equals(cartasEnMesa[0].getDibujos()[i])){
                repetido = true;
            }
        }

        if(repetido){
            int pts1 = Integer.parseInt(ptsJug1.getText().toString());
            pts1++;
            ptsJug1.setText(String.valueOf(pts1));
            cartasEnMesa[1] = baraja.sacarCarta();
            dibujarCartas();
            repetido = false;
        }
    }
    public void dibujarCartas(){
        int idImagen1 = getResources().getIdentifier("imagen" + cartasEnMesa[0].getDibujo1(), "mipmap", getPackageName());
        int idImagen2 = getResources().getIdentifier("imagen" + cartasEnMesa[0].getDibujo2(), "mipmap", getPackageName());
        int idImagen3 = getResources().getIdentifier("imagen" + cartasEnMesa[0].getDibujo3(), "mipmap", getPackageName());
        int idImagen4 = getResources().getIdentifier("imagen" + cartasEnMesa[0].getDibujo4(), "mipmap", getPackageName());
        int idImagen5 = getResources().getIdentifier("imagen" + cartasEnMesa[0].getDibujo5(), "mipmap", getPackageName());
        int idImagen6 = getResources().getIdentifier("imagen" + cartasEnMesa[0].getDibujo6(), "mipmap", getPackageName());

        int idImagen7 = getResources().getIdentifier("imagen" + cartasEnMesa[1].getDibujo1(), "mipmap", getPackageName());
        int idImagen8 = getResources().getIdentifier("imagen" + cartasEnMesa[1].getDibujo2(), "mipmap", getPackageName());
        int idImagen9 = getResources().getIdentifier("imagen" + cartasEnMesa[1].getDibujo3(), "mipmap", getPackageName());
        int idImagen10 = getResources().getIdentifier("imagen" + cartasEnMesa[1].getDibujo4(), "mipmap", getPackageName());
        int idImagen11 = getResources().getIdentifier("imagen" + cartasEnMesa[1].getDibujo5(), "mipmap", getPackageName());
        int idImagen12 = getResources().getIdentifier("imagen" + cartasEnMesa[1].getDibujo6(), "mipmap", getPackageName());

        Drawable draw = null;
        Drawable draw2 = null;
        Bitmap bitmap = null;
        Bitmap newbitMap = null;
        Bitmap bitmap2 = null;
        Bitmap newbitMap2 = null;

        for (int i = 0; i < 6; i++) {
            int direccion = (int) (Math.random() * 7 + 1);
            direccion = direccion * 45;
            int direccion2 = (int) (Math.random() * 7 + 1);
            direccion2 = direccion2 * 45;
            if (i == 0) {
                button.setRotation(direccion);
                button7.setRotation(direccion2);
                draw = getResources().getDrawable(idImagen1);
                draw2 = getResources().getDrawable(idImagen7);


                int tamanio = (int) (Math.random() * 2 + 1);
                switch (tamanio) {
                    case 1:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 50, 50, true);

                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 50, 50, true);

                        break;
                    case 2:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 75, 75, true);
                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 75, 75, true);
                        break;
                }

                button.setImageBitmap(newbitMap);
                button7.setImageBitmap(newbitMap2);
            } else if (i == 1) {
                button2.setRotation(direccion);
                button8.setRotation(direccion);
                draw = getResources().getDrawable(idImagen2);
                draw2 = getResources().getDrawable(idImagen8);
                int tamanio = (int) (Math.random() * 2 + 1);
                switch (tamanio) {
                    case 1:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 50, 50, true);

                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 50, 50, true);

                        break;
                    case 2:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 75, 75, true);
                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 75, 75, true);
                        break;
                }

                button2.setImageBitmap(newbitMap);
                button8.setImageBitmap(newbitMap2);
            } else if (i == 2) {
                button3.setRotation(direccion);
                button9.setRotation(direccion);
                draw = getResources().getDrawable(idImagen3);
                draw2 = getResources().getDrawable(idImagen9);
                int tamanio = (int) (Math.random() * 2 + 1);
                switch (tamanio) {
                    case 1:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 50, 50, true);

                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 50, 50, true);

                        break;
                    case 2:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 75, 75, true);
                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 75, 75, true);
                        break;
                }

                button3.setImageBitmap(newbitMap);
                button9.setImageBitmap(newbitMap2);
            } else if (i == 3) {
                button4.setRotation(direccion);
                button10.setRotation(direccion);
                draw = getResources().getDrawable(idImagen4);
                draw2 = getResources().getDrawable(idImagen10);
                int tamanio = (int) (Math.random() * 2 + 1);
                switch (tamanio) {
                    case 1:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 50, 50, true);

                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 50, 50, true);

                        break;
                    case 2:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 75, 75, true);
                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 75, 75, true);
                        break;
                }

                button4.setImageBitmap(newbitMap);
                button10.setImageBitmap(newbitMap2);
            } else if (i == 4) {
                button5.setRotation(direccion);
                button11.setRotation(direccion);
                draw = getResources().getDrawable(idImagen5);
                draw2 = getResources().getDrawable(idImagen11);
                int tamanio = (int) (Math.random() * 2 + 1);
                switch (tamanio) {
                    case 1:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 50, 50, true);

                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 50, 50, true);

                        break;
                    case 2:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 75, 75, true);
                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 75, 75, true);
                        break;
                }

                button5.setImageBitmap(newbitMap);
                button11.setImageBitmap(newbitMap2);
            } else {
                button6.setRotation(direccion);
                button12.setRotation(direccion);
                draw = getResources().getDrawable(idImagen6);
                draw2 = getResources().getDrawable(idImagen12);
                int tamanio = (int) (Math.random() * 2 + 1);
                switch (tamanio) {
                    case 1:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 50, 50, true);

                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 50, 50, true);

                        break;
                    case 2:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 75, 75, true);
                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 75, 75, true);
                        break;
                }

                button6.setImageBitmap(newbitMap);
                button12.setImageBitmap(newbitMap2);
            }
        }
    }

    @Override
    public void onBackPressed(){

    }
}
