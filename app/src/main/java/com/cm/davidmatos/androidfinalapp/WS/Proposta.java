package com.cm.davidmatos.androidfinalapp.WS;

public class Proposta {

    private int idProposta;
    private int fkViagem;
    private int fkUser;
    private int estado;
    private String origemNome;
    private String origemCoordLat;
    private String origemCoordLong;

    public Proposta(int idProposta, int fkViagem, int fkUser, int estado, String origemNome, String origemCoordLat, String origemCoordLong) {
        this.idProposta = idProposta;
        this.fkViagem = fkViagem;
        this.fkUser = fkUser;
        this.estado = estado;
        this.origemNome = origemNome;
        this.origemCoordLat = origemCoordLat;
        this.origemCoordLong = origemCoordLong;
    }

    public Proposta() {

    }

    public int getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(int idProposta) {
        this.idProposta = idProposta;
    }

    public int getFkViagem() {
        return fkViagem;
    }

    public void setFkViagem(int fkViagem) {
        this.fkViagem = fkViagem;
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
}
