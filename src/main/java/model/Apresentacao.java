package model;

import java.util.Date;

public class Apresentacao {
    private int id;
    private Date data;
    private Artista artista;
    private Cidade cidade;
    private double precoIngresso;
    private int duracao;
    private int numeroDeIngressosVendidos;

    public Apresentacao(int id, Date data, Artista artista, Cidade cidade, double precoIngresso, int duracao, int numeroDeIngressosVendidos) {
        this.id = id;
        this.data = data;
        this.artista = artista;
        this.cidade = cidade;
        this.precoIngresso = precoIngresso;
        this.duracao = duracao;
        this.numeroDeIngressosVendidos = numeroDeIngressosVendidos;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public double getPrecoIngresso() {
        return precoIngresso;
    }

    public void setPrecoIngresso(double precoIngresso) {
        this.precoIngresso = precoIngresso;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public int getNumeroDeIngressosVendidos() {
        return numeroDeIngressosVendidos;
    }

    public void setNumeroDeIngressosVendidos(int numeroDeIngressosVendidos) {
        this.numeroDeIngressosVendidos = numeroDeIngressosVendidos;
    }
}
