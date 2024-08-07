package model;

import java.util.List;

public class Cidade {
    private String nome;
    private int capacidadePublico;
    private List<Apresentacao> listaApresentacao;

    public Cidade(String nome, int capacidadePublico, List<Apresentacao> listaApresentacao) {
        this.nome = nome;
        this.capacidadePublico = capacidadePublico;
        this.listaApresentacao = listaApresentacao;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapacidadePublico() {
        return capacidadePublico;
    }

    public void setCapacidadePublico(int capacidadePublico) {
        this.capacidadePublico = capacidadePublico;
    }

    public List<Apresentacao> getListaApresentacao() {
        return listaApresentacao;
    }

    public void setListaApresentacao(List<Apresentacao> listaApresentacao) {
        this.listaApresentacao = listaApresentacao;
    }
}