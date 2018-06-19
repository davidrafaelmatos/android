package com.cm.davidmatos.androidfinalapp.WS;

public class Boleia {

    private int idBoleia;
    private int fkViagem;
    private int fkUser;
    private int estadoPagamento;
    private int estado;

    public Boleia(int idBoleia, int fkViagem, int fkUser, int estadoPagamento, int estado) {
        this.idBoleia = idBoleia;
        this.fkViagem = fkViagem;
        this.fkUser = fkUser;
        this.estadoPagamento = estadoPagamento;
        this.estado = estado;
    }

    public int getIdBoleia() {
        return idBoleia;
    }

    public void setIdBoleia(int idBoleia) {
        this.idBoleia = idBoleia;
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

    public int getEstadoPagamento() {
        return estadoPagamento;
    }

    public void setEstadoPagamento(int estadoPagamento) {
        this.estadoPagamento = estadoPagamento;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
