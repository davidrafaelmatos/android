package com.cm.davidmatos.androidfinalapp.WS;

public class User {

    private int idUser;
    private String username;
    private String password;
    private String nome;
    private String email;
    private int estado;

    public User (int id, String username, String password, String nome, String email, int estado) {
        this.idUser = id;
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.email = email;
        this.estado = estado;
    }

    public User() {
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

}
