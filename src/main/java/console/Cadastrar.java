package console;

import excecoes.ExcecaoNegocio;
import fachada.Fachada;
import model.Apresentacao;
import model.Artista;
import model.Cidade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cadastrar {
    public static void main(String[] args) {
        Fachada fachada = new Fachada();

        try {
            // Cadastrando Artistas
            List<Apresentacao> apresentacoes1 = new ArrayList<>();
            Artista artista1 = new Artista("Artista1", "Rock", 25, apresentacoes1);
            fachada.createArtista(artista1);

            List<Apresentacao> apresentacoes2 = new ArrayList<>();
            Artista artista2 = new Artista("Artista2", "Pop", 30, apresentacoes2);
            fachada.createArtista(artista2);

            // Cadastrando Cidades
            List<Apresentacao> apresentacoesCidade1 = new ArrayList<>();
            Cidade cidade1 = new Cidade("Cidade1", 10000, apresentacoesCidade1);
            fachada.createCidade(cidade1);

            List<Apresentacao> apresentacoesCidade2 = new ArrayList<>();
            Cidade cidade2 = new Cidade("Cidade2", 15000, apresentacoesCidade2);
            fachada.createCidade(cidade2);

            // Cadastrando Apresentacoes
            Apresentacao apresentacao1 = new Apresentacao(1, new Date(), artista1, cidade1, 50.0, 120, 5000);
            fachada.createApresentacao(apresentacao1);
            apresentacoes1.add(apresentacao1);
            apresentacoesCidade1.add(apresentacao1);

            Apresentacao apresentacao2 = new Apresentacao(2, new Date(), artista2, cidade2, 75.0, 150, 7000);
            fachada.createApresentacao(apresentacao2);
            apresentacoes2.add(apresentacao2);
            apresentacoesCidade2.add(apresentacao2);

            System.out.println("Objetos cadastrados com sucesso!");
        } catch (ExcecaoNegocio e) {
            System.err.println(e.getMessage());
        } finally {
            fachada.close();
        }
    }
}
