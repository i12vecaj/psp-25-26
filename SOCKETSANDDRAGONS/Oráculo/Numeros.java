package Oráculo;

import java.io.Serializable;

public class Numeros implements Serializable {

    private int num;

    private String direccion;
    private int puerto;

    private int cuadrado;
    private int cubo;

    public Numeros(int num, String direccion, int puerto) {
        this.num = num;
        this.direccion = direccion;
        this.puerto = puerto;

        this.cuadrado = 0;
        this.cubo = 0;

    }

    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public int getPuerto() {
        return puerto;
    }
    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }
    public int getCuadrado() {
        return cuadrado;
    }
    public void setCuadrado(int cuadrado) {
        this.cuadrado = cuadrado;
    }
    public int getCubo() {
        return cubo;
    }
    public void setCubo(int cubo) {
        this.cubo = cubo;
    }






}
