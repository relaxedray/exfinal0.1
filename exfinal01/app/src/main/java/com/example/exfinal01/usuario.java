package com.example.exfinal01;

import org.jetbrains.annotations.Nullable;

public class usuario {
    private int id;
    private String nombres;
    private String apellidos;
    private String email;
    private String cedula;
    private int role;
    private int active;
    private String created_at;
    private String updated_at;
    @Nullable
    private String email_verified_at;
    @Nullable
    Integer docente_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Nullable
    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(@Nullable String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    @Nullable
    public Integer getDocente_id() {
        return docente_id;
    }

    public void setDocente_id(@Nullable Integer docente_id) {
        this.docente_id = docente_id;
    }
}
