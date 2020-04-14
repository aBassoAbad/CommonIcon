package com.example.commonicon;

public class Carta {
    private String dibujo1;
    private String dibujo2;
    private String dibujo3;
    private String dibujo4;
    private String dibujo5;
    private String dibujo6;

    public Carta(){}

    public Carta( String dibujo1, String dibujo2, String dibujo3, String dibujo4, String dibujo5, String dibujo6) {
        this.dibujo1 = dibujo1;
        this.dibujo2 = dibujo2;
        this.dibujo3 = dibujo3;
        this.dibujo4 = dibujo4;
        this.dibujo5 = dibujo5;
        this.dibujo6 = dibujo6;
    }

    public String getDibujo1() {
        return dibujo1;
    }

    public void setDibujo1(String dibujo1) {
        this.dibujo1 = dibujo1;
    }

    public String getDibujo2() {
        return dibujo2;
    }

    public void setDibujo2(String dibujo2) {
        this.dibujo2 = dibujo2;
    }

    public String getDibujo3() {
        return dibujo3;
    }

    public void setDibujo3(String dibujo3) {
        this.dibujo3 = dibujo3;
    }

    public String getDibujo4() {
        return dibujo4;
    }

    public void setDibujo4(String dibujo4) {
        this.dibujo4 = dibujo4;
    }

    public String getDibujo5() {
        return dibujo5;
    }

    public void setDibujo5(String dibujo5) {
        this.dibujo5 = dibujo5;
    }

    public String getDibujo6() {
        return dibujo6;
    }

    public void setDibujo6(String dibujo6) {
        this.dibujo6 = dibujo6;
    }

    public String[] getDibujos(){
        String[] dibujos = new String[6];
        dibujos[0] = this.dibujo1;
        dibujos[1] = this.dibujo2;
        dibujos[2] = this.dibujo3;
        dibujos[3] = this.dibujo4;
        dibujos[4] = this.dibujo5;
        dibujos[5] = this.dibujo6;
        return dibujos;
    }

    @Override
    public String toString() {
        return this.dibujo1+", "+this.dibujo2+", "+this.dibujo3+", "+this.dibujo4+", "+this.dibujo5+", "+this.dibujo6;
    }
}
