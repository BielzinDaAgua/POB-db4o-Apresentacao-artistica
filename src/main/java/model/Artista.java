package model;

import java.util.List;

public class Artista {
    private String nome;
    private String generoMusical;
    private int idade;
    private List<Apresentacao> listaApresentacao;

    public Artista(String nome, String generoMusical, int idade, List<Apresentacao> listaApresentacao) {
        this.nome = nome;
        this.generoMusical = generoMusical;
        this.idade = idade;
        this.listaApresentacao = listaApresentacao;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGeneroMusical() {
        return generoMusical;
    }

    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public List<Apresentacao> getListaApresentacao() {
        return listaApresentacao;
    }

    public void setListaApresentacao(List<Apresentacao> listaApresentacao) {
        this.listaApresentacao = listaApresentacao;
    }
}