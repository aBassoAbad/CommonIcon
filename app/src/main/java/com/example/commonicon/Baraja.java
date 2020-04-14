package com.example.commonicon;

import java.util.ArrayList;

public class Baraja {
    private ArrayList<Carta> cartas;

    public Baraja() {
        cartas = new ArrayList<Carta>();
    }

    public void crearBaraja(){
        int n = 5;
        //Primera carta
        String[] simbolos = new String[n+1];
        for(int i = 0; i <= n; i++) {
            simbolos[i] = (i+1)+"";
        }
        cartas.add(new Carta(simbolos[0],simbolos[1],simbolos[2],simbolos[3],simbolos[4],simbolos[5]));
        System.out.println();
        //Siguientes n cartas
        for(int i = 0; i < n; i++) {
            simbolos = new String[n+1];
            simbolos[0] = 1+"";
            for(int j = 0; j < n; j++) {
                simbolos[j+1] = n+1 + n*i +j+1+"";
            }
            cartas.add(new Carta(simbolos[0],simbolos[1],simbolos[2],simbolos[3],simbolos[4],simbolos[5]));
        }
        //Resto de cartas
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                simbolos = new String[n+1];
                simbolos[0] = i+2+"";
                for (int k = 0; k < n; k++) {
                    simbolos[k+1] = n+1 + n*k + (i*k+j)%n+1+"";
                }
                cartas.add(new Carta(simbolos[0],simbolos[1],simbolos[2],simbolos[3],simbolos[4],simbolos[5]));
            }
        }
    }

    public void mostrarBaraja(){
        for(int i = 0; i < cartas.size(); i++){
            System.out.println(cartas.get(i));
        }
    }

    public void barajar() {
        Carta[] aux = new Carta[this.cartas.size()];
        for(int i = 0; i < aux.length; i++) {
            boolean esta = false;
            do {
                int posAleatoria = (int)(Math.random()*aux.length);
                if(aux[posAleatoria] == null) {
                    esta = true;
                    aux[posAleatoria] = this.cartas.get(i);
                }
            }while(!esta);
        }

        this.cartas = new ArrayList<Carta>();
        for(int i = 0; i < aux.length; i++) {
            this.cartas.add(aux[i]);
        }
    }

    public Carta[] sacar2Cartas() {
        Carta[] cartas = new Carta[2];
        Carta cartaRepartida1 = this.cartas.get(0);
        Carta cartaRepartida2 = this.cartas.get(1);

        cartas[0] = cartaRepartida1;
        cartas[1] = cartaRepartida2;

        this.cartas.remove(0);
        this.cartas.remove(0);

        return cartas;
    }

    public Carta sacarCarta() {
        Carta cartaRepartida = this.cartas.get(0);
        this.cartas.remove(0);
        return cartaRepartida;
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }
}
