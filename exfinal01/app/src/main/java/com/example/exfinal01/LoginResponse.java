package com.example.exfinal01;

import java.util.ArrayList;
import java.util.List;

public class LoginResponse {


    private String title;
    private String mensaje;
    private String route;
    usuario usuario;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public com.example.exfinal01.usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(com.example.exfinal01.usuario usuario) {
        this.usuario = usuario;
    }
}

