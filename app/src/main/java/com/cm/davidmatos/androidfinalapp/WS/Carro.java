package com.cm.davidmatos.androidfinalapp.WS;

public class Carro {

    private int idCarro;
    private String marca;
    private String modelo;
    private int combustivel;
    private double consumo;
    private int fkUser;
    private int estado;

    public Carro ( int id, String marca, String modelo, int combustivel, double consumo, int fkUser, int estado) {
        this.idCarro = id;
        this.marca = marca;
        this.modelo = modelo;
        this.combustivel = combustivel;
        this.consumo = consumo;
        this.fkUser = fkUser;
        this.estado = estado;
    }

    public int getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(int idCarro) {
        this.idCarro = idCarro;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(int combustivel) {
        this.combustivel = combustivel;
    }

    public double getConsumo() {
        return consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    public int getFkUser() {
        return fkUser;
    }

    public void setFkUser(int fkUser) {
        this.fkUser = fkUser;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
