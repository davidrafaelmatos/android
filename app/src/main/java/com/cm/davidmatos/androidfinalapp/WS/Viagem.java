package com.cm.davidmatos.androidfinalapp.WS;

public class Viagem {

    private int idViagem;
    private String origemNome;
    private String origemCoordLat;
    private String origemCoordLong;
    private String destinoNome;
    private String destinoCoordLat;
    private String destinoCoordLong;
    private int fkCar;
    private int fkUser;
    private double totalKm;
    private int estado;
    private int quantidadeLugares;
    private int lugaresDisponiveis;
    private String dataViagem;

    public Viagem ( int idViagem, String origemNome, String origemCoordLat, String origemCoordLong, String destinoNome, String destinoCoordLat, String destinoCoordLong, int fkCar, int fkUser, double totalKm, int estado, int quantidadeLugares, int lugaresDisponiveis, String dataViagem) {
        this.idViagem = idViagem;
        this.origemNome = origemNome;
        this.origemCoordLat = origemCoordLat;
        this.origemCoordLong = origemCoordLong;
        this.destinoNome = destinoNome;
        this.destinoCoordLat = destinoCoordLat;
        this.destinoCoordLong = destinoCoordLong;
        this.fkCar = fkCar;
        this.fkUser = fkUser;
        this.totalKm = totalKm;
        this.estado = estado;
        this.quantidadeLugares = quantidadeLugares;
        this.lugaresDisponiveis = lugaresDisponiveis;
        this.dataViagem = dataViagem;
    }

    public int getIdViagem() {
        return idViagem;
    }

    public void setIdViagem(int idViagem) {
        this.idViagem = idViagem;
    }

    public String getOrigemNome() {
        return origemNome;
    }

    public void setOrigemNome(String origemNome) {
        this.origemNome = origemNome;
    }

    public String getOrigemCoordLat() {
        return origemCoordLat;
    }

    public void setOrigemCoordLat(String origemCoordLat) {
        this.origemCoordLat = origemCoordLat;
    }

    public String getOrigemCoordLong() {
        return origemCoordLong;
    }

    public void setOrigemCoordLong(String origemCoordLong) {
        this.origemCoordLong = origemCoordLong;
    }

    public String getDestinoNome() {
        return destinoNome;
    }

    public void setDestinoNome(String destinoNome) {
        this.destinoNome = destinoNome;
    }

    public String getDestinoCoordLat() {
        return destinoCoordLat;
    }

    public void setDestinoCoordLat(String destinoCoordLat) {
        this.destinoCoordLat = destinoCoordLat;
    }

    public String getDestinoCoordLong() {
        return destinoCoordLong;
    }

    public void setDestinoCoordLong(String destinoCoordLong) {
        this.destinoCoordLong = destinoCoordLong;
    }

    public int getFkCar() {
        return fkCar;
    }

    public void setFkCar(int fkCar) {
        this.fkCar = fkCar;
    }

    public int getFkUser() {
        return fkUser;
    }

    public void setFkUser(int fkUser) {
        this.fkUser = fkUser;
    }

    public double getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(double totalKm) {
        this.totalKm = totalKm;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getQuantidadeLugares() {
        return quantidadeLugares;
    }

    public void setQuantidadeLugares(int quantidadeLugares) {
        this.quantidadeLugares = quantidadeLugares;
    }

    public int getLugaresDisponiveis() {
        return lugaresDisponiveis;
    }

    public void setLugaresDisponiveis(int lugaresDisponiveis) {
        this.lugaresDisponiveis = lugaresDisponiveis;
    }

    public String getDataViagem() {
        return dataViagem;
    }

    public void setDataViagem(String dataViagem) {
        this.dataViagem = dataViagem;
    }
}
