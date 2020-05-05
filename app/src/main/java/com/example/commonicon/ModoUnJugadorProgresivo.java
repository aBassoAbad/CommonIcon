package com.example.commonicon;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.commonicon.Retrofit.Repositorio;
import com.example.commonicon.Retrofit.RetrofitService;
import com.example.commonicon.Retrofit.Usuarios;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModoUnJugadorProgresivo extends AppCompatActivity {

    ImageButton button, button2, button3, button4, button5, button6;
    ImageView image1, image2, image3, image4, image5, image6, carta2;
    ProgressBar p;
    Carta[] cartasEnMesa;
    Baraja baraja;
    TextView pts, puntos;
    int contador;
    int milis = 50;
    int i = 0;
    int cuentaAtras = 0;
    boolean repetido = false;
    Dialog myDialog;
    Thread hilo;
    MediaPlayer mediaPlayer;
    SharedPreferences preferences;
    int[] sonidos = {R.raw.click, R.raw.correcto, R.raw.incorrecto, R.raw.cuenta_atras};
    String usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_un_jugador_progresivo);
        preferences = getSharedPreferences("usuarioActual", Context.MODE_PRIVATE);
        usuario = preferences.getString("nombre","basso");
        myDialog = new Dialog(this);
        pts = findViewById(R.id.txtPts);
        puntos = findViewById(R.id.txtPuntos);
        contador = 0;
        carta2 = findViewById(R.id.imageView2);

        this.button = findViewById(R.id.imageButton1);
        this.button2 = findViewById(R.id.imageButton2);
        this.button3 = findViewById(R.id.imageButton3);
        this.button4 = findViewById(R.id.imageButton4);
        this.button5 = findViewById(R.id.imageButton5);
        this.button6 = findViewById(R.id.imageButton6);

        this.image1 = (ImageView) findViewById(R.id.image1);
        this.image2 = (ImageView) findViewById(R.id.image2);
        this.image3 = (ImageView) findViewById(R.id.image3);
        this.image4 = (ImageView) findViewById(R.id.image4);
        this.image5 = (ImageView) findViewById(R.id.image5);
        this.image6 = (ImageView) findViewById(R.id.image6);

        p = (ProgressBar)  findViewById(R.id.progressBar);

        myDialog.getWindow().setGravity(Gravity.TOP);
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

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                baraja = new Baraja();
                baraja.crearBaraja();
                baraja.barajar();
                cartasEnMesa = baraja.sacar2Cartas();
                baraja.getCartas().add(cartasEnMesa[0]);
                baraja.getCartas().add(cartasEnMesa[1]);
                dibujarCartas();
            }
        });
    }

    public void pulsarIcono (View v){
        ImageButton button = (ImageButton)v;
        String nombreBoton = getResources().getResourceName(button.getId());

        String numeroIcono = nombreBoton.substring(nombreBoton.length()-1);

        repetido = false;


        for(int i = 0; i < 6; i++){
            if(cartasEnMesa[0].getDibujos()[Integer.parseInt(numeroIcono)-1].equals(cartasEnMesa[1].getDibujos()[i])){
                repetido = true;
                hilo.interrupt();
            }
        }

        if(repetido){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            playMedia(1);

            contador++;
            milis -= 0.5;
            pts.setText(String.valueOf(contador));
            cartasEnMesa[1] = cartasEnMesa[0];
            Carta cartaRepartida = new Carta();
            do{
                baraja.barajar();
                cartaRepartida = baraja.sacarCarta();
                baraja.getCartas().add(cartaRepartida);
            }while(cartaRepartida.equals(cartasEnMesa[1]));
            cartasEnMesa[0] = cartaRepartida;
            dibujarCartas();
            repetido = false;
        }else{
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            playMedia(2);

            if(contador > 0) {
                contador--;
            }
            pts.setText(String.valueOf(contador));
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
                image1.setRotation(direccion2);
                draw = getResources().getDrawable(idImagen1);
                draw2 = getResources().getDrawable(idImagen7);

                int tamanio = (int) (Math.random() * 2 + 1);
                switch (tamanio) {
                    case 1:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 70, 70, true);

                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 50, 50, true);

                        break;
                    case 2:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 65, 65, true);
                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 50, 50, true);
                        break;
                }

                button.setImageBitmap(newbitMap);
                image1.setImageBitmap(newbitMap2);
            } else if (i == 1) {
                button2.setRotation(direccion);
                image2.setRotation(direccion);
                draw = getResources().getDrawable(idImagen2);
                draw2 = getResources().getDrawable(idImagen8);

                int tamanio = (int) (Math.random() * 2 + 1);
                switch (tamanio) {
                    case 1:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 70, 70, true);

                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 50, 50, true);

                        break;
                    case 2:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 65, 65, true);
                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 50, 50, true);
                        break;
                }

                button2.setImageBitmap(newbitMap);
                image2.setImageBitmap(newbitMap2);
                carta2.post(new Runnable() {
                    @Override
                    public void run() {
                        int height = carta2.getHeight(); //height is ready
                        int width = carta2.getWidth();


                        RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                        rel_btn.leftMargin = (width /10)*6+button2.getWidth()/2;
                        rel_btn.topMargin = (height /10)*3;

                        button2.setLayoutParams(rel_btn);
                    }
                });
            } else if (i == 2) {
                button3.setRotation(direccion);
                image3.setRotation(direccion);
                draw = getResources().getDrawable(idImagen3);
                draw2 = getResources().getDrawable(idImagen9);
                int tamanio = (int) (Math.random() * 2 + 1);
                switch (tamanio) {
                    case 1:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 70, 70, true);

                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 50, 50, true);

                        break;
                    case 2:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 65, 65, true);
                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 50, 50, true);
                        break;
                }

                button3.setImageBitmap(newbitMap);
                image3.setImageBitmap(newbitMap2);
                carta2.post(new Runnable() {
                    @Override
                    public void run() {
                        int height = carta2.getHeight(); //height is ready
                        int width = carta2.getWidth();


                        RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                        rel_btn.leftMargin = (width /7)*2;
                        rel_btn.topMargin = (height /10)*7;

                        button3.setLayoutParams(rel_btn);
                    }
                });
            } else if (i == 3) {
                button4.setRotation(direccion);
                image4.setRotation(direccion);
                draw = getResources().getDrawable(idImagen4);
                draw2 = getResources().getDrawable(idImagen10);
                int tamanio = (int) (Math.random() * 2 + 1);
                switch (tamanio) {
                    case 1:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 70, 70, true);

                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 50, 50, true);

                        break;
                    case 2:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 65, 65, true);
                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 50, 50, true);
                        break;
                }

                button4.setImageBitmap(newbitMap);
                image4.setImageBitmap(newbitMap2);
                carta2.post(new Runnable() {
                    @Override
                    public void run() {
                        int height = carta2.getHeight(); //height is ready
                        int width = carta2.getWidth();


                        RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                        rel_btn.leftMargin = (width /15)+button4.getWidth()/2;
                        rel_btn.topMargin = (height /10)*3;

                        button4.setLayoutParams(rel_btn);
                    }
                });
            } else if (i == 4) {
                button5.setRotation(direccion);
                image5.setRotation(direccion);
                draw = getResources().getDrawable(idImagen5);
                draw2 = getResources().getDrawable(idImagen11);
                int tamanio = (int) (Math.random() * 2 + 1);
                switch (tamanio) {
                    case 1:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 70, 70, true);

                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 50, 50, true);

                        break;
                    case 2:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 65, 65, true);
                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 50, 50, true);
                        break;
                }

                button5.setImageBitmap(newbitMap);
                image5.setImageBitmap(newbitMap2);
                carta2.post(new Runnable() {
                    @Override
                    public void run() {
                        int height = carta2.getHeight(); //height is ready
                        int width = carta2.getWidth();


                        RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                        rel_btn.topMargin = (height /10)*2-button5.getHeight()/2;
                        rel_btn.leftMargin = (width /10)*5-button5.getWidth()/2;

                        button5.setLayoutParams(rel_btn);
                    }
                });
            } else {
                button6.setRotation(direccion);
                image6.setRotation(direccion);
                draw = getResources().getDrawable(idImagen6);
                draw2 = getResources().getDrawable(idImagen12);
                int tamanio = (int) (Math.random() * 2 + 1);
                switch (tamanio) {
                    case 1:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 70, 70, true);

                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 50, 50, true);

                        break;
                    case 2:
                        bitmap = ((BitmapDrawable) draw).getBitmap();
                        newbitMap = Bitmap.createScaledBitmap(bitmap, 65, 65, true);
                        bitmap2 = ((BitmapDrawable) draw2).getBitmap();
                        newbitMap2 = Bitmap.createScaledBitmap(bitmap2, 50, 50, true);
                        break;
                }

                button6.setImageBitmap(newbitMap);
                image6.setImageBitmap(newbitMap2);
                carta2.post(new Runnable() {
                    @Override
                    public void run() {
                        int height = carta2.getHeight(); //height is ready
                        int width = carta2.getWidth();


                        RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                        rel_btn.leftMargin = (width /10)*6;
                        rel_btn.topMargin = (height /10)*7-button6.getWidth()/2;

                        button6.setLayoutParams(rel_btn);
                    }
                });
            }
        }

        hilo = new Thread(new Runnable() {
            public boolean exit = false;

            @Override
            public void run() {
                try {
                    if (cuentaAtras == 0){
                        Thread.sleep(1800);
                        playMedia(3);
                        Thread.sleep(3200);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                cuentaAtras = 1;
                i = 0;
                while ((i < 100) && !exit) {
                    p.setProgress(i);
                    try {
                        Thread.sleep(milis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                    if (repetido == true) {
                        detener();
                    }
                }
                if(i == 100){

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

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
                            textView.setText("¡Se ha acabado el tiempo!\nTienes "+contador+" puntos");
                            menu.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    playMedia(0);
                                    registrarPuntuacion();
                                    Intent intent = new Intent(getApplicationContext(), Menu.class);
                                    startActivity(intent);
                                }
                            });
                            reiniciar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    playMedia(0);
                                    registrarPuntuacion();
                                    Intent intent = new Intent(getApplicationContext(), ModoUnJugadorProgresivo.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                }
            }
            public void detener(){
                exit = true;
            }
        });

        hilo.start();
    }

    @Override
    public void onBackPressed(){

    }

    public void playMedia(int media){

        mediaPlayer = MediaPlayer.create(getApplicationContext(), sonidos[media]);
        mediaPlayer.start();

    }

    public void registrarPuntuacion(){

        RetrofitService service = Repositorio.getUserService();
        service.insertarPuntuacion(contador,"progresivo",usuario).enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                System.out.println(usuario+"--------------------------------------------");
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {

            }
        });
    }

}
