package com.cm.davidmatos.androidfinalapp.WS;

public class BoleiaViagem {

    private int estadoPagamento;
    private String origemNome;
    private String destinoNome;
    private String dataViagem;
    private int idBoleia;

    public BoleiaViagem(int idBoleia, int estadoPagamento, String origemNome, String destinoNome, String dataViagem) {
        this.estadoPagamento = estadoPagamento;
        this.origemNome = origemNome;
        this.destinoNome = destinoNome;
        this.dataViagem = dataViagem;
        this.idBoleia = idBoleia;
    }

    public BoleiaViagem() {
    }

    public int getEstadoPagamento() {
        return estadoPagamento;
    }

    public void setEstadoPagamento(int estadoPagamento) {
        this.estadoPagamento = estadoPagamento;
    }

    public String getOrigemNome() {
        return origemNome;
    }

    public void setOrigemNome(String origemNome) {
        this.origemNome = origemNome;
    }

    public String getDestinoNome() {
        return destinoNome;
    }

    public void setDestinoNome(String destinoNome) {
        this.destinoNome = destinoNome;
    }

    public String getDataViagem() {
        return dataViagem;
    }

    public void setDataViagem(String dataViagem) {
        this.dataViagem = dataViagem;
    }

    public int getIdBoleia() {
        return idBoleia;
    }

    public void setIdBoleia(int idBoleia) {
        this.idBoleia = idBoleia;
    }

}
