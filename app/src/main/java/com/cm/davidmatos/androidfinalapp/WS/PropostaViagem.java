package com.cm.davidmatos.androidfinalapp.WS;

public class PropostaViagem {

    private int idProposta;
    private String origemNome;
    private String destinoNome;
    private String dataViagem;
    private int estado;

    public PropostaViagem(int idProposta, String origemNome, String destinoNome, String dataViagem, int estado) {
        this.idProposta = idProposta;
        this.origemNome = origemNome;
        this.destinoNome = destinoNome;
        this.dataViagem = dataViagem;
        this.estado = estado;
    }

    public PropostaViagem() {
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(int idProposta) {
        this.idProposta = idProposta;
    }
}
