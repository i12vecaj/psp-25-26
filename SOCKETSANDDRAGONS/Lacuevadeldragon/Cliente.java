package Lacuevadeldragon;

import java.io.Serializable;

public class Cliente implements Serializable {

    private String rol;
    private String nombre;
    private String direccion;
    private int puerto;
    private boolean estado;
    private String mensaje;

    public Cliente(String rol, String nombre, String direccion, int puerto, boolean estado, String mensaje) {
        this.rol = rol;
        this.nombre = nombre;
        this.direccion = direccion;
        this.puerto = puerto;
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
